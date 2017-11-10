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
package org.eclipse.cmf.occi.docker.connector.observer;

import org.eclipse.cmf.occi.docker.Container;
import org.eclipse.cmf.occi.docker.Machine;
import org.eclipse.cmf.occi.docker.connector.DockerClientManager;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.eclipse.cmf.occi.infrastructure.ComputeStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Observe the docker machine states and update attributes if it change.
 * 
 * @author Christophe Gourdin
 *
 */
public class MachineObserver {
	private static Logger LOGGER = LoggerFactory.getLogger(MachineObserver.class);
	protected static Compute cpMachine = null;

	/**
	 * Listener for Compute machine only (not the containers).
	 * 
	 * @param compute
	 * @return
	 * @throws DockerException
	 */
	public Compute listener(Compute compute) throws DockerException {

		// Make a reference copy.
		cpMachine = EcoreUtil.copy(compute);

		// ADD listener to Class
		compute.eAdapters().add(new EContentAdapter() {

			@Override
			public void notifyChanged(Notification notification) {
				// ID of the element deleted
				Container deletedElement = null;
				String machineName = null;

				if (notification.getNotifier() instanceof Machine) {
					machineName = ((Machine) cpMachine).getName();
				} else if (notification.getNotifier() instanceof Compute) {
					machineName = cpMachine.getTitle();
				} else {
					LOGGER.warn("Notifier is not on a compute machine !");
					return;
				}
				Compute newMachine = (Compute) notification.getNotifier();
				String newMachineName = null;

				if (newMachine instanceof Machine) {
					newMachineName = ((Machine) newMachine).getName();
				} else {
					newMachineName = newMachine.getTitle();
				}

				// Name Changes
				// The compute must be stopped or suspended before renaming.
				if (!machineName.equals(newMachineName)
						&& newMachine.getOcciComputeState().equals(ComputeStatus.ACTIVE)) {
					if (compute instanceof Machine) {
						((Machine) compute).setName(notification.getOldValue().toString());
					} else {
						compute.setTitle(notification.getOldValue().toString());
					}
					// Throw an exception
					throw new UnsupportedOperationException();
				}
				try {
					if (notification.getEventType() == Notification.SET
							&& notification.getOldValue() instanceof Container) {
						deletedElement = (Container) notification.getOldValue();
						// Notify the deleted element in the model
						LOGGER.info("Deleted model element with ID: {}", deletedElement.getContainerid());

						// Remove the container from the machine
						DockerClientManager dockerClient = new DockerClientManager(compute);

						if (dockerClient.containerNameExists(deletedElement.getName(), compute)) {
							dockerClient.removeContainer(compute, deletedElement);
						}
					}
				} catch (DockerException ex) {
					LOGGER.error("Exception thrown : " + ex.getMessage());
					ex.printStackTrace();
				}
				LOGGER.info("Old value : " + notification.getOldValue());
				LOGGER.info("New value : " + notification.getNewValue());
			}

		});

		return compute;
	}

}
