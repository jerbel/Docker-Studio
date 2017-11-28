/**
 * Copyright (c) 2016-2017 Inria
 *  
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * - Christophe Gourdin <christophe.gourdin@inria.fr>
 *  
 */
package org.eclipse.cmf.occi.docker.connector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.cmf.occi.core.Link;
import org.eclipse.cmf.occi.docker.Container;
import org.eclipse.cmf.occi.docker.Contains;
import org.eclipse.cmf.occi.docker.Machine;
import org.eclipse.cmf.occi.docker.Network;
import org.eclipse.cmf.occi.docker.Networklink;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.docker.connector.exceptions.ValueNotSetException;
import org.eclipse.cmf.occi.docker.connector.helpers.DockerMachineHelper;
import org.eclipse.cmf.occi.docker.connector.helpers.ProcessManager;
import org.eclipse.cmf.occi.docker.connector.utils.DockerUtil;
import org.eclipse.cmf.occi.docker.connector.utils.Graph;
import org.eclipse.cmf.occi.docker.connector.utils.GraphNode;
import org.eclipse.cmf.occi.docker.connector.utils.ModelHandler;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.eclipse.cmf.occi.infrastructure.ComputeStatus;
import org.eclipse.cmf.occi.infrastructure.NetworkStatus;
import org.eclipse.cmf.occi.infrastructure.RestartMethod;
import org.eclipse.cmf.occi.infrastructure.StopMethod;
import org.eclipse.cmf.occi.infrastructure.SuspendMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * Abstract class for managing all machines types.
 * 
 * @author Christophe Gourdin
 *
 */
public abstract class MachineManager extends ComputeStateMachine<Compute> {

	private static Logger LOGGER = LoggerFactory.getLogger(MachineManager.class);

	// Get docker-machine command
	private String dockerMachineCmd = DockerUtil.getDockerMachineCmd();

	protected Compute compute;

	protected DockerClientManager dockerContainerManager = new DockerClientManager();

	protected Multimap<String, String> containerDependency = ArrayListMultimap.<String, String>create();

	/**
	 * 
	 * @param c
	 */
	public MachineManager(Compute c) {
		super(c);
		this.compute = c;
	}

	/**
	 * Return the Docker machine driver name.
	 */
	public abstract String getDriverName();

	/**
	 * Append specific Docker machine driver parameters.
	 */
	public abstract void appendDriverParameters(StringBuilder sb) throws ValueNotSetException;

	/**
	 * Start a Docker machine.
	 */
	@Override
	public void start_execute() throws DockerException {

		// Initialize the runtime
		Runtime runtime = Runtime.getRuntime();

		// Execute the docker-machine start command.
		StringBuilder command = new StringBuilder();

		String machineName = getMachineName(compute);

		Preconditions.checkNotNull(machineName, "Machine name is null");
		Preconditions.checkNotNull(getDriverName(), "Driver name is null");

		StringBuilder parameter = putParameters(compute);

		// build docker-machine command
		String dockerMachineCMD = String.format("%s -D create --driver ", this.dockerMachineCmd);

		// Create the machine command
		command.append(dockerMachineCMD).append(getDriverName());
		// Add the corresponding command
		try {
			appendDriverParameters(command);
		} catch (ValueNotSetException ex) {
			throw new DockerException(ex);
		}
		// Add Parameters to command
		command.append(" ").append(parameter);
		command.append(" ").append(machineName);

		System.out.println("CMD : #" + command.toString());

		// Get the existing machines
		Map<String, String> hosts = DockerUtil.getHosts();
		Map<String, String> activeHosts = new HashMap<>();

		// Filter the map for the active machine.
		for (Map.Entry<String, String> entry : hosts.entrySet()) {
			String name = entry.getKey();
			String status = entry.getValue();
			if (status.equalsIgnoreCase(DockerUtil.HOST_RUNNING)) {
				activeHosts.put(name, status);
			}
		}

		if (!hosts.containsKey(machineName)) { // Check if machine exists in the real environment
			// Create the machine and start it
			ProcessManager.runCommand(command.toString(), runtime, true);
		} else {
			if (!activeHosts.containsKey(machineName)) {
				// Start the machine
				DockerMachineHelper.startCmd(runtime, machineName);
				// Regenerate Cert when IP addresses change
				DockerMachineHelper.regenerateCert(runtime, machineName);
			}
		}

		Map<Container, Set<Networklink>> networks = detectNetworkLink();
		// Create the network overlay
		this.createNetwork(networks);
		// Connect all container to network overlay
		this.connectToNetwork(this.compute, networks);

	}

