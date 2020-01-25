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
 * http://occiware.org/occi/docker# - term: machinemicrosofthyperv - title:
 * Machine on Microsoft Hyper-V
 */
public class MachinemicrosofthypervConnector extends org.eclipse.cmf.occi.docker.impl.MachinemicrosofthypervImpl {
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(MachinemicrosofthypervConnector.class);
	private MachineObserver machineObserver = null;
	
	protected MachineManager manager = new MachineManager(this) {
		
		@Override
		public String getDriverName() {
			
			return Provider.microsofthyperv.toString();
		}
		
		@Override
		public void appendDriverParameters(StringBuilder sb) throws ValueNotSetException {
			if (StringUtils.isNotBlank(getVirtualSwitch())) {
				sb.append(" --hyperv-virtual-switch ").append(getVirtualSwitch());
			}
			if (StringUtils.isNotBlank(getDiskSize().toString())) {
				sb.append(" --hyperv-disk-size ").append(getDiskSize());
			}
			if (StringUtils.isNotBlank(getOcciComputeMemory().toString())) {
				sb.append(" --hyperv-memory ").append(getOcciComputeMemory());
			}
			if (StringUtils.isNotBlank(getOcciComputeCores().toString())) {
				sb.append(" --hyperv-cpu-count ").append(getOcciComputeCores());
			}
			if (StringUtils.isNotBlank(getStaticMacAddress())) {
				sb.append(" --hyperv-static-macaddress ").append(getStaticMacAddress());
			}
			if (StringUtils.isNotBlank(getVlanId())) {
				sb.append(" --hyperv-vlan-id ").append(getVlanId());
			}
			
		}
	};
	// Start of user code Machinemicrosofthypervconnector_constructor
	/**
	 * Constructs a machinemicrosofthyperv connector.
	 */
	MachinemicrosofthypervConnector() {
		LOGGER.debug("Constructor called on " + this);
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//

	// Start of user code MachinemicrosofthypervocciCreate
	/**
	 * Called when this Machinemicrosofthyperv instance is completely created.
	 */
	@Override
	public void occiCreate() {
		LOGGER.debug("occiCreate() called on " + this);
		start();
	}
	// End of user code

	// Start of user code Machinemicrosofthyperv_occiRetrieve_method
	/**
	 * Called when this Machinemicrosofthyperv instance must be retrieved.
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

	// Start of user code Machinemicrosofthyperv_occiUpdate_method
	/**
	 * Called when this Machinemicrosofthyperv instance is completely updated.
	 */
	@Override
	public void occiUpdate() {
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code MachinemicrosofthypervocciDelete_method
	/**
	 * Called when this Machinemicrosofthyperv instance will be deleted.
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
	// Machinemicrosofthyperv actions.
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
