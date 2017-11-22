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
 * http://occiware.org/occi/docker# - term: machineamazonec2 - title:
 */
public class Machineamazonec2Connector extends org.eclipse.cmf.occi.docker.impl.Machineamazonec2Impl {
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(Machineamazonec2Connector.class);
	
	private MachineObserver machineObserver = null;

	protected MachineManager manager = new MachineManager(this) {

		@Override
		public String getDriverName() {
			// TODO Auto-generated method stub
			return Provider.amazonec2.toString();
		}

		@Override
		public void appendDriverParameters(StringBuilder sb) throws ValueNotSetException {

			Preconditions.checkNotNull(getAccessKey(), "access_key is null");
			Preconditions.checkNotNull(getSecretKey(), "secret_key is null");
			Preconditions.checkNotNull(getVpcId(), "vpc_id is null");

			Preconditions.checkNotNull(zone, "zone is null");

			if (StringUtils.isNotBlank(getAccessKey())) {
				sb.append(" --amazonec2-access-key ").append(getAccessKey());
			}
			if (StringUtils.isNotBlank(getSecretKey())) {
				sb.append(" --amazonec2-secret-key ").append(getSecretKey());
			}
			if (StringUtils.isNotBlank(getVpcId())) {
				sb.append(" --amazonec2-vpc-id ").append(getVpcId());
			}
			if (StringUtils.isNotBlank(getZone())) {
				sb.append(" --amazonec2-zone ").append(getZone());
			}
			if (StringUtils.isNotBlank(getAmi())) {
				sb.append(" --amazonec2-ami ").append(getAmi());
			}
			if (StringUtils.isNotBlank(region)) {
				sb.append(" --amazonec2-region ").append(getRegion());
			}
			if (StringUtils.isNotBlank(getInstanceType())) {
				sb.append(" --amazonec2-instance-type ").append(getInstanceType());
			}
			if (getRootSize() > 0) {
				sb.append(" --amazonec2-root-size ").append(getRootSize());
			}
			if (StringUtils.isNotBlank(getSubnetId())) {
				sb.append(" --amazonec2-subnet-id ").append(getSubnetId());
			}
			if (StringUtils.isNotBlank(getSessionToken())) {
				sb.append(" --amazonec2-session-token ").append(getSessionToken());
			}
			if (StringUtils.isNotBlank(getSecurityGroup())) {
				sb.append(" --amazonec2-security-group ").append(getSecurityGroup());
			}

		}
	};

	// Start of user code Machineamazonec2connector_constructor
	/**
	 * Constructs a machineamazonec2 connector.
	 */
	Machineamazonec2Connector() {
		LOGGER.debug("Constructor called on " + this);
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//

	// Start of user code Machineamazonec2occiCreate
	/**
	 * Called when this Machineamazonec2 instance is completely created.
	 */
	@Override
	public void occiCreate() {
		LOGGER.debug("occiCreate() called on " + this);
		start();
	}
	// End of user code

	// Start of user code Machineamazonec2_occiRetrieve_method
	/**
	 * Called when this Machineamazonec2 instance must be retrieved.
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

	// Start of user code Machineamazonec2_occiUpdate_method
	/**
	 * Called when this Machineamazonec2 instance is completely updated.
	 */
	@Override
	public void occiUpdate() {
		LOGGER.debug("occiUpdate() called on " + this);
	}
	// End of user code

	// Start of user code Machineamazonec2occiDelete_method
	/**
	 * Called when this Machineamazonec2 instance will be deleted.
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
	// Machineamazonec2 actions.
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