	@Override
	public void startAll_execute() throws DockerException {

		// Initialize the runtime
		Runtime runtime = Runtime.getRuntime();

		// Execute the docker-machine start command.
		StringBuilder command = new StringBuilder();

		String machineName = getMachineName(compute);

		Preconditions.checkNotNull(machineName, "Machine name is null");
		Preconditions.checkNotNull(getDriverName(), "Driver name is null");

		StringBuilder parameter = putParameters(compute);

		// Create the machine command
		String dockerMachineCMD = String.format("%s -D create --driver ", this.dockerMachineCmd);
		command.append(dockerMachineCMD).append(getDriverName());
		// Add the corresponding command
		try {
			appendDriverParameters(command);
		} catch (ValueNotSetException ex) {
			throw new DockerException(ex);
		}
		// Add Parameters to command
		command.append(' ').append(parameter);
		command.append(' ').append(machineName);

		// Get the existing machines
		Map<String, String> hosts = DockerUtil.getHosts();
		Map<String, String> activeHosts = new HashMap<>();

		// Filter the map for the active machine.
		for (Map.Entry<String, String> entry : hosts.entrySet()) {
			String name = entry.getKey();
			String status = entry.getValue();
			if (status.equalsIgnoreCase(DockerUtil.HOST_RUNNING)) {
				activeHosts.put(name, status);
			}
		}

		Map<Container, Set<Networklink>> networks = detectNetworkLink();

		if (!hosts.containsKey(machineName)) { // Check if machine exists in the real environment
			// Create the machine and start it
			ProcessManager.runCommand(command.toString(), runtime, true);

			// Set state
			compute.setOcciComputeState(ComputeStatus.ACTIVE);

			// Create the network if it exists
			this.createNetwork(networks);

			// Create the Containers belong to this machine.
			List<com.github.dockerjava.api.model.Container> listContainers = dockerContainerManager
					.listContainer(compute);

			if (!compute.getLinks().isEmpty()) {

				// Start the containers without create graph
				if (!this.linkFound()) {
					for (Link link : compute.getLinks()) {
						if (link instanceof Contains) {
							Contains contains = (Contains) link;
							if (contains.getTarget() instanceof org.eclipse.cmf.occi.docker.Container) {
								ContainerConnector con = (ContainerConnector) contains.getTarget();

								// The container does not exists in the machine
								if (!containerIsDeployed(con.getName(), this.compute, listContainers)) {

									// Create container
									con.createContainer(this.compute);

									// Start container
									con.start();
								} else { // The container exists, then just starts it
									// Start container
									con.start();
								}
							}
						}
					}
				} else { // Create the graph
					Multimap<String, String> dependencies = this.containerDependency;
					for (Container c : this.deploymentOrder()) {
						ContainerConnector con = (ContainerConnector) c;
						if (!containerIsDeployed(con.getName(), compute, listContainers)) {

							// Create container
							con.createContainer(this.compute, dependencies);

							// Start container
							con.start();
						} else {

							// Start container
							con.start();
						}
					}
				}
			}
		} else { // The machine exits, just start it and create the containers inside it
			if (!activeHosts.containsKey(machineName)) {

				// Start the machine
				DockerMachineHelper.startCmd(runtime, machineName);

				// Regenerate Cert when IP addresses change
				DockerMachineHelper.regenerateCert(runtime, machineName);

				// Set state
				compute.setOcciComputeState(ComputeStatus.ACTIVE);

				// Create the network if it exists
				this.createNetwork(networks);

				// Create the Containers belong to this machine.
				List<com.github.dockerjava.api.model.Container> listContainers = dockerContainerManager
						.listContainer(compute);
				if (!compute.getLinks().isEmpty()) {

					// Start the containers without create graph
					if (!this.linkFound()) {
						for (Link link : compute.getLinks()) {
							if (link instanceof Contains) {
								Contains contains = (Contains) link;
								if (contains.getTarget() instanceof Container) {
									ContainerConnector con = (ContainerConnector) contains.getTarget();

									// The container does not exists in the machine
									if (!containerIsDeployed(con.getName(), this.compute, listContainers)) {

										// Create container
										System.out.println("Creating the container: " + con.getName());
										con.createContainer(this.compute);
										System.out.println("The container is created");

										// Start container
										con.start();
									} else { // The machine exists
										// Start container
										System.out.println("Trying to start container: " + con.getName());
										con.start();
										System.out.println("Started ...");
									}
								}
							}
						}
					} else {
						for (Container c : this.deploymentOrder()) {
							ContainerConnector con = (ContainerConnector) c;

							// The container does not exists in the machine
							if (!containerIsDeployed(con.getName(), compute, listContainers)) {

								// Create container
								con.createContainer(this.compute, this.containerDependency);

								// Start container
								con.start();
							} else { // The container exists
								// Start container
								System.out.println("Trying to start container: " + con.getName());
								con.start();
								System.out.println("Started ... ");
							}
						}
					}
				}
			} else {

				// Create the Containers belong to this machine.
				List<com.github.dockerjava.api.model.Container> listContainers = dockerContainerManager
						.listContainer(compute);
				if (!compute.getLinks().isEmpty()) {

					// Start the containers without create graph
					if (!this.linkFound()) {
						for (Link link : compute.getLinks()) {
							if (link instanceof Contains) {
								Contains contains = (Contains) link;
								if (contains.getTarget() instanceof Container) {
									ContainerConnector con = (ContainerConnector) contains.getTarget();
									if (!containerIsDeployed(con.getName(), this.compute, listContainers)) {

										// Create container
										con.createContainer(this.compute);

										// Start container
										con.start();
									} else {

										// Start container
										System.out.println("Trying to start container: " + con.getName());
										con.start();
										System.out.println("Started ...");
									}
								}
							}
						}
					} else {
						for (Container c : this.deploymentOrder()) {
							ContainerConnector con = (ContainerConnector) c;

							// The container does not exists in the machine
							if (!containerIsDeployed(con.getName(), compute, listContainers)) {

								// Create container
								con.createContainer(this.compute, this.containerDependency);

								// Start container
								System.out.println("Trying to start container: " + con.getName());
								con.start();
								System.out.println("Started ... ");
							} else { // The container exists
								// Start container
								System.out.println("Trying to start container: " + con.getName());
								System.out.println("Started ... ");
								con.start();
							}
						}
					}
				}

			}
		}

		// Connect all container to network overlay
		this.connectToNetwork(this.compute, networks);

		System.out.println("EXECUTE COMMAND: " + command.toString());
	}

