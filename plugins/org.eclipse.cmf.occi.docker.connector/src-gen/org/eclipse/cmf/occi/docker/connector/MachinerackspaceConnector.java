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

import com.google.common.base.Preconditions;

/**
 * Connector implementation for the OCCI kind: - scheme:
 * http://occiware.org/occi/docker# - term: machinerackspace - title: Machine on
 * Rackspace cloud
 */
public class MachinerackspaceConnector extends org.eclipse.cmf.occi.docker.impl.MachinerackspaceImpl {
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(MachinerackspaceConnector.class);
	
	private MachineObserver machineObserver = null;
	
	protected MachineManager manager = new MachineManager(this) {
		
		@Override
		public String getDriverName() {
			
			return Provider.rackspace.toString();
		}
		
		@Override
		public void appendDriverParameters(StringBuilder sb) throws ValueNotSetException {
			Preconditions.checkNotNull(getApiKey(), "apikey is null");
			Preconditions.checkNotNull(getUsername(), "username is null");
			Preconditions.checkNotNull(getRegion(), "region is null");

			if (StringUtils.isNotBlank(getApiKey())) {
				sb.append(" --rackspace-api-key ").append(getApiKey());
			}
			if (StringUtils.isNotBlank(getUsername())) {
				sb.append(" --rackspace-username ").append(getUsername());
			}
			if (StringUtils.isNotBlank(getRegion())) {
				sb.append(" --rackspace-region ").append(getRegion());
			}
			if (StringUtils.isNotBlank(getEndPointType())) {
				sb.append(" --rackspace-endpoint-type ").append(getEndPointType());
			}
			if (StringUtils.isNotBlank(getSshUser())) {
				sb.append(" --rackspace-ssh-user ").append(getSshUser());
			}
			if (getSshPort() > 0) {
				sb.append(" --rackspace-ssh-port ").append(getSshPort());
			}
			if (StringUtils.isNotBlank(getFlavorId())) {
				sb.append(" --rackspace-flavor-id ").append(getFlavorId());
			}
			if (!isDockerInstall()) {
				sb.append(" --rackspace-docker-install ").append(isDockerInstall());
			}
		}
	};
	
	// Start of user code Machinerackspaceconnector_constructor
	/**
	 * Constructs a machinerackspace connector.
	 */
	MachinerackspaceConnector() {
		LOGGER.debug("Constructor called on " + this);
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//

	// Start of user code MachinerackspaceocciCreate
	/**
	 * Called when this Machinerackspace instance is completely created.
	 */
	@Override
	public void occiCreate() {
		LOGGER.debug("occiCreate() called on " + this);
		start();
	}
	// End of user code

	// Start of user code Machinerackspace_occiRetrieve_method
	/**
	 * Called when this Machinerackspace instance must be retrieved.
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

	// Start of user code Machinerackspace_occiUpdate_method
	/**
	 * Called when this Machinerackspace instance is completely updated.
	 */
	@Override
	public void occiUpdate() {
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code MachinerackspaceocciDelete_method
	/**
	 * Called when this Machinerackspace instance will be deleted.
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
	// Machinerackspace actions.
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
