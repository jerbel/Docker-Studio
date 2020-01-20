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
 * http://occiware.org/occi/docker# - term: machinevmwarevcloudair - title:
 * Machine on VMware vCloud Air
 */
public class MachinevmwarevcloudairConnector extends org.eclipse.cmf.occi.docker.impl.MachinevmwarevcloudairImpl {
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(MachinevmwarevcloudairConnector.class);

	private MachineObserver machineObserver = null;
	
	protected MachineManager manager = new MachineManager(this) {
		
		@Override
		public String getDriverName() {
			
			return Provider.vmwarevcloudair.toString();
		}
		
		@Override
		public void appendDriverParameters(StringBuilder sb) throws ValueNotSetException {
			if (StringUtils.isNotBlank(username)) {
				sb.append(" --vmwarevcloudair-username ").append(username);
			}
			if (StringUtils.isNotBlank(password)) {
				sb.append(" --vmwarevcloudair-password ").append(password);
			}
			if (StringUtils.isNotBlank(getComputeId())) {
				sb.append(" --vmwarevcloudair-computeid ").append(getComputeId());
			}
			if (getCpuCount() > 0) {
				sb.append(" --vmwarevcloudair-computeid ").append(getCpuCount());
			}
			if (StringUtils.isNotBlank(getCatalog())) {
				sb.append(" --vmwarevcloudair-catalog ").append(getCatalog());
			}
			if (getDockerPort() > 0) {
				sb.append(" --vmwarevcloudair-docker-port ").append(getDockerPort());
			}
			if (StringUtils.isNotBlank(edgegateway)) {
				sb.append(" --vmwarevcloudair-edgegateway ").append(getEdgegateway());
			}
			if (getMemorySize() > 0) {
				sb.append(" --vmwarevcloudair-edgegateway ").append(getMemorySize());
			}
			if (isProvision()) {
				sb.append(" --vmwarevcloudair-provision ").append(isProvision());
			}
			if (StringUtils.isNotBlank(getPublicIp())) {
				sb.append(" --vmwarevcloudair-publicip ").append(getPublicIp());
			}
			if (StringUtils.isNotBlank(getOrgvdcnetwork())) {
				sb.append(" --vmwarevcloudair-orgvdcnetwork ").append(getOrgvdcnetwork());
			}
			if (getSshPort() > 0) {
				sb.append(" --vmwarevcloudair-ssh-port ").append(getSshPort());
			}
			if (StringUtils.isNotBlank(getVdcId())) {
				sb.append(" --vmwarevcloudair-vdcid ").append(getVdcId());
			}
			
		}
	};
	
	// Start of user code Machinevmwarevcloudairconnector_constructor
	/**
	 * Constructs a machinevmwarevcloudair connector.
	 */
	MachinevmwarevcloudairConnector() {
		LOGGER.debug("Constructor called on " + this);
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//

	// Start of user code MachinevmwarevcloudairocciCreate
	/**
	 * Called when this Machinevmwarevcloudair instance is completely created.
	 */
	@Override
	public void occiCreate() {
		LOGGER.debug("occiCreate() called on " + this);
		start();
	}
	// End of user code

	// Start of user code Machinevmwarevcloudair_occiRetrieve_method
	/**
	 * Called when this Machinevmwarevcloudair instance must be retrieved.
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

	// Start of user code Machinevmwarevcloudair_occiUpdate_method
	/**
	 * Called when this Machinevmwarevcloudair instance is completely updated.
	 */
	@Override
	public void occiUpdate() {
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code MachinevmwarevcloudairocciDelete_method
	/**
	 * Called when this Machinevmwarevcloudair instance will be deleted.
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
	// Machinevmwarevcloudair actions.
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
