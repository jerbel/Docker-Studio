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
package org.eclipse.cmf.occi.docker.connector.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.cmf.occi.docker.ArrayOfString;
import org.eclipse.cmf.occi.docker.Container;
import org.eclipse.cmf.occi.docker.Contains;
import org.eclipse.cmf.occi.docker.DockerFactory;
import org.eclipse.cmf.occi.docker.Machine;
import org.eclipse.cmf.occi.docker.Machineamazonec2;
import org.eclipse.cmf.occi.docker.Machinedigitalocean;
import org.eclipse.cmf.occi.docker.Machinegooglecomputeengine;
import org.eclipse.cmf.occi.docker.Machineibmsoftlayer;
import org.eclipse.cmf.occi.docker.Machinemicrosoftazure;
import org.eclipse.cmf.occi.docker.Machinemicrosofthyperv;
import org.eclipse.cmf.occi.docker.Machineopenstack;
import org.eclipse.cmf.occi.docker.Machinerackspace;
import org.eclipse.cmf.occi.docker.Machinevirtualbox;
import org.eclipse.cmf.occi.docker.Machinevmwarefusion;
import org.eclipse.cmf.occi.docker.Machinevmwarevsphere;
import org.eclipse.cmf.occi.docker.connector.DockerClientManager;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.docker.connector.helpers.DockerConfigurationHelper;
import org.eclipse.cmf.occi.docker.connector.helpers.DockerMachineHelper;
import org.eclipse.cmf.occi.docker.connector.helpers.Provider;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.eclipse.cmf.occi.infrastructure.ComputeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Link;
import com.github.dockerjava.api.model.NetworkSettings;
import com.github.dockerjava.api.model.Ports;

/**
 * 
 * @author Christophe Gourdin
 *
 */
public class ModelHandler {
	// TODO : Refactor with OCCIHelper and synchronize / import model commands..
	private static Logger LOGGER = LoggerFactory.getLogger(ModelHandler.class);

	public Map<String, Machine> getModelEClass() {
		// TODO : Check this.
		Map<String, Machine> m = new HashMap<>();
		// TODO verify the good driver name
		m.put(Provider.virtualbox.toString(), DockerFactory.eINSTANCE.createMachinevirtualbox());
		m.put(Provider.amazonec2.toString(), DockerFactory.eINSTANCE.createMachineamazonec2());
		m.put(Provider.digitalocean.toString(), DockerFactory.eINSTANCE.createMachinedigitalocean());
		m.put(Provider.google.toString(), DockerFactory.eINSTANCE.createMachinegooglecomputeengine());
		m.put(Provider.ibm.toString(), DockerFactory.eINSTANCE.createMachineibmsoftlayer());
		m.put(Provider.azure.toString(), DockerFactory.eINSTANCE.createMachinemicrosoftazure());
		m.put(Provider.microsofthyperv.toString(), DockerFactory.eINSTANCE.createMachinemicrosofthyperv());
		m.put(Provider.openstack.toString(), DockerFactory.eINSTANCE.createMachineopenstack());
		m.put(Provider.rackspace.toString(), DockerFactory.eINSTANCE.createMachinerackspace());
		m.put(Provider.vmwarefusion.toString(), DockerFactory.eINSTANCE.createMachinevmwarefusion());
		m.put(Provider.vmwarevcloudair.toString(), DockerFactory.eINSTANCE.createMachinevmwarevcloudair());
		m.put(Provider.vmwarevsphere.toString(), DockerFactory.eINSTANCE.createMachinevmwarevsphere());
		return m;
	}

