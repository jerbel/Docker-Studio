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
import org.eclipse.cmf.occi.docker.connector.utils.DockerUtil;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.eclipse.cmf.occi.infrastructure.ComputeStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Observe the containers within machines and check states and attributes, create new ones if they are created manually.
 * @author Christophe Gourdin
 *
 */
public class ContainerObserver {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ContainerObserver.class);
	protected static Container cpContainer = null;
	protected static Machine cpMachine = null;
	
	public Container listener(Container container, Compute compute) throws DockerException {
		
		LOGGER.info("Enable listener on {}", container.getContainerid() + " --< " + container.getName());
		// Make a reference copy
		
		cpContainer = EcoreUtil.copy(container);
		String machineName = null;
		
		if (compute instanceof Machine) {
			machineName = ((Machine)compute).getName();
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
		container.eAdapters().add(
			new EContentAdapter() {
				@Override
				public void notifyChanged(Notification notification) {
					// ID of the element deleted
					Container deletedElement = null;
					Container newContainer = null;
					if (notification.getEventType() == Notification.REMOVE &&
						notification.getNotifier() instanceof Container) {
							
						deletedElement = (Container)notification.getNotifier();
						// Notify the deleted element in the model 
						LOGGER.info("Delete model element with ID: {}", deletedElement.getContainerid());
						// Remove the container from the machine
						DockerClientManager dockerManager = new DockerClientManager(compute);
						dockerManager.removeContainer(compute, deletedElement.getContainerid());
					}
					
					if (notification.getNotifier() instanceof Container) {
						newContainer = (Container) notification.getNotifier();

						// Elasticity method
						elasticity.action(cpuManager, host, privateKey, (ContainerConnector) newContainer);
						
						// When the container name's Changes
						if (cpContainer.getContainerid().equals(newContainer.getContainerid()) &&
							cpContainer.getOcciComputeState().equals(ComputeStatus.ACTIVE)) {

							// The container name changed
							if (!cpContainer.getName().equals(newContainer.getName())) {
								DockerClientManager dockerManager = new DockerClientManager(compute);
								
								if (!containerNameExists(dockerManager, newContainer.getName(), compute)) {
									dockerManager.renameContainer(machine, newContainer.getContainerid(), newContainer.getName());
								}
							}
							
							// CPU Changes
							if (!cpContainer.cores.equals(newContainer.cores)) {
								// Update CPU value
								cpContainer.cores = container.cores;
								cpuManager.setCPUValue(host, privateKey, newContainer,
									String.valueOf(newContainer.cores));
							}

							// CPU Frequency Changes
							if (!cpContainer.speed.equals(newContainer.speed)) {
								// Update CPU value
								cpContainer.cores = container.cores;
								cpuManager.setFreqValue(host, privateKey, newContainer,
									String.valueOf(Math.round(newContainer.speed)));
							}

							// Memory changes
							if (!cpContainer.memory.equals(newContainer.memory)) {
								val memoryManager = new MemoryManager();
								// Update Memory value
								cpContainer.memory = container.memory;
								memoryManager.setMemValue(host, privateKey, newContainer,
									String.valueOf(newContainer.memory));
//								memoryManager.setSwapValue(host, privateKey, newContainer,
//									String.valueOf(newContainer.memory))									
							}
							// Bandwidth changes
							if (!cpContainer.bandwidth_used.equals(newContainer.bandwidth_used)) {
								val networkManager = new NetWorkManager();
								// Update Memory value
								cpContainer.bandwidth_used = container.bandwidth_used;
								networkManager.setNetworkValue(host, privateKey, newContainer,
									String.valueOf(newContainer.bandwidth_used));
							}

						}
						
//						LOGGER.info("Old value : " + notification.oldValue);
//						LOGGER.info("New value : " + notification.newValue);
					}

				}
			}
		);

		return container;
	}
}
