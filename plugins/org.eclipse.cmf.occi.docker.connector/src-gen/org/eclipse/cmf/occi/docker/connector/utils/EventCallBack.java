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

import org.eclipse.cmf.occi.core.Configuration;
import org.eclipse.cmf.occi.core.Link;
import org.eclipse.cmf.occi.core.Resource;
import org.eclipse.cmf.occi.docker.Container;
import org.eclipse.cmf.occi.docker.Contains;
import org.eclipse.cmf.occi.docker.connector.ContainerConnector;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.docker.connector.manager.DockerClientManager;
import org.eclipse.cmf.occi.docker.connector.observer.ContainerObserver;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.eclipse.cmf.occi.infrastructure.ComputeStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.model.Event;
import com.github.dockerjava.core.command.EventsResultCallback;

/**
 * Notifies new events to the container model connector.
 * 
 * @author Christophe Gourdin
 *
 */
public class EventCallBack extends EventsResultCallback {
	private static Logger LOGGER = LoggerFactory.getLogger(EventCallBack.class);

	protected DockerClientManager dockerClient = null;

	private ContainerConnector container;

	public EventCallBack(ContainerConnector container) {
		this.container = container;
	}

	/**
	 * 
	 * @param resource
	 * @param state
	 * @param containerId
	 */
	public void modifyResourceSet(final Resource resource, final String state, final String containerId) {
		if (state == null) {
			System.out.println("Cant update container status, no state defined !");
			return;
		}
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(resource.eResource().getResourceSet());
		Command cmd = new RecordingCommand(domain) {

			@Override
			protected void doExecute() {
				// try {
					// Containers status possible : created, restarting, running, removing, paused,
					// exited, or dead
					// these modifications require a write transaction in this editing domain
					if (state.equalsIgnoreCase("stop")) {
						LOGGER.warn("container stopped");
						((Compute) resource).setOcciComputeState(ComputeStatus.INACTIVE);
					}
					if (state.equalsIgnoreCase("start")) {
						LOGGER.warn("container started");
						((Compute) resource).setOcciComputeState(ComputeStatus.ACTIVE);
					}
					if (state.equalsIgnoreCase("create")) {
						LOGGER.warn("container created");
						
						// final ModelHandler instanceMH = new ModelHandler();
						// Compute compute = ((ContainerConnector) resource).getCompute();
						// Container c = instanceMH.buildContainer(compute, containerId);

						// Attach listener to the new container created
						// ContainerObserver observer = new ContainerObserver();
						// observer.listener(c, compute);
						// ((ContainerConnector) c).setContainerObserver(observer);
						// instanceMH.linkContainerToMachine(c, compute);
						// if (compute.eContainer() instanceof Configuration) {
						//	((Configuration) compute.eContainer()).getResources().add((ContainerConnector) c);
						//	System.out.println("Load new container model");
						// }
					}
					if (state.equalsIgnoreCase("destroy")) {
						LOGGER.warn("Container destroyed");
						((Compute) resource).setOcciComputeState(ComputeStatus.INACTIVE);
						// final ModelHandler instanceMH = new ModelHandler();
						// Container container = (Container) resource;
						// Compute compute = ((ContainerConnector) resource).getCompute();
						// ContainerObserver observer = ((ContainerConnector) container).getObserver();
						
						// if (observer != null) {
						//	observer.removeListener(container);
						//	((ContainerConnector) container).setContainerObserver(null);
						// }
						
						// instanceMH.removeContainerFromMachine(container, compute);
						// if (compute.eContainer() instanceof Configuration) {
						//	((Configuration) compute.eContainer()).getResources()
						//			.remove((ContainerConnector) container);
						//	System.out.println("Destroy a container");
						// }
					}
				// } catch (DockerException ex) {
				//	LOGGER.error("Exception thrown : " + ex.getMessage());
				//	ex.printStackTrace();
				// }
			}
		};
		try {
			if(domain!= null) {
				TransactionalCommandStack tcs = (TransactionalCommandStack) domain.getCommandStack();
				tcs.execute(cmd, null);		
			}
			// default options.
		} catch (RollbackException rbe) {
			LOGGER.error(rbe.getStatus().toString());
			rbe.printStackTrace();
		} catch (InterruptedException ex) {
			LOGGER.error(ex.getMessage());
			ex.printStackTrace();
		}

	}

	@Override
	public void onNext(Event item) {
		//System.out.println("Received event #" + item.getAction() + " from : " + item.getFrom() + " status : " + item.getStatus());
		if (item.getStatus() == null) {
			return;
		}
		// Get the machine that contains this container.
		Compute compute = this.container.getCompute();

		if (compute != null && compute.getOcciComputeState().equals(ComputeStatus.ACTIVE)) {
			// Apply modification only when the machine is active
			EList<Link> links = compute.getLinks();
			LOGGER.warn("Links size : ", links.size());
			for (Link link : links) {
				if (link instanceof Contains) {
					Contains contains = (Contains) link;
					// TODO : To refactor... Check if this is necessary to update all containers in
					// Machine.
					if (link instanceof Contains && link.getTarget() instanceof Container) {
						// contains = (Contains) link;
						// Update all the container status on this compute.
						Container containerComp = (Container) link.getTarget();
						// Check between event id and container id of this current container.

						if (containerComp != null && containerComp.getContainerid() != null
								&& this.container.getContainerid() != null
								&& containerComp.getContainerid().equals(this.container.getContainerid())) {
							if (item.getStatus().equalsIgnoreCase("stop")) {
								modifyResourceSet(containerComp, item.getStatus(), item.getId());
								System.out.println("Apply stop notification to model.");
							}
							if (item.getStatus().equalsIgnoreCase("start")) {
								modifyResourceSet(containerComp, item.getStatus(), this.container.getContainerid());
								System.out.println("Apply start notification to model.");
							}
							if (item.getStatus().equalsIgnoreCase("destroy")) {
								modifyResourceSet(containerComp, item.getStatus(), null);
								System.out.println("Apply destroy notification to model.");
							}

						} else {
							try {
								if (this.dockerClient == null) {
									this.dockerClient = new DockerClientManager(compute);
								}

								if (item.getStatus().equalsIgnoreCase("create") && !dockerClient
										.containerIsInsideMachine(compute, this.container.getContainerid())) {
									modifyResourceSet(contains.getTarget(), item.getStatus(),
											this.container.getContainerid());
									System.out.println("Apply create notification to model");
								}
							} catch (DockerException ex) {
								LOGGER.error("Exception thrown : " + ex.getMessage());
								ex.printStackTrace();
							}
						}

					}
				}
			}
		}

	}

}