	public Machine getModel(String machine, String state, boolean machineExists) throws DockerException {

		DockerClientManager instance = new DockerClientManager();
		JsonNode node = DockerUtil.jsonify(DockerMachineHelper.inspectHostCmd(Runtime.getRuntime(), machine));
		Machine vbox = getModelEClass().get(node.get("DriverName").toString().replaceAll("\"", ""));
		if (node != null) {

			if (vbox instanceof Machinevirtualbox) {
				Machinevirtualbox newvbox = (Machinevirtualbox) vbox;
				// Set values
				machineFactory_VBOX(newvbox, node, state);
				System.out.println("Model setting: " + newvbox);

			} else if (vbox instanceof Machineamazonec2) {
				Machineamazonec2 newvbox = (Machineamazonec2) vbox;

				// Set values
				machineFactory(newvbox, node, state);
				System.out.println("Model setting: " + newvbox);

			} else if (vbox instanceof Machinedigitalocean) {
				Machinedigitalocean newvbox = (Machinedigitalocean) vbox;

				// Set values
				machineFactory(newvbox, node, state);
				System.out.println("Model setting: " + newvbox);
			} else if (vbox instanceof Machinevmwarefusion) {
				Machinevmwarefusion newvbox = (Machinevmwarefusion) vbox;

				// Set values
				machineFactory_Fusion(newvbox, node, state);
				System.out.println("Model setting: " + newvbox);

			} else if (vbox instanceof Machinegooglecomputeengine) {
				Machinegooglecomputeengine newvbox = (Machinegooglecomputeengine) vbox;

				// Set values
				machineFactory(newvbox, node, state);
				System.out.println("Model setting: " + newvbox);
			} else if (vbox instanceof Machineibmsoftlayer) {
				Machineibmsoftlayer newvbox = (Machineibmsoftlayer) vbox;

				// Set values
				machineFactory(newvbox, node, state);
				System.out.println("Model setting: " + newvbox);
			} else if (vbox instanceof Machinemicrosoftazure) {
				Machinemicrosoftazure newvbox = (Machinemicrosoftazure) vbox;

				// Set values
				machineFactory(newvbox, node, state);
				System.out.println("Model setting: " + newvbox);
			} else if (vbox instanceof Machinemicrosofthyperv) {
				Machinemicrosofthyperv newvbox = (Machinemicrosofthyperv) vbox;

				// Set values
				machineFactory(newvbox, node, state);
				System.out.println("Model setting: " + newvbox);
			} else if (vbox instanceof Machineopenstack) {
				Machineopenstack newvbox = (Machineopenstack) vbox;

				// Set values
				machineFactory_OpenStack(newvbox, node, state);
				System.out.println("Model setting: " + newvbox);
			} else if (vbox instanceof Machinerackspace) {
				Machinerackspace newvbox = (Machinerackspace) vbox;

				// Set values
				machineFactory(newvbox, node, state);
				System.out.println("Model setting: " + newvbox);
			} else if (vbox instanceof Machinevmwarevsphere) {
				Machinevmwarevsphere newvbox = (Machinevmwarevsphere) vbox;

				// Set values
				machineFactory(newvbox, node, state);
				System.out.println("Model setting: " + newvbox);
			}
			if (!machineExists) { // ignore the machine if it exists.
				// Check machine state
				if (vbox.getOcciComputeState().toString().equalsIgnoreCase("active")) {
					DockerClient dockerClient = DockerConfigurationHelper.buildDockerClient(vbox);
					instance.setDockerClient(dockerClient);
					instance.setCompute(vbox);

					// Introspect containers
					List<com.github.dockerjava.api.model.Container> containers = instance.listContainer(vbox);
					if (containers != null && !containers.isEmpty()) {
						List<Container> modelContainers = buildContainer(vbox, containers);
						for (Container container : modelContainers) {
							HashSet<String> existingLinks = new HashSet<>();
							linkContainerToMachine(container, vbox);
							InspectContainerResponse inspectContainer = instance.inspectContainer(vbox,
									container.getContainerid());
							if (inspectContainer.getHostConfig().getLinks().length > 0) { // ignore the link if it is
																							// already taken into
																							// account
								for (Link link : inspectContainer.getHostConfig().getLinks()) {
									if (!existingLinks.contains(link.getName())) {
										linkContainerToContainer(container,
												getContainerByName(modelContainers, link.getName()));
										existingLinks.add(link.getName());
									}

								}
							}
						}
					}

				}

			}

		}

		return vbox;
	}

	/**
	 * 
	 * @param containers
	 * @param containerName
	 * @return
	 */
	private Container getContainerByName(final List<Container> containers, final String containerName)
			throws DockerException {
		for (Container c : containers) {
			if (c.getName() == containerName) {
				return c;
			}
		}
		throw new DockerException("No container model found for this name : " + containerName);
	}

