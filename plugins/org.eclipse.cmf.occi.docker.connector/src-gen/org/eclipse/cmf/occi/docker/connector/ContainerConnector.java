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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.cmf.occi.core.Link;
import org.eclipse.cmf.occi.docker.Contains;
import org.eclipse.cmf.occi.docker.DockerPackage;
import org.eclipse.cmf.occi.docker.Machine;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.docker.connector.manager.ComputeStateMachine;
import org.eclipse.cmf.occi.docker.connector.manager.DockerClientManager;
import org.eclipse.cmf.occi.docker.connector.observer.ContainerObserver;
import org.eclipse.cmf.occi.docker.connector.observer.StatsCallBack;
import org.eclipse.cmf.occi.docker.connector.utils.EventCallBack;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.eclipse.cmf.occi.infrastructure.ComputeStatus;
import org.eclipse.cmf.occi.infrastructure.RestartMethod;
import org.eclipse.cmf.occi.infrastructure.StopMethod;
import org.eclipse.cmf.occi.infrastructure.SuspendMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.google.common.collect.Multimap;

/**
 * Connector implementation for the OCCI kind: - scheme:
 * http://occiware.org/occi/docker# - term: container - title: Container
 * Resource
 */
public class ContainerConnector extends org.eclipse.cmf.occi.docker.impl.ContainerImpl {
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(ContainerConnector.class);

	/**
	 * Notifier change observer.
	 */
	private ContainerObserver containerObserver = null;

	private EventCallBack eventCallBack = new EventCallBack(this);
	/**
	 * Manage statistics from monitoring.
	 */
	private StatsCallBack statsCallBack = new StatsCallBack(this);

	private Map<DockerClient, CreateContainerResponse> map = null;

	protected static DockerClientManager dockerClientManager = null;

	// This is a cache of containers current machine
	protected static Map<String, Machine> listCurrentMachine = new HashMap<>();
	
	private String ipaddress;

	public ComputeStateMachine<org.eclipse.cmf.occi.docker.Container> stateMachine = new ComputeStateMachine<org.eclipse.cmf.occi.docker.Container>(
			this) {
		@Override
		public void start_execute() throws DockerException {
			System.out.println("EXECUTE container start");
			Compute machine = getCompute();

			if (machine.getOcciComputeState().equals(ComputeStatus.ACTIVE)) {
				try {
					/*
					 * In the previous implementation the DockerClientManager instance was null
					 * checked and only created if not existent. This had the flaw, that when a
					 * container with a different machine was previously created, the same
					 * DockerClientManager instance was used leading to the wrong connection being
					 * used for configuring the container. Therefore the dockerClientManager has to
					 * be recreated for the Container with the right machine instance every time
					 * when it is started.
					 */
					dockerClientManager = new DockerClientManager(machine, eventCallBack);

					System.out.println("Command to execute : " + getCommand());
					/**
					 * This block was commented out due to debugging in the Martserver environment
					 * (IllegalStateException "Connection is still allocated" was thrown otherwise)
					 */
					// First check if container already exist.
					if (!dockerClientManager.containerIsInsideMachine(machine, this.compute)) {
						// Create the container..
						createContainer(machine);
					}

					dockerClientManager.startContainer(machine, this.compute, getStatsCallBack());
				} catch (Exception e) {
					throw new DockerException("Exception thrown while starting container " + getName(), e);
				}
			} else {
				System.out.println("Host is suspended or inactive.");
				throw new DockerException("Host machine is suspended or is inactive");
			}
		}

		/**
		 * Stop the Docker container.
		 */
		@Override
		public void stop_execute(StopMethod method) throws DockerException {
			System.out.println("EXECUTE container stop");
			Compute machine = getCompute();
			try {
				if (machine.getOcciComputeState().equals(ComputeStatus.ACTIVE)
						&& this.compute.getOcciComputeState().equals(ComputeStatus.ACTIVE)) {

					if (dockerClientManager == null) {
						dockerClientManager = new DockerClientManager(machine, eventCallBack);
					}
					// Check if this container is already stopped before stopping.
					ComputeStatus status = dockerClientManager.getCurrentContainerStatus(machine, this.compute);
					if (status.equals(ComputeStatus.ACTIVE) || status.equals(ComputeStatus.SUSPENDED)) {
						dockerClientManager.stopContainer(machine, this.compute);
					}
				} else {
					System.out.println("Already stopped");
				}
			} catch (DockerException ex) {
				ex.printStackTrace();
				throw new DockerException(ex.getMessage(), ex.getCause());
			}
		}

		/**
		 * Restart the Docker container.
		 */
		@Override
		public void restart_execute(RestartMethod method) throws DockerException {
			System.out.println("EXECUTE container restart");
			stop_execute(StopMethod.GRACEFUL);
			start_execute();
		}

		/**
		 * Suspend the Docker container.
		 */
		@Override
		public void suspend_execute(SuspendMethod method) throws DockerException {
			System.out.println("EXECUTE container suspend");
			stateMachine.suspend(method);

		}

	};

