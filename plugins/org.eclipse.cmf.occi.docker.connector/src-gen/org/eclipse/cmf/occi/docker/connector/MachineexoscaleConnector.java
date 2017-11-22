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
 * http://occiware.org/occi/docker# - term: machineexoscale - title: Machine
 * Exoscale
 */
public class MachineexoscaleConnector extends org.eclipse.cmf.occi.docker.impl.MachineexoscaleImpl {
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(MachineexoscaleConnector.class);

	private MachineObserver machineObserver = null;
	
	protected MachineManager manager = new MachineManager(this) {

		@Override
		public String getDriverName() {
			// TODO Auto-generated method stub
			return Provider.exoscale.toString();
		}

		@Override
		public void appendDriverParameters(StringBuilder sb) throws ValueNotSetException {

			if (!StringUtils.isEmpty(getUrl())) {
				sb.append(" --exoscale-url ").append(getUrl());
			}
			if (!StringUtils.isEmpty(getApiKey())) {
				sb.append(" --exoscale-api-key ").append(getApiKey());
			}
			if (!StringUtils.isEmpty(getApiSecretKey())) {
				sb.append(" --exoscale-api-secret-key ").append(getApiSecretKey());
			}
			if (!StringUtils.isEmpty(getInstanceProfile())) {
				sb.append(" --exoscale-instance-profile	").append(getInstanceProfile());
			}
			if (!StringUtils.isEmpty(image)) {
				sb.append(" --exoscale-image ").append(image);
			}
			if (!StringUtils.isEmpty(getSecurityGroup())) {
				sb.append(" --exoscale-security-group ").append(getSecurityGroup());
			}
			if (!StringUtils.isEmpty(getAvailabilityZone())) {
				sb.append(" --exoscale-availability-zone ").append(getAvailabilityZone());
			}
			if (!StringUtils.isEmpty(getSshUser())) {
				sb.append(" --exoscale-ssh-user ").append(getSshUser());
			}
			if (!StringUtils.isEmpty(getUserData())) {
				sb.append(" --exoscale-userdata ").append(getUserData());
			}
			if (!StringUtils.isEmpty(getAffinityGroup())) {
				sb.append(" --exoscale-affinity-group ").append(getAffinityGroup());
			}

		}
	};
	
	// Start of user code Machineexoscaleconnector_constructor
	/**
	 * Constructs a machineexoscale connector.
	 */
	MachineexoscaleConnector() {
		LOGGER.debug("Constructor called on " + this);
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//

	// Start of user code MachineexoscaleocciCreate
	/**
	 * Called when this Machineexoscale instance is completely created.
	 */
	@Override
	public void occiCreate() {
		LOGGER.debug("occiCreate() called on " + this);
		start();
	}
	// End of user code

	// Start of user code Machineexoscale_occiRetrieve_method
	/**
	 * Called when this Machineexoscale instance must be retrieved.
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

	// Start of user code Machineexoscale_occiUpdate_method
	/**
	 * Called when this Machineexoscale instance is completely updated.
	 */
	@Override
	public void occiUpdate() {
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code MachineexoscaleocciDelete_method
	/**
	 * Called when this Machineexoscale instance will be deleted.
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
	// Machineexoscale actions.
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