	/**
	 * Build container model from real docker container.
	 * 
	 * @param machine
	 * @param containers
	 * @return
	 * @throws DockerException
	 */
	public List<Container> buildContainer(Machine machine, List<com.github.dockerjava.api.model.Container> containers)
			throws DockerException {
		DockerClientManager instance = new DockerClientManager(machine);

		List<Container> containerList = new ArrayList<>();
		for (com.github.dockerjava.api.model.Container c : containers) {
			InspectContainerResponse currentContainer = instance.inspectContainer(machine, c.getId());
			currentContainer.getId();

			// Retrieve the default factory singleton
			Container modelContainer = DockerFactory.eINSTANCE.createContainer();
			updateContainerModel(modelContainer, currentContainer);

			containerList.add(modelContainer);
		}

		return containerList;
	}

	/**
	 * 
	 * @param modelContainer
	 * @param currentContainer
	 * @return
	 * @throws DockerException
	 */
	public Container updateContainerModel(Container modelContainer, InspectContainerResponse currentContainer)
			throws DockerException {
		modelContainer.setName(currentContainer.getName().replace("/", ""));
		String imageName = currentContainer.getConfig().getImage();
		System.out.println("Image name : " + imageName);

		modelContainer.setImage(imageName);
		String[] commands = currentContainer.getConfig().getCmd();

		String command = Arrays.toString(currentContainer.getConfig().getCmd()).replace("[", "").replace("]", "");
		if (command != null && !command.trim().isEmpty()) {
			System.out.println("Command found : " + command);
			modelContainer.setCommand(
					Arrays.toString(currentContainer.getConfig().getCmd()).replace("[", "").replace("]", ""));
		}

		modelContainer.setContainerid(currentContainer.getId());
		String user = currentContainer.getConfig().getUser();
		if (user != null && !user.trim().isEmpty()) {
			modelContainer.setUser(user);
		}

		ExposedPort[] exposedPorts = currentContainer.getConfig().getExposedPorts();
		NetworkSettings netSettings = currentContainer.getNetworkSettings();
		final Ports ports = netSettings.getPorts();
		// Prepare the model with complex data Array of String.
		ArrayOfString modelPorts = DockerFactory.eINSTANCE.createArrayOfString();
		List<String> portsToAdd = new LinkedList<>();
		if (exposedPorts != null) {
			for (ExposedPort exposedPort : exposedPorts) {
				String expoPort = exposedPort.toString().replace("[", "").replace("]", "");
				String portFinal = expoPort;
				if (ports != null && ports.getBindings() != null && !ports.getBindings().isEmpty()) {
					final Map<ExposedPort, Ports.Binding[]> bindings = ports.getBindings();
					// get binding and assign to port
					Ports.Binding[] bindedPorts = bindings.get(exposedPort);
					// Add exposed and binded port to model.
					String bindedPort = "";
					if (bindedPorts != null && bindedPorts.length > 0) {
						for (Ports.Binding bind : bindedPorts) {
							bindedPort = bind.getHostPortSpec().replace("[", "").replace("]", "");
							portFinal = portFinal + ":" + bindedPort;
						}
					}

				} else {
					// No bindings.
					// Get exposed ports only.
				}
				portsToAdd.add(portFinal);

			}
		}
		modelPorts.getValues().addAll(portsToAdd);

		String modelPortsStr = listToStringArrayWithSeparatorComma(modelPorts.getValues());

		modelContainer.setPorts(modelPortsStr);
		modelContainer.setMacAddress(currentContainer.getConfig().getMacAddress());
		modelContainer.setDomainName(currentContainer.getConfig().getDomainName());
		modelContainer.setOcciComputeHostname(currentContainer.getConfig().getHostName());
		modelContainer.setWorkingDir(currentContainer.getConfig().getWorkingDir());

		ArrayOfString modelEnv = DockerFactory.eINSTANCE.createArrayOfString();

		String[] dockerEnv = currentContainer.getConfig().getEnv();
		if (dockerEnv != null && dockerEnv.length > 0) {
			for (String dockEnv : dockerEnv) {
				modelEnv.getValues().add(dockEnv.replace("[", "").replace("]", ""));
			}
		}
		String dockerEnvStr = listToStringArrayWithSeparatorComma(modelEnv.getValues());
		modelContainer.setEnvironment(dockerEnvStr);
		String[] entryPoints = currentContainer.getConfig().getEntrypoint();
		if (entryPoints != null && entryPoints.length > 0) {
			String entryPoint = Arrays.toString(entryPoints).replace("[", "").replace("]", "");
			if (!entryPoint.contains("null")) {
				modelContainer.setEntrypoint(entryPoint);
			}
		}

		modelContainer.setTty(currentContainer.getConfig().getTty());
		modelContainer.setStdinOpen(currentContainer.getConfig().getStdinOpen());
		modelContainer.setPid(currentContainer.getProcessLabel());

		if (currentContainer.getState().getRunning()) {
			modelContainer.setOcciComputeState(ComputeStatus.ACTIVE);
		}
		if (currentContainer.getState().getPaused()) {
			modelContainer.setOcciComputeState(ComputeStatus.SUSPENDED);
		}
		if (currentContainer.getState().getDead()) {
			modelContainer.setOcciComputeState(ComputeStatus.INACTIVE);
		}

		return modelContainer;
	}