	// Start of user code Containerconnector_constructor
	/**
	 * Constructs a container connector.
	 */
	ContainerConnector() {
		LOGGER.info("Constructor called on " + this);
		// TODO: Implement this constructor.
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//

	// Start of user code ContainerocciCreate
	/**
	 * Called when this Container instance is completely created.
	 */
	@Override
	public void occiCreate() {
		LOGGER.info("occiCreate() called on " + this);
		/*
		 * Existence of compute node has to be checked because for the deployment
		 * process of occi models in the Martserver. Ther instances are connected after
		 * they are created. That means container haven't been connected with their
		 * compute node when the occiCreate() Method is called.
		 */
		if (hasCompute()) {
			LOGGER.info("Container has a compute node");
			try {
				Compute machine = getCompute();
				if (!checkHostMachineStarted()) {
					machine.start();
				}
				this.createContainer(getCompute());
				containerObserver = new ContainerObserver();
				// Ensure that observer is not set.
				containerObserver.removeListener(this);
				containerObserver.listener(this, getCompute());

			} catch (DockerException ex) {
				LOGGER.error("Exception thrown while creating a container : " + this.getName());
				ex.printStackTrace();
			}
		} else {
			LOGGER.info("Container has no compute node");
		}
	}

	private boolean hasCompute() {
		return getCompute() != null;
	}
	// End of user code

	// Start of user code Container_occiRetrieve_method
	/**
	 * Called when this Container instance must be retrieved.
	 */
	@Override
	public void occiRetrieve() {
		LOGGER.info("occiRetrieve() called on " + this);
		if (hasCompute()) {
			if (!checkHostMachineStarted()) {
				throw new RuntimeException("Host machine is not started !");
			}
			if (dockerClientManager == null) {
				try {
					dockerClientManager = new DockerClientManager(getCompute());
					// dockerClientManager.retrieveAndUpdateContainerModel(getCompute(), this);

				} catch (DockerException ex) {
					LOGGER.error("Exception thrown while building docker client with compute: " + getCompute()
							+ " for container : " + this.getName());
					ex.printStackTrace();
				}
			}
		}
		// Retrieve infos...
		try {
			if (dockerClientManager != null)
				dockerClientManager.retrieveAndUpdateContainerModel(getCompute(), this);
			else
				LOGGER.warn("Failed to retrive infos from the container. dockerClientManager wasn't initialized!");
		} catch (DockerException ex) {
			LOGGER.error("Exception thrown while retrieving docker container on compute: " + getCompute()
					+ " for container : " + this.getName());
			ex.printStackTrace();
		}
	}
	// End of user code

	// Start of user code Container_occiUpdate_method
	/**
	 * Called when this Container instance is completely updated.
	 */
	@Override
	public void occiUpdate() {
		LOGGER.info("occiUpdate() called on " + this);
		if (!checkHostMachineStarted()) {
			throw new RuntimeException("Host machine is not started !");
		}
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code ContainerocciDelete_method
	/**
	 * Called when this Container instance will be deleted.
	 */
	@Override
	public void occiDelete() {
		LOGGER.info("occiDelete() called on " + this);
		try {
			/*
			 * If the container is still connected to a compute node the normal deletion
			 * process is started. If it doesn't there are two possible cases: 
			 * - the container was placed on the local docker host: 
			 *    -> the local deletion process has to be started 
			 * - the container was placed on a remote docker host: 
			 *    -> assuming that following the smartwyrm deletion process the remote docker host
			 *       was deleted before and with it the container, no additional deletion process
			 * 		 has to be started.
			 */
			if (hasCompute()) {
				Compute machine = getCompute();
				if (!checkHostMachineStarted()) {
					machine.start();
				}
				removeContainer(getCompute());
			} else {
				if(dockerClientManager != null) {
					if (dockerClientManager.isOnLocalHost()) {
						removeLocalContainer();
					}
				}
			}

			if (containerObserver != null) {
				containerObserver.removeListener(this);
			}

		} catch (DockerException ex) {
			LOGGER.error("Error thrown while deleting the container : " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	// End of user code

	@Override
	public void start() {
		LOGGER.info("start() called on " + this);
		try {
			Compute machine = getCompute();
			if (!checkHostMachineStarted()) {
				machine.start();
			}

			stateMachine.start();

			if (containerObserver == null && machine.getOcciComputeState().equals(ComputeStatus.ACTIVE)) {
				// Add listener here
				containerObserver = new ContainerObserver();
				// Ensure that observer is not set.
				containerObserver.removeListener(this);
				containerObserver.listener(this, machine);
			}

			this.ipaddress = retrieveIPAddress();

		} catch (DockerException ex) {
			LOGGER.error("Exception thrown while starting container : " + ex.getMessage());
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void stop(StopMethod method) {
		LOGGER.info("stop() called on " + this);
		try {
			Compute machine = getCompute();
			if(dockerClientManager != null) {
				if (!dockerClientManager.isOnLocalHost()) {
					if (!checkHostMachineStarted()) {
						machine.start();
					}
					stateMachine.stop(method);
				} else {
					dockerClientManager.stopLocalContainer(this);
				}
			}
		} catch (DockerException ex) {
			LOGGER.error("Exception thrown while stopping container : " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Override
	public void restart(RestartMethod method) {
		LOGGER.info("restart() called on " + this);
		try {
			Compute machine = getCompute();
			if (!checkHostMachineStarted()) {
				machine.start();
			}
			stateMachine.restart(method);
		} catch (DockerException ex) {
			LOGGER.error("Exception thrown while restarting container : " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Override
	public void suspend(SuspendMethod method) {
		LOGGER.info("suspend() called on " + this);
		try {
			Compute machine = getCompute();
			if (!checkHostMachineStarted()) {
				machine.start();
			}
			stateMachine.suspend(method);
		} catch (DockerException ex) {
			LOGGER.error("Exception thrown while suspending container : " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	//
	// Container actions.
	//
	// Start of user code Container_Kind_create_action
	/**
	 * Implement OCCI action: - scheme:
	 * http://occiware.org/occi/docker/container/action# - term: create - title:
	 */
	@Override
	public void create() {
		LOGGER.info("create() called on " + this);

		/*
		 * try { Compute machine = getCompute(); if (!checkHostMachineStarted()) {
		 * machine.start(); } this.createContainer(getCompute()); // Ensure that
		 * observer is not set. containerObserver.removeListener(this);
		 * containerObserver.listener(this, machine); } catch (DockerException ex) {
		 * LOGGER.error("Exception thrown while creating a container : " +
		 * this.getName()); ex.printStackTrace(); }
		 */
		// TODO: Implement how to create this container.
	}
	// End of user code

	// Start of user code Container_Kind_stop_action
	/**
	 * Implement OCCI action: - scheme:
	 * http://occiware.org/occi/docker/container/action# - term: stop - title:
	 */
	@Override
	public void stop() {
		LOGGER.info("stop() called on " + this);
		Compute machine = getCompute();
		if (!dockerClientManager.isOnLocalHost()) {
			if (!checkHostMachineStarted()) {
				machine.start();
			}
		}
		stop(StopMethod.GRACEFUL);
	}
	// End of user code

	// Start of user code Container_Kind_run_action
	/**
	 * Implement OCCI action: - scheme:
	 * http://occiware.org/occi/docker/container/action# - term: run - title:
	 */
	@Override
	public void run() {
		LOGGER.info("run() called on " + this);
		start();
	}
	// End of user code

	// Start of user code Container_Kind_pause_action
	/**
	 * Implement OCCI action: - scheme:
	 * http://occiware.org/occi/docker/container/action# - term: pause - title:
	 */
	@Override
	public void pause() {
		LOGGER.info("pause() called on " + this);
		suspend(SuspendMethod.SUSPEND);
	}
	// End of user code

	// Start of user code Container_Kind_unpause_action
	/**
	 * Implement OCCI action: - scheme:
	 * http://occiware.org/occi/docker/container/action# - term: unpause - title:
	 */
	@Override
	public void unpause() {
		LOGGER.info("unpause() called on " + this);
		start();
	}
	// End of user code

	// Start of user code Container_Kind_kill_action
	/**
	 * Implement OCCI action: - scheme:
	 * http://occiware.org/occi/docker/container/action# - term: kill - title:
	 */
	@Override
	public void kill() {
		String signal = "9";
		LOGGER.info("kill(" + "signal=" + signal + ") called on " + this);
		Compute machine = getCompute();
		if (!checkHostMachineStarted()) {
			machine.start();
		}
		if (dockerClientManager == null) {
			dockerClientManager = new DockerClientManager();
		}

		if (containerid == null) {
			occiRetrieve();
		}
		if (containerid != null) {
			try {
				dockerClientManager.killContainer(getCompute(), getContainerid());
				if (containerObserver != null) {
					containerObserver.removeListener(this);
				}
			} catch (DockerException ex) {
				LOGGER.error("Cant kill the container : " + getName() + " --> " + ex.getMessage());
				ex.printStackTrace();
			}
		} else {
			System.out.println("The container : " + getName() + " doesnt exist.");
		}
	}

	// End of user code

	/**
	 * Get the compute that contains this container.
	 * 
	 * @return the Compute that contains this container. Null if the container is
	 *         executed locally (on linux only).
	 */
	public Compute getCompute() {
		Compute compute = null;
		for (Link link : this.getRlinks()) {
			if (link instanceof Contains && link.getSource() instanceof Machine) {
				compute = (Compute) link.getSource();
				break;
			} else {

			}
		}
		return compute;
	}

	public ContainerObserver getObserver() {
		return this.containerObserver;
	}

	/**
	 * Create the container within compute machine.
	 * 
	 * @param machine
	 * @throws DockerException
	 */
	public void createContainer(Compute machine) throws DockerException {
		if (dockerClientManager == null) {
			dockerClientManager = new DockerClientManager(machine, eventCallBack);
		}

		// Download image
		dockerClientManager.pullImage(machine, this.image);

		// Create the container
		dockerClientManager.createContainer(machine, this);

	}

	/**
	 * 
	 * @param machine
	 * @param containerDependency
	 * @return
	 * @throws DockerException
	 */
	public Map<DockerClient, CreateContainerResponse> createContainer(Compute machine,
			Multimap<String, String> containerDependency) throws DockerException {

		// Set dockerClient
		Map<DockerClient, CreateContainerResponse> result = new HashMap<>();
		if (dockerClientManager == null) {
			dockerClientManager = new DockerClientManager(machine, eventCallBack);
		}

		// Download image
		dockerClientManager.pullImage(machine, this.image);

		result = dockerClientManager.createContainer(machine, this, containerDependency);
		this.map = new HashMap<DockerClient, CreateContainerResponse>(result);
		return result;
	}

	/**
	 * Remove container from compute machine.
	 * 
	 * @param machine a compute.
	 */
	public void removeContainer(Compute machine) throws DockerException {
		if (dockerClientManager == null) {
			dockerClientManager = new DockerClientManager(machine, eventCallBack);
		}
		// Inspect the container to get container id and state.
		occiRetrieve();
		if (this.getOcciComputeState().equals(ComputeStatus.ACTIVE)) {
			stop(); // Stop the container before deleting it.
		}

		if (this.getContainerid() != null) {
			dockerClientManager.removeContainer(machine, this.getContainerid());
			if (containerObserver != null) {
				containerObserver.removeListener(this);
			}
			this.containerObserver = null;
		} else {
			LOGGER.warn("Container : + " + this.getName() + " + is already removed.");
		}

	}

	public void removeLocalContainer() {
		/*
		 * The deletion process of the smartwyrm deletes compute node before containers.
		 * For containers hosted on these compute being remote machines this isn't a
		 * problem because with the machine the container is removed as well. But for
		 * Containers hosted on the local docker host the deletion process has to be
		 * adapted in the way that it works without an connected compute node.
		 */
		LOGGER.info("Alternativ container deletion process is started");
		occiRetrieve();
		if (this.getOcciComputeState().equals(ComputeStatus.ACTIVE)) {
			LOGGER.info("The container is active and has to be stopped");
			stop(); // Stop the container before deleting it.
		} else {
			LOGGER.info("The container is inactive");
		}

		if (dockerClientManager != null) {
			if (dockerClientManager.isOnLocalHost()) {
				LOGGER.info("The deletion process for a local docker host is initialized");
				if (getContainerid() != null) {
					dockerClientManager.removeLocalContainer(getContainerid());
				} else {
					LOGGER.warn("The deletion process can't be proceeded because the container id is null");
				}
			} else {
				LOGGER.info(
						"No deletion process could be applied because the container has no machine and isn't connected to the local docker host!");
			}
		} else {
			LOGGER.warn("The deletion process can't be proceeded because the dockerClientManager is null!");
		}
	}

	/**
	 * 
	 * @param observer
	 */
	public void setContainerObserver(ContainerObserver observer) {
		this.containerObserver = observer;
	}

	/**
	 * 
	 * @param machine
	 * @return
	 */
	public Compute linkContainerToMachine(Compute machine) {
		LOGGER.info("Method linkContainerToMachine() called on ContainerConnector with compute node " + machine);
		Contains contains = DockerPackage.eINSTANCE.getDockerFactory().createContains();
		contains.setTarget(this);
		contains.setSource(machine);
		machine.getLinks().add(contains);
		return machine;
	}

	public StatsCallBack getStatsCallBack() {
		return statsCallBack;
	}

	public void setStatsCallBack(StatsCallBack statsCallBack) {
		this.statsCallBack = statsCallBack;
	}

	/**
	 * Check if the compute machine is activated.
	 * 
	 * @return true if host machine is activated else false.
	 */
	public boolean checkHostMachineStarted() {

		if (!hasCompute())
			// Adding additional info for debugging purpose
			throw new NullPointerException("Container has no Compute node!");
		if (getCompute().getOcciComputeState().equals(ComputeStatus.ACTIVE)) {
			return true;
		} else {
			return false;
		}
	}

	public String retrieveIPAddress() throws DockerException {
		return dockerClientManager.getContainerIP(this);
	}
}