	/**
	 * Connect container to all networks overlay.
	 */
	public void connectToNetwork(Compute machine, Map<org.eclipse.cmf.occi.docker.Container, Set<Networklink>> networks)
			throws DockerException {
		dockerContainerManager.connectToNetwork(this.compute, networks);
	}

	/**
	 * Create and update the Id of all networks detected inside the machine
	 */
	protected void createNetwork(Map<Container, Set<Networklink>> networks) throws DockerException {
		if (!networks.isEmpty()) {
			Set<String> createdNetworks = new HashSet<>();
			String machineName = getMachineName(compute);
			for (Map.Entry<Container, Set<Networklink>> entry : networks.entrySet()) {
				for (Networklink net : entry.getValue()) {
					Network tmpNetwork = (Network) net.getTarget();
					if (!createdNetworks.contains(tmpNetwork.getName())) {
						createdNetworks.add(tmpNetwork.getName());
						String networkId = dockerContainerManager.createNetwork(this.compute, tmpNetwork);
						// Update the model networkId
						tmpNetwork.setNetworkId(networkId);
						System.out.println("Network name=#" + tmpNetwork.getName()
								+ " was created inside ---> machine #" + machineName);
						// TODO change this with Network StateMachine
						// Change the Network State
						tmpNetwork.setOcciNetworkState(NetworkStatus.ACTIVE);
					}
				}

			}
		}
	}

