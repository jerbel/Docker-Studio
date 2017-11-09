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
import org.eclipse.cmf.occi.docker.Machine;
import org.eclipse.cmf.occi.docker.connector.ContainerConnector;
import org.eclipse.cmf.occi.docker.connector.observer.ContainerObserver;
import org.eclipse.cmf.occi.docker.connector.observer.MachineObserver;
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

		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(resource.eResource().getResourceSet());
		Command cmd = new RecordingCommand(domain) {

			@Override
			protected void doExecute() {
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
					// TODO : Attach observer to the Container object.
					final ModelHandler instanceMH = new ModelHandler();
					Machine machine = ((ContainerConnector)resource).getCurrentMachine();
					Container c = instanceMH.buildContainer(machine, containerId);
					// Attach listener to the new container created
					ContainerObserver observer = new ContainerObserver();
					observer.listener(c, machine);
					instanceMH.linkContainerToMachine(c, machine);
					if (machine.eContainer() instanceof Configuration) {
						((Configuration) machine.eContainer()).getResources().add((ContainerConnector) c);
						LOGGER.info("Load new container model");
					}
				}
				if (state.equalsIgnoreCase("destroy")) {
					LOGGER.warn("Container destroyed");
					final ModelHandler instanceMH = new ModelHandler();
					Container container = (Container) resource;
					Machine machine = ((ContainerConnector)resource).getCurrentMachine();
					ContainerObserver observer = container.getObserver();
					observer.removeListener(container);
					instanceMH.removeContainerFromMachine(container, machine);
					if (machine.eContainer() instanceof Configuration) {
						((Configuration) machine.eContainer()).getResources().remove((ContainerConnector) container);
						LOGGER.info("Destroy a container");
					}
				}
			}
		};
		try {
			((TransactionalCommandStack) domain.getCommandStack()).execute(cmd, null); // default options.
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
		LOGGER.info("Received event #{}",
				item.getAction() + " from : " + item.getFrom() + " status : " + item.getStatus());

		// Get the machine that contains this container.
		Compute compute = this.container.getCompute();

		if (compute != null && compute.getOcciComputeState().equals(ComputeStatus.ACTIVE)) {
			// Apply modification only when the machine is active
			EList<Link> links = compute.getLinks();
			LOGGER.warn("Links size : ", links.size());
			for (Link link : links) {
				Contains contains;
				// TODO : To refactor... Check if this is necessary to update all containers in
				// Machine.
				if (link instanceof Contains && link.getTarget() instanceof Container) {
					// contains = (Contains) link;
					// Update all the container status on this compute.
					Container containerComp = (Container) link.getTarget();
					// Check between event id and container id of this current container.
					if (containerComp.getContainerid().equals(item.getId())) {
						if (item.getStatus().equalsIgnoreCase("stop")) {
							modifyResourceSet(containerComp, item.getStatus(), item.getId());
							LOGGER.info("Apply stop notification to model.");
						}
						if (item.getStatus().equalsIgnoreCase("start")) {
							modifyResourceSet(containerComp, item.getStatus(), item.getId());
							LOGGER.info("Apply start notification to model.");
						}
						if (item.getStatus().equalsIgnoreCase("destroy")) {
							modifyResourceSet(containerComp, item.getStatus(), item.getId());
							LOGGER.info("Apply destroy notification to model.");
						}

					} else {
						// TODO : uncomment the following test.
						// if (item.getStatus().equalsIgnoreCase("create") &&
						// !container.getDockerClientManager.containerIsInsideMachine(compute,
						// item.getId())) {
						// modifyResourceSet(containerComp, item.getStatus(), item.getId());
						// LOGGER.info("Apply create notification to model");
						// }
					}

				}
			}

		}

	}

}