	/**
	 * From a list of String, result will be : myvalue1;myValue2;myValue3
	 * 
	 * @param myList
	 * @return a flat string with separator ";".
	 */
	public String listToStringArrayWithSeparatorComma(final List<String> myList) {
		if (myList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		String resultStr;
		for (String currentVal : myList) {
			result.append(currentVal);
			result.append(";");
		}
		resultStr = result.toString();
		if (resultStr.endsWith(";")) {
			// Remove lastest comma.
			resultStr = resultStr.substring(0, resultStr.length() - 1);
		}
		return resultStr;
	}

	/**
	 * 
	 * @param machine
	 * @param containerId
	 * @return
	 * @throws DockerException
	 */
	public Container buildContainer(Compute machine, String containerId) throws DockerException {
		DockerClientManager instance = new DockerClientManager(machine);
		InspectContainerResponse currentContainer = instance.inspectContainer(machine, containerId);
		Container modelContainer = DockerFactory.eINSTANCE.createContainer();

		currentContainer.getId();

		updateContainerModel(modelContainer, currentContainer);

		return modelContainer;
	}

	/**
	 * 
	 * @param vbox
	 * @param node
	 * @param state
	 */
	public void machineFactory(Machine vbox, JsonNode node, String state) {
		vbox.setName(node.get("Driver").get("MachineName").toString().replaceAll("\"", ""));
		vbox.setOcciComputeMemory(Float.parseFloat(node.get("Driver").get("Memory").toString()));
		vbox.setOcciComputeCores(Integer.parseInt(node.get("Driver").get("CPU").toString()));
		if (state.equalsIgnoreCase("Running")) {
			vbox.setOcciComputeState(ComputeStatus.ACTIVE);
		}
		if (state.equalsIgnoreCase("Stopped")) {
			vbox.setOcciComputeState(ComputeStatus.INACTIVE);
		}
	}

	public void machineFactory_VBOX(Machinevirtualbox vbox, JsonNode node, String state) {
		vbox.setName(node.get("Driver").get("MachineName").toString().replaceAll("\"", ""));
		vbox.setOcciComputeMemory(Float.parseFloat(node.get("Driver").get("Memory").toString()));
		vbox.setDiskSize(Integer.parseInt(node.get("Driver").get("DiskSize").toString()));
		vbox.setOcciComputeCores(Integer.parseInt(node.get("Driver").get("CPU").toString()));
		vbox.setBoot2dockerURL(node.get("Driver").get("Boot2DockerURL").toString().replaceAll("\"", ""));

		if (state.equalsIgnoreCase("Running")) {
			vbox.setOcciComputeState(ComputeStatus.ACTIVE);
		}
		if (state.equalsIgnoreCase("Stopped")) {
			vbox.setOcciComputeState(ComputeStatus.INACTIVE);
		}
	}

	public void machineFactory_Fusion(Machinevmwarefusion vbox, JsonNode node, String state) {
		vbox.setName(node.get("Driver").get("MachineName").toString().replaceAll("\"", ""));
		vbox.setOcciComputeMemory(Float.parseFloat(node.get("Driver").get("Memory").toString()));
		vbox.setDiskSize(Integer.parseInt(node.get("Driver").get("DiskSize").toString()));

		Integer cpuCores = 0;
		if (node.get("Driver").get("CPU") == null) {
			vbox.setOcciComputeCores(Integer.parseInt(node.get("Driver").get("CPUs").toString()));
		} else {
			vbox.setOcciComputeCores(Integer.parseInt(node.get("Driver").get("CPU").toString()));
		}
		vbox.setBoot2dockerURL(node.get("Driver").get("Boot2DockerURL").toString().replaceAll("\"", ""));
		if (state.equalsIgnoreCase("Running")) {
			vbox.setOcciComputeState(ComputeStatus.ACTIVE);
		}
		if (state.equalsIgnoreCase("Stopped")) {
			vbox.setOcciComputeState(ComputeStatus.INACTIVE);
		}
	}

	/**
	 * 
	 * @param vbox
	 * @param node
	 * @param state
	 */
	public void machineFactory_OpenStack(Machineopenstack vbox, JsonNode node, String state) {
		vbox.setName(node.get("Driver").get("MachineName").toString().replaceAll("\"", ""));
		vbox.setAuthUrl(node.get("Driver").get("AuthUrl").toString().replaceAll("\"", ""));
		vbox.setUsername(node.get("Driver").get("Username").toString().replaceAll("\"", ""));
		vbox.setPassword(node.get("Driver").get("Password").toString().replaceAll("\"", ""));
		vbox.setTenantName(node.get("Driver").get("TenantName").toString().replaceAll("\"", ""));
		vbox.setTenantId(node.get("Driver").get("TenantId").toString().replaceAll("\"", ""));
		vbox.setRegion(node.get("Driver").get("Region").toString().replaceAll("\"", ""));
		vbox.setEndpointType(node.get("Driver").get("EndpointType").toString().replaceAll("\"", ""));
		vbox.setFlavorId(node.get("Driver").get("FlavorId").toString().replaceAll("\"", ""));
		vbox.setFloatingIpPool(node.get("Driver").get("FloatingIpPool").toString().replaceAll("\"", ""));
		vbox.setImageId(node.get("Driver").get("ImageId").toString().replaceAll("\"", ""));
		vbox.setNetId(node.get("Driver").get("NetworkId").toString().replaceAll("\"", ""));
		vbox.setSecGroups(node.get("Driver").get("SecurityGroups").toString().replaceAll("\\[\"", "")
				.replaceAll("\"\\]", "").replaceAll("\"", ""));

		if (state.equalsIgnoreCase("Running")) {
			vbox.setOcciComputeState(ComputeStatus.ACTIVE);
		}
		if (state.equalsIgnoreCase("Stopped")) {
			vbox.setOcciComputeState(ComputeStatus.INACTIVE);
		}

	}

	/**
	 * 
	 * @param container
	 * @param machine
	 */
	public void linkContainerToMachine(Container container, Compute machine) {

		// Retrieve the default factory singleton
		Contains contains = DockerFactory.eINSTANCE.createContains();

		// Add Container to the Contains
		contains.setTarget(container);
		machine.getLinks().add(contains);
	}

	/**
	 * 
	 * @param left
	 * @param right
	 */
	public void linkContainerToContainer(Container left, Container right) {

		// Retrieve the default factory singleton
		org.eclipse.cmf.occi.docker.Link links = DockerFactory.eINSTANCE.createLink();

		// Add Container to the Contains
		links.setTarget(right);

		// Link Container to another
		left.getLinks().add(links);
	}

	/**
	 * 
	 * @param container
	 * @param machine
	 */
	public void removeContainerFromMachine(Container container, Compute compute) {
		System.out.println("ModelHandler: remove container from machine model: " + container.getId() + " --> Machine : " + compute.getId());
		// Retrieve the default factory singleton
		Contains contains = DockerFactory.eINSTANCE.createContains();

		// Add Container to the Contains
		contains.setTarget(container);
		compute.getLinks().remove(contains);
	}

}