	/**
	 * Retrieve information and / or remove container from workbench if it has been
	 * remove from real.
	 * 
	 * @throws DockerException
	 */
	public void synchronize() throws DockerException {

		// Get all hosts in the real environment
		Map<String, String> hosts = DockerUtil.getHosts();
		String machineName = getMachineName(compute);

		// refreshing compute state :
		// (else synchronize useless, because fails if machine stopped without the model
		// knowing it)
		if (hosts.containsKey(machineName) && !DockerUtil.HOST_RUNNING.equalsIgnoreCase(hosts.get(machineName))) { // else
																													// #193
																													// several
																													// DockerException
																													// :
			// certifateDockerUtil. path (DOCKER_CERT_PATH) is not public voidined,
			// Unsupported protocol scheme...
			// machine is actually down, though it is believed to be up
			// (inspired by start_execute(), to be refactored centrally
			this.compute.setOcciComputeState(ComputeStatus.INACTIVE);
			// TODO Q or start it right away instead ??
			// start(All)_execute
		} else if (DockerUtil.HOST_RUNNING.equalsIgnoreCase(hosts.get(machineName))) {
			this.compute.setOcciComputeState(ComputeStatus.ACTIVE);
		}
		// NB. another way could be by testing model built from Docker introspection :
		// instanceMH.getModel(this.compute.name, hosts.get(this.compute.name),
		// false).state

		if (this.compute.getOcciComputeState().equals(ComputeStatus.ACTIVE)) {
			ModelHandler instanceMH = new ModelHandler();
			DockerClientManager instance = new DockerClientManager(this.compute);
			List<com.github.dockerjava.api.model.Container> listContainers = dockerContainerManager
					.listContainer(compute);
			if (!this.compute.getLinks().isEmpty()) {
				// Create the containers without create graph
				if (!this.linkFound()) {
					List<String> containersInModel = new ArrayList<>();
					for (Link link : compute.getLinks()) {
						if (link instanceof Contains) {
							Contains contains = (Contains) link;
							if (contains.getTarget() instanceof Container) {
								ContainerConnector con = (ContainerConnector) contains.getTarget();
								containersInModel.add(con.getName());
								com.github.dockerjava.api.model.Container dc = getDeployedContainer(con.getName(),
										this.compute, listContainers);
								if (dc == null) {
									// Create container
									System.out.println("Creating the container: " + con.getName());
									con.createContainer(this.compute);
									System.out.println("The container is created");

								} else if (con.getContainerid() != dc.getId()) {
									con.setContainerid(dc.getId()); // update id else start cmd ill fail
								}
							}
						}

						// Remove the containers
						List<String> containersToRemove = containerInReal(machineName, listContainers);
						if (!containersToRemove.isEmpty()) {
							containersToRemove.removeAll(containersInModel);
							for (String id : containersToRemove) {
								instance.removeContainer(this.compute, id);
							}

						}
					}
				} else {
					List<String> containersInModel = new ArrayList<>();
					for (Container c : this.deploymentOrder()) {
						ContainerConnector con = (ContainerConnector) c;
						containersInModel.add(c.getName());

						// The container does not exists in the machine
						com.github.dockerjava.api.model.Container dc = getDeployedContainer(con.getName(), this.compute,
								listContainers);
						if (dc == null) {
							// Create container
							con.createContainer(this.compute, this.containerDependency);

						} else if (con.getContainerid() != dc.getId()) {
							con.setContainerid(dc.getId()); // update id else start cmd ill fail
						}
					}

					// Remove the containers
					List<String> containersToRemove = containerInReal(machineName, listContainers);
					if (!containersToRemove.isEmpty()) {
						containersToRemove.removeAll(containersInModel);
						for (String id : containersToRemove) {
							instance.removeContainer(this.compute, id);
						}
					}
				}
			}
			/*
			 * // Introspect containers val contains = instance.listContainer(machine) if
			 * (contains != null) { val modelContainers =
			 * instanceMH.buildContainer(this.compute, contains) for (Container container :
			 * modelContainers) { (container as
			 * ExecutableContainer).linkContainerToMachine(this.compute) } if
			 * (this.compute.links != null) { for (Link link : compute.links) {
			 * this.compute.eContainer.eResource.allContents.toList.add(link) if (link
			 * instanceof Link) { val c = link as Contains if (c.target instanceof
			 * Container) { this.compute.eContainer.eResource.allContents.toList.add(
			 * (c.target as Container)) } } }
			 *
			 * } }
			 */
		} else {
			if (!compute.getOcciComputeState().equals(ComputeStatus.INACTIVE) && !compute.getLinks().isEmpty()) {

				// Stop the containers
				// TODO won't do anything since machine is stopped, rather merely
				// container.state = INACTIVE
				for (Link link : compute.getLinks()) {
					if (link.getTarget() != null && link.getTarget() instanceof Container) {
						Container container = (Container) link.getTarget();
						container.stop(StopMethod.GRACEFUL);
					}
				}
			}
			// compute.links.filter[elt|elt.target != null] // else NPE on links to delete
			// containers #194
			// .forEach[elt|(elt.target as ExecutableContainer).stop(StopMethod.GRACEFUL)]
		}

	}

