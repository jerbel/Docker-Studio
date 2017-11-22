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
import org.eclipse.cmf.occi.docker.connector.observer.MachineObserver;
import org.eclipse.cmf.occi.infrastructure.StopMethod;
import org.eclipse.cmf.occi.infrastructure.SuspendMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connector implementation for the OCCI kind: - scheme:
 * http://occiware.org/occi/docker# - term: machinegeneric - title: Create
 * machines using an existing VM/Host with SSH
 */
public class MachinegenericConnector extends org.eclipse.cmf.occi.docker.impl.MachinegenericImpl {
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(MachinegenericConnector.class);

	private MachineObserver machineObserver = null;
	
	protected MachineManager manager = new MachineManager(this) {
		
		@Override
		public String getDriverName() {
			
			return Provider.generic.toString();
		}
		
		@Override
		public void appendDriverParameters(StringBuilder sb) throws ValueNotSetException {
			if (!(StringUtils.isEmpty(getEnginePort().toString()) || "0".equals(getEnginePort().toString()))) {
				sb.append(" --generic-engine-port ").append(getEnginePort());
			}
			if (!StringUtils.isEmpty(getIpAddress())) {
				sb.append(" --generic-ip-address ").append(getIpAddress());
			}
			if (!StringUtils.isEmpty(getSshUser())) {
				sb.append(" --generic-ssh-user ").append(getSshUser());
			}
			if (!(StringUtils.isEmpty(getSshPort().toString()) || "0".equals(getSshPort().toString()))) {
				sb.append(" --generic-ssh-port ").append(getSshPort());
			}
            if (!(StringUtils.isEmpty(getSshKey()) || "0".equals(getSshKey()))) {
                sb.append(" --generic-ssh-key ").append(getSshKey());
            }
			
		}
	};
	// Start of user code Machinegenericconnector_constructor
	/**
	 * Constructs a machinegeneric connector.
	 */
	MachinegenericConnector() {
		LOGGER.debug("Constructor called on " + this);
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//

	// Start of user code MachinegenericocciCreate
	/**
	 * Called when this Machinegeneric instance is completely created.
	 */
	@Override
	public void occiCreate() {
		LOGGER.debug("occiCreate() called on " + this);
		start();
	}
	// End of user code

	// Start of user code Machinegeneric_occiRetrieve_method
	/**
	 * Called when this Machinegeneric instance must be retrieved.
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

	// Start of user code Machinegeneric_occiUpdate_method
	/**
	 * Called when this Machinegeneric instance is completely updated.
	 */
	@Override
	public void occiUpdate() {
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code MachinegenericocciDelete_method
	/**
	 * Called when this Machinegeneric instance will be deleted.
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
	// Machinegeneric actions.
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
