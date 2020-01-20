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
 * http://occiware.org/occi/docker# - term: machinegrid5000 - title: Grid5000
 */
public class Machinegrid5000Connector extends org.eclipse.cmf.occi.docker.impl.Machinegrid5000Impl {
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(Machinegrid5000Connector.class);

	private MachineObserver machineObserver = null;

	protected MachineManager manager = new MachineManager(this) {
		
		@Override
		public String getDriverName() {
			return Provider.g5k.toString();
		}
		
		@Override
		public void appendDriverParameters(StringBuilder sb) throws ValueNotSetException {
			
			if (!StringUtils.isEmpty(getUsername())) {
				sb.append(" --g5k-username ").append(getUsername());
			}
			if (!StringUtils.isEmpty(getPassword())) {
				sb.append(" --g5k-password ").append(getPassword());
			}
			if (!StringUtils.isEmpty(getSite())) {
				sb.append(" --g5k-site ").append(getSite());
			}
			if (!StringUtils.isEmpty(getWalltime())) {
				sb.append(" --g5k-walltime ").append(getWalltime());
			}
			if (!StringUtils.isEmpty(getSshPrivateKey())) {
				sb.append(" --g5k-ssh-private-key ").append(getSshPrivateKey());
			}
			if (!StringUtils.isEmpty(getSshPublicKey())) {
				sb.append(" --g5k-ssh-public-key ").append(getSshPublicKey());
			}
			if (!StringUtils.isEmpty(image)) {
				sb.append(" --g5k-image ").append(image);
			}
			if (!StringUtils.isEmpty(getResourceProperties())) {
				sb.append(" --g5k-resource-properties ").append(getResourceProperties());
			}
			if (!StringUtils.isEmpty(getUseJobReservation())) {
				sb.append(" --g5k-use-job-reservation ").append(getUseJobReservation());
			}
			if (!StringUtils.isEmpty(getHostToProvision())) {
				sb.append(" --g5k-host-to-provision ").append(getHostToProvision());
			}
		}
	};
	
	// Start of user code Machinegrid5000connector_constructor
	/**
	 * Constructs a machinegrid5000 connector.
	 */
	Machinegrid5000Connector() {
		LOGGER.debug("Constructor called on " + this);
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//

	// Start of user code Machinegrid5000occiCreate
	/**
	 * Called when this Machinegrid5000 instance is completely created.
	 */
	@Override
	public void occiCreate() {
		LOGGER.debug("occiCreate() called on " + this);
		start();
	}
	// End of user code

	// Start of user code Machinegrid5000_occiRetrieve_method
	/**
	 * Called when this Machinegrid5000 instance must be retrieved.
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

	// Start of user code Machinegrid5000_occiUpdate_method
	/**
	 * Called when this Machinegrid5000 instance is completely updated.
	 */
	@Override
	public void occiUpdate() {
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code Machinegrid5000occiDelete_method
	/**
	 * Called when this Machinegrid5000 instance will be deleted.
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
	// Machinegrid5000 actions.
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