	/**
	 * Checks inside the machine model if any container has networkLink
	 */
	public Map<Container, Set<Networklink>> detectNetworkLink() {
		Map<Container, Set<Networklink>> networkLinksMap = new LinkedHashMap<>();
		Set<Networklink> networkLinksSet = new HashSet<>();
		for (Link l : compute.getLinks()) {
			if (l instanceof Contains) {
				Contains contains = (Contains) l;
				if (contains.getTarget() instanceof Container) {
					Container container = (Container) contains.getTarget();
					for (Link cl : container.getLinks()) {
						if (cl instanceof Networklink && cl.getTarget() instanceof Network) {
							networkLinksSet.add(((Networklink) cl));
						}
					}
					networkLinksMap.put((Container) (contains.getTarget()), networkLinksSet);
				}
			}
		}
		return networkLinksMap;
	}

	/**
	 * Checks if there is a link between containers.
	 */
	public boolean linkFound() {
		List<Container> containers = this.getContainers();
		boolean linkFound = false;
		for (Container c : containers) {
			if (c != null) {
				if (!c.getLinks().isEmpty()) {
					linkFound = true;
					break;
				}
			}
		}
		return linkFound;
	}

	/**
	 * Provide the containers deployment order.
	 */
	public List<Container> deploymentOrder() {
		List<Container> containers = new ArrayList<>();
		Graph<Container> graph = new Graph<Container>();

		for (Link l : compute.getLinks()) {
			if (l instanceof Contains) {
				Contains contains = (Contains) l;
				if (contains.getTarget() instanceof Container) {
					Container container = (Container) contains.getTarget();
					for (Link cl : container.getLinks()) {
						if (cl.getTarget() instanceof Container) {
							graph.addDependency(container, ((Container) cl.getTarget()));
							this.containerDependency.put(container.getName(), ((Container) cl.getTarget()).getName());
						}
					}

				}
			}
		}
		System.out.println("------------------- GRAPH : " + graph);
		try {
			if (graph.deploymentOrder() != null) {
				for (GraphNode<Container> c : graph.deploymentOrder()) {

					containers.add(c.value);
					System.out.println("--->" + c.value);
				}
			}

		} catch (NullPointerException exception) { // TODO : Conception problem, remove this try catch nullpointer...

		}

		// Add standalone container
		for (Container standaloneContainer : this.leafContainers()) {
			if (!containers.contains(standaloneContainer)) {
				containers.add(standaloneContainer);
			}
		}

		for (Container container : containers) {
			System.out.println("Container : " + container.getName());
		}

		return containers;
	}

	/**
	 * Retrieves all containers inside a given machine.
	 */
	public List<Container> getContainers() {
		List<Container> containers = new ArrayList<>();
		for (Link link : compute.getLinks()) {
			if (link.getTarget() instanceof Container) {
				containers.add((Container) link.getTarget());
			}
		}
		// compute.links.forEach[elt|containers.add((elt.target as Container))]
		containers.removeAll(Collections.singleton(null)); // TODO : Check null on containers list !!! There is maybe a
															// big problem here..
		return containers;
	}

	/**
	 * Get all containers which has not a link to another container.
	 */
	public List<Container> leafContainers() {
		List<Container> containers = this.getContainers();
		List<Container> leafContainers = new ArrayList<>();
		for (Container c : containers) {
			if (c.getLinks().isEmpty()) {
				leafContainers.add(c);
			} else {
				Boolean tagertContainerFound = false;
				for (Link l : c.getLinks()) {
					if (l.getTarget() instanceof Container) {
						tagertContainerFound = true;
					}
				}
				if (!tagertContainerFound) {
					leafContainers.add(c);
				}
			}
		}
		return leafContainers;
	}

