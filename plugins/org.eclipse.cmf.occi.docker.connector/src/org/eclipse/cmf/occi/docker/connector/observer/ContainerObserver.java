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
import org.eclipse.cmf.occi.docker.connector.ContainerConnector;
import org.eclipse.cmf.occi.docker.connector.DockerClientManager;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.docker.connector.helpers.CpuManager;
import org.eclipse.cmf.occi.docker.connector.helpers.DockerMachineHelper;
import org.eclipse.cmf.occi.docker.connector.helpers.MemoryManager;
import org.eclipse.cmf.occi.docker.connector.helpers.NetworkManager;
import org.eclipse.cmf.occi.docker.connector.utils.DockerUtil;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.eclipse.cmf.occi.infrastructure.ComputeStatus;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Observe the containers within machines and check states and attributes,
 * create new ones if they are created manually.
 * 
 * @author Christophe Gourdin
 *
 */
public class ContainerObserver {

	private static Logger LOGGER = LoggerFactory.getLogger(ContainerObserver.class);
	protected static Container cpContainer = null;

	public Container listener(Container container, Compute compute) throws DockerException {

		System.out.println("Enable listener on " + container.getContainerid() + " --< " + container.getName());
		// Make a reference copy

		cpContainer = EcoreUtil.copy(container);
		String machineName = null;

		if (compute instanceof Machine) {
			machineName = ((Machine) compute).getName();
		} else {
			machineName = compute.getTitle();
		}
		String privateKey = DockerUtil.getEnv(machineName) + "/" + "id_rsa";

		String host = DockerMachineHelper.ipCmd(Runtime.getRuntime(), machineName);
		CpuManager cpuManager = new CpuManager();
		Elasticity elasticity = new Elasticity();

		// Add listener to the machine
		MachineObserver machineObserver = new MachineObserver();

		machineObserver.listener(compute);

		// Add listener to the container
		container.eAdapters().add(new EContentAdapter() {
			@Override
			public void notifyChanged(Notification notification) {
				// ID of the element deleted
				Container deletedElement = null;
				Container newContainer = null;
				try {
					if (notification.getEventType() == Notification.REMOVE
							&& notification.getNotifier() instanceof Container) {

						deletedElement = (Container) notification.getNotifier();
						// Notify the deleted element in the model
						System.out.println("Delete model element with ID: " + deletedElement.getContainerid());
						// Remove the container from the machine
						// DockerClientManager dockerManager = new DockerClientManager(compute);
						// dockerManager.removeContainer(compute, deletedElement.getContainerid());
					}

					if (notification.getNotifier() instanceof Container) {
						newContainer = (Container) notification.getNotifier();

						// Elasticity method
						elasticity.action(cpuManager, host, privateKey, (ContainerConnector) newContainer);

						// When the container name's Changes
						if (cpContainer.getContainerid().equals(newContainer.getContainerid())
								&& cpContainer.getOcciComputeState().equals(ComputeStatus.ACTIVE)) {

							// The container name changed
							if (!cpContainer.getName().equals(newContainer.getName())) {
								DockerClientManager dockerManager = new DockerClientManager(compute);

								if (!dockerManager.containerNameExists(newContainer.getName(), compute)) {
									dockerManager.renameContainer(compute, newContainer, newContainer.getName());
								}
							}

							// CPU Changes
							if (cpContainer.getOcciComputeCores() != newContainer.getOcciComputeCores()) {
								// Update CPU value
								cpContainer.setOcciComputeCores(container.getOcciComputeCores());
								cpuManager.setCPUValue(host, privateKey, newContainer,
										String.valueOf(newContainer.getOcciComputeCores()));
							}

							// CPU Frequency Changes
							if (cpContainer.getOcciComputeSpeed() != newContainer.getOcciComputeSpeed()) {
								// Update CPU value
								cpContainer.setOcciComputeCores(container.getOcciComputeCores());
								cpuManager.setFreqValue(host, privateKey, newContainer,
										String.valueOf(Math.round(newContainer.getOcciComputeSpeed())));
							}

							// Memory changes
							if (cpContainer.getOcciComputeMemory() != newContainer.getOcciComputeMemory()) {
								MemoryManager memoryManager = new MemoryManager();
								// Update Memory value
								cpContainer.setOcciComputeMemory(container.getOcciComputeMemory());
								memoryManager.setMemValue(host, privateKey, newContainer,
										String.valueOf(newContainer.getOcciComputeMemory()));
								// memoryManager.setSwapValue(host, privateKey, newContainer,
								// String.valueOf(newContainer.memory))
							}
							
							// Bandwidth changes
							if (cpContainer.getBandwidthUsed() != newContainer.getBandwidthUsed()) {
								NetworkManager networkManager = new NetworkManager();
								// Update Memory value
								cpContainer.setBandwidthUsed(container.getBandwidthUsed());
								networkManager.setNetworkValue(host, privateKey, newContainer,
										String.valueOf(newContainer.getBandwidthUsed()));
							}

						}

						// System.out.println("Old value : " + notification.oldValue);
						// System.out.println("New value : " + notification.newValue);

					}
				} catch (DockerException ex) {
					LOGGER.error(ex.getMessage());
				}

			}
		});

		return container;
	}

	/**
	 * Remove the notifier (listener) previously assigned to this container.
	 * 
	 * @param container
	 * @param compute
	 * @throws DockerException
	 */
	public void removeListener(Container container) throws DockerException {
		System.out.println("Remove listener from container : " + container.getId() + " --> " + container.getName());
		EContentAdapter eAdapterToRemove = null;
		for (Adapter eAdapter : container.eAdapters()) {

			if (eAdapter instanceof EContentAdapter) {
				eAdapterToRemove = (EContentAdapter) eAdapter;
				break;
			}

		}
		if (eAdapterToRemove != null) {
			container.eAdapters().remove(eAdapterToRemove);
			System.out.println("Listener from container : " + container.getId() + " --> " + container.getName() + " is removed !");
		}
	}

}
