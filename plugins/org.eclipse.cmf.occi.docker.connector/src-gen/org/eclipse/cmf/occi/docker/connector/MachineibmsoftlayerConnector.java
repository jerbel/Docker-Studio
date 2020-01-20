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

import org.apache.commons.lang.StringUtils;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.docker.connector.exceptions.ValueNotSetException;
import org.eclipse.cmf.occi.docker.connector.helpers.Provider;
import org.eclipse.cmf.occi.docker.connector.manager.MachineManager;
import org.eclipse.cmf.occi.docker.connector.observer.MachineObserver;
import org.eclipse.cmf.occi.infrastructure.StopMethod;
import org.eclipse.cmf.occi.infrastructure.SuspendMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connector implementation for the OCCI kind: - scheme:
 * http://occiware.org/occi/docker# - term: machineibmsoftlayer - title: Machine
 * on IBM SoftLayer
 */
public class MachineibmsoftlayerConnector extends org.eclipse.cmf.occi.docker.impl.MachineibmsoftlayerImpl {
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(MachineibmsoftlayerConnector.class);

	private MachineObserver machineObserver = null;
	
	protected MachineManager manager = new MachineManager(this) {
		
		@Override
		public String getDriverName() {
			
			return Provider.ibm.toString();
		}
		
		@Override
		public void appendDriverParameters(StringBuilder sb) throws ValueNotSetException {
			if (StringUtils.isNotBlank(getUser())) {
				sb.append(" --softlayer-user ").append(getUser());
			}
			if (StringUtils.isNotBlank(getDomain())) {
				sb.append(" --softlayer-domain ").append(getDomain());
			}
			if (StringUtils.isNotBlank(getApiEndpoint())) {
				sb.append(" --softlayer-api-endpoint ").append(getApiEndpoint());
			}
			if (StringUtils.isNotBlank(getApiKey())) {
				sb.append(" --softlayer-api-key ").append(getApiKey());
			}
			if (getCpu() > 0) {
				sb.append(" --softlayer-cpu ").append(getCpu());
			}
			if (getDiskSize() > 0) {
				sb.append(" --softlayer-disk-size ").append(getDiskSize());
			}
			if (getOcciComputeMemory() > 0) {
				sb.append(" --softlayer-memory ").append(getOcciComputeMemory());
			}
			if (StringUtils.isNotBlank(getOcciComputeHostname())) {
				sb.append(" --softlayer-hostname ").append(getOcciComputeHostname());
			}
			if (StringUtils.isNotBlank(getImage())) {
				sb.append(" --softlayer-image ").append(getImage());
			}
			if (StringUtils.isNotBlank(getPublicVlanId())) {
				sb.append(" --softlayer-public-vlan-id ").append(getPublicVlanId());
			}
			if (StringUtils.isNotBlank(getPrivateVlanId())) {
				sb.append(" --softlayer-private-vlan-id ").append(getPrivateVlanId());
			}
			if (StringUtils.isNotBlank(getRegion())) {
				sb.append(" --softlayer-region ").append(getRegion());
			}
			if (isPrivateNetOnly()) {
				sb.append(" --softlayer-private-net-only ").append(isPrivateNetOnly());
			}
			if (isLocalDisk()) {
				sb.append(" --softlayer-local-disk ").append(isLocalDisk());
			}
			if (isPrivateNetOnly()) {
				sb.append(" --softlayer-private-net-only ").append(isPrivateNetOnly());
			}
			
		}
	};
	
	
	// Start of user code Machineibmsoftlayerconnector_constructor
	/**
	 * Constructs a machineibmsoftlayer connector.
	 */
	MachineibmsoftlayerConnector() {
		LOGGER.debug("Constructor called on " + this);
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//

	// Start of user code MachineibmsoftlayerocciCreate
	/**
	 * Called when this Machineibmsoftlayer instance is completely created.
	 */
	@Override
	public void occiCreate() {
		LOGGER.debug("occiCreate() called on " + this);
		start();
	}
	// End of user code

	// Start of user code Machineibmsoftlayer_occiRetrieve_method
	/**
	 * Called when this Machineibmsoftlayer instance must be retrieved.
	 */
	@Override
	public void occiRetrieve() {
		LOGGER.debug("occiRetrieve() called on " + this);
		try {
			manager.synchronize();
		} catch (DockerException ex) {
			LOGGER.error("Exception thrown while retrieving informations about this machine : " + this.getName());
			ex.printStackTrace();
		}
	}
	// End of user code

	// Start of user code Machineibmsoftlayer_occiUpdate_method
	/**
	 * Called when this Machineibmsoftlayer instance is completely updated.
	 */
	@Override
	public void occiUpdate() {
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code MachineibmsoftlayerocciDelete_method
	/**
	 * Called when this Machineibmsoftlayer instance will be deleted.
	 */
	@Override
	public void occiDelete() {
		LOGGER.debug("occiDelete() called on " + this);
		try {
			manager.removeMachine(this);
//			if (machineObserver != null) {
//				machineObserver.removeListener(this);
//			}
		} catch (DockerException ex) {
			ex.printStackTrace();
		}
	}
	// End of user code

	//
	// Machineibmsoftlayer actions.
	//
	@Override
	public void startall() {
		LOGGER.debug("Start all action call on " + this);
		try {
			manager.startAll();
		} catch (DockerException ex) {
			LOGGER.error(ex.getMessage());
			ex.printStackTrace();
		}
		
	}

	@Override
	public void start() {
		try {
			manager.start();
//			if (machineObserver == null) {
//				machineObserver = new MachineObserver();
//				machineObserver.listener(this);
//			}
		} catch (DockerException ex) {
			LOGGER.error(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Override
	public void stop(StopMethod method) {
		try {
			manager.stop(method);
		} catch (DockerException ex) {
			LOGGER.error(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Override
	public void suspend(SuspendMethod method) {
		try {
			manager.suspend(method);
		} catch (DockerException ex) {
			LOGGER.error(ex.getMessage());
			ex.printStackTrace();
		}
	}
}
