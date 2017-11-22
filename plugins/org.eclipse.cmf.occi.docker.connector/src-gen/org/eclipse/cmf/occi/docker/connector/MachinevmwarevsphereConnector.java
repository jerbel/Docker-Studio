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
 * http://occiware.org/occi/docker# - term: machinevmwarevsphere - title:
 * Machine on VMware vSphere
 */
public class MachinevmwarevsphereConnector extends org.eclipse.cmf.occi.docker.impl.MachinevmwarevsphereImpl {
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(MachinevmwarevsphereConnector.class);
	
	private MachineObserver machineObserver = null;
	
	protected MachineManager manager = new MachineManager(this) {
		
		@Override
		public String getDriverName() {
			
			return Provider.vmwarevsphere.toString();
		}
		
		@Override
		public void appendDriverParameters(StringBuilder sb) throws ValueNotSetException {
			if (StringUtils.isNotBlank(getUsername())) {
				sb.append(" --vmwarevsphere-username ").append(getUsername());
			}
			if (StringUtils.isNotBlank(getPassword())) {
				sb.append(" --vmwarevsphere-password ").append(getPassword());
			}
			if (StringUtils.isNotBlank(getDatacenter())) {
				sb.append(" --vmwarevsphere-datacenter ").append(getDatacenter());
			}
			if (StringUtils.isNotBlank(getVcenter())) {
				sb.append(" --vmwarevsphere-vcenter ").append(getVcenter());
			}
			if (StringUtils.isNotBlank(getDatastore())) {
				sb.append(" --vmwarevsphere-datastore ").append(getDatastore());
			}
			if (StringUtils.isNotBlank(getNetwork())) {
				sb.append(" --vmwarevsphere-network ").append(getNetwork());
			}
			if (StringUtils.isNotBlank(getBoot2dockerURL())) {
				sb.append(" --vmwarevsphere-boot2docker-url ").append(getBoot2dockerURL());
			}
			if (StringUtils.isNotBlank(getComputeIp())) {
				sb.append(" --vmwarevsphere-compute-ip ").append(getComputeIp());
			}
			if (StringUtils.isNotBlank(pool)) {
				sb.append(" --vmwarevsphere-pool ").append(pool);
			}
			if (getOcciComputeMemory() > 0.0F) {
				sb.append(" --vmwarevsphere-memory-size ").append(getOcciComputeMemory().intValue());
			}
			if (getDiskSize() > 0) {
				sb.append(" --vmwarevsphere-disk-size ").append(getDiskSize());
			}
			
		}
	};
	
	// Start of user code Machinevmwarevsphereconnector_constructor
	/**
	 * Constructs a machinevmwarevsphere connector.
	 */
	MachinevmwarevsphereConnector() {
		LOGGER.debug("Constructor called on " + this);
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//

	// Start of user code MachinevmwarevsphereocciCreate
	/**
	 * Called when this Machinevmwarevsphere instance is completely created.
	 */
	@Override
	public void occiCreate() {
		LOGGER.debug("occiCreate() called on " + this);
		start();
	}
	// End of user code

	// Start of user code Machinevmwarevsphere_occiRetrieve_method
	/**
	 * Called when this Machinevmwarevsphere instance must be retrieved.
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

	// Start of user code Machinevmwarevsphere_occiUpdate_method
	/**
	 * Called when this Machinevmwarevsphere instance is completely updated.
	 */
	@Override
	public void occiUpdate() {
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code MachinevmwarevsphereocciDelete_method
	/**
	 * Called when this Machinevmwarevsphere instance will be deleted.
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
	// Machinevmwarevsphere actions.
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