	/**
	 * Checks if the container is deployed.
	 */
	public boolean containerIsDeployed(String containerName, Compute machine) throws DockerException {
		List<com.github.dockerjava.api.model.Container> listContainers = dockerContainerManager.listContainer(machine);
		return containerIsDeployed(containerName, machine, listContainers);
	}

	/**
	 * 
	 * @param containerName
	 * @param machine
	 * @param listContainers
	 * @return
	 */
	public boolean containerIsDeployed(String containerName, Compute machine,
			List<com.github.dockerjava.api.model.Container> listContainers) {
		return getDeployedContainer(containerName, machine, listContainers) != null;
	}

	/**
	 * 
	 * @param containerName
	 * @param machine
	 * @param listContainers
	 * @return
	 */
	public com.github.dockerjava.api.model.Container getDeployedContainer(String containerName, Compute machine,
			List<com.github.dockerjava.api.model.Container> listContainers) {
		for (com.github.dockerjava.api.model.Container c : listContainers) {
			String contName = null;
			String name = c.getNames()[0];
			String linkName = "LinkTo";
			int index = name.indexOf(linkName);
			if (index == -1) {
				contName = name.replaceAll("/", "");
			} else {

				// index = index + linkName.length
				contName = name.substring(index + linkName.length());
			}
			if (contName.equalsIgnoreCase(containerName)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Get all containers deployed inside a machine.
	 */
	public List<String> containerInReal(Compute machine) throws DockerException {
		List<com.github.dockerjava.api.model.Container> listContainers = dockerContainerManager.listContainer(machine);
		return containerInReal(getMachineName(machine), listContainers);
	}

	/**
	 * 
	 * @param machineName
	 * @param listContainers
	 * @return
	 */
	public List<String> containerInReal(String machineName,
			List<com.github.dockerjava.api.model.Container> listContainers) {
		List<String> containers = new ArrayList<>();
		for (com.github.dockerjava.api.model.Container c : listContainers) {
			String contName = null;
			String name = c.getNames()[0];
			String linkName = "LinkTo";
			int index = name.indexOf(linkName);
			if (index == -1) {
				contName = name.replaceAll("/", "");
			} else {

				// index = index + linkName.length
				contName = name.substring(index + linkName.length());
			}
			containers.add(contName);
		}
		return containers;
	}

	/**
	 * Stop a Docker machine.
	 */
	@Override
	public void stop_execute(StopMethod method) {

		String machineName = getMachineName(compute);
		// Execute the docker-machine stop command.
		System.out.println("EXECUTE COMMAND: docker machine stop: " + machineName);

		// Stop all Docker containers contained by this Docker machine.
		if (method == StopMethod.GRACEFUL) {
			for (Link link : compute.getLinks()) {
				if (link instanceof Contains) {
					Contains contains = (Contains) link;
					if (contains.getTarget() instanceof org.eclipse.cmf.occi.docker.Container) {
						Container container = (Container) contains.getTarget();
						container.stop(StopMethod.GRACEFUL);
					}
				}
			}
			// Stop all the Network
			Map<Container, Set<Networklink>> networks = detectNetworkLink();
			if (networks.isEmpty()) {
				for (Map.Entry<Container, Set<Networklink>> entry : networks.entrySet()) {
					for (Networklink net : entry.getValue()) {
						Network tmpNetwork = (Network) net.getTarget();
						tmpNetwork.setOcciNetworkState(NetworkStatus.INACTIVE);
					}
				}
			}

			// Stop the machine
			try {
				DockerMachineHelper.stopCmd(Runtime.getRuntime(), machineName);
			} catch (DockerException ex) {
				LOGGER.error("Unable to stop the machine : " + machineName + " error: " + ex.getMessage());
			}

		}

	}

	/**
	 * Restart a Docker machine.
	 * 
	 * @throws DockerException
	 */
	@Override
	public void restart_execute(RestartMethod method) throws DockerException {
		String machineName;
		if (this.compute instanceof Machine) {
			machineName = ((Machine) compute).getName();
		} else {
			machineName = compute.getTitle();
		}
		System.out.println("EXECUTE COMMAND: docker machine restart " + machineName);
		stop_execute(StopMethod.GRACEFUL);
		start_execute();
	}

	/**
	 * Suspend a Docker machine.
	 */
	@Override
	public void suspend_execute(SuspendMethod method) {
		String machineName;
		if (this.compute instanceof Machine) {
			machineName = ((Machine) compute).getName();
		} else {
			machineName = compute.getTitle();
		}
		System.out.println("EXECUTE COMMAND: docker machine suspend " + machineName);

		// TODO: must be implemented
	}

	/**
	 * Remove a machine from provider.
	 * 
	 * @param machine
	 * @throws DockerException
	 */
	public void removeMachine(Compute machine) throws DockerException {
		String machineName = getMachineName(machine);
		if (machineName == null) {
			throw new DockerException("Undefined machine name, cannot remove it.");
		}
		try {
			DockerMachineHelper.removeCmd(Runtime.getRuntime(), machineName);
		} catch (DockerException ex) {
			LOGGER.error("Unable to remove the machine : " + machineName + " error: " + ex.getMessage());
			throw new DockerException(ex.getMessage(), ex);
		}

	}

	/**
	 * 
	 * @param compute
	 * @return
	 */
	public String getMachineName(final Compute compute) {
		String machineName = null;
		// Check requirements parameters
		if (compute instanceof Machine) {
			machineName = ((Machine) compute).getName();

		} else {
			machineName = compute.getTitle();
		}
		return machineName;
	}

	/**
	 * Put parameters for docker machine commands for swarm and engine.
	 * 
	 * @param compute
	 * @return
	 */
	private StringBuilder putParameters(final Compute compute) {
		StringBuilder parameter = new StringBuilder();
		// TODO : Replace by a mixin for swarm.
		// TODO : Replace by a mixin for engine options.
		if (compute instanceof Machine) {
			Machine machine = (Machine) compute;
			if (machine.isSwarm()) {
				parameter.append(" --swarm");
			}
			if (machine.isSwarmMaster()) {
				parameter.append(" --swarm-master");
			}

			if (StringUtils.isNotBlank(machine.getSwarmDiscovery())) {
				parameter.append(" --swarm-discovery=\"" + machine.getSwarmDiscovery() + "\"");
			}

			if (StringUtils.isNotBlank(machine.getSwarmAddr())) {
				parameter.append(" --swarm-addr=\"" + machine.getSwarmAddr() + "\"");
			}
			if (StringUtils.isNotBlank(machine.getSwarmExperimental())) {
				parameter.append(" --swarm-experimental=\"" + machine.getSwarmExperimental() + "\"");
			}
			if (StringUtils.isNotBlank(machine.getSwarmHost())) {
				parameter.append(" --swarm-host=\"" + machine.getSwarmHost() + "\"");
			}
			if (StringUtils.isNotBlank(machine.getSwarmImage())) {
				parameter.append(" --swarm-image=\"" + machine.getSwarmImage() + "\"");
			}

			if (StringUtils.isNotBlank(machine.getSwarmOpt())) {

				String[] tab_swarm_opt = machine.getSwarmOpt().split(";");
				for (String opt : tab_swarm_opt) {
					parameter.append(" --swarm-opt=\"" + opt + "\"");
				}
			}

			if (StringUtils.isNotBlank(machine.getEngineEnv())) {
				parameter.append(" --engine-env=\"" + machine.getEngineEnv() + "\"");
			}

			if (StringUtils.isNotBlank(((Machine) compute).getEngineInsecureRegistry())) {
				parameter.append(" --engine-insecure-registry=\"" + machine.getEngineInsecureRegistry() + "\"");
			}
			if (StringUtils.isNotBlank(machine.getEngineInstallURL())) {
				parameter.append(" --engine-install-url=\"" + machine.getEngineInstallURL() + "\"");
			}
			if (StringUtils.isNotBlank(machine.getEngineLabel())) {
				parameter.append(" --engine-label=\"" + machine.getEngineLabel() + "\"");
			}
			if (StringUtils.isNotBlank(machine.getEngineOpt())) {
				String[] tab_engine_opt = machine.getEngineOpt().split(";");
				for (String opt : tab_engine_opt) {
					parameter.append(" --engine-opt=\"" + opt + "\"");
				}

			}

		}
		return parameter;
	}

}
