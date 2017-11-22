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
 * http://occiware.org/occi/docker# - term: machinemicrosoftazure - title:
 * Machine on Microsoft Azure
 */
public class MachinemicrosoftazureConnector extends org.eclipse.cmf.occi.docker.impl.MachinemicrosoftazureImpl {
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(MachinemicrosoftazureConnector.class);

	private MachineObserver machineObserver = null;
	
	protected MachineManager manager = new MachineManager(this) {
		
		@Override
		public String getDriverName() {
			
			return Provider.azure.toString();
		}
		
		@Override
		public void appendDriverParameters(StringBuilder sb) throws ValueNotSetException {
			Preconditions.checkNotNull(getSubscriptionId(), "subscription_id is null");
			Preconditions.checkNotNull(getSubscriptionCert(), "subscription_cert is null");

			if (StringUtils.isNotBlank(getSubscriptionId())) {
				sb.append(" --azure-subscription-id ").append(getSubscriptionId());
			}
			if (StringUtils.isNotBlank(getSubscriptionCert())) {
				sb.append(" --azure-subscription-cert ").append(getSubscriptionCert());
			}
			if (StringUtils.isNotBlank(getEnvironment())) {
				sb.append(" --azure-environment ").append(getEnvironment());
			}
			if (StringUtils.isNotBlank(getImage())) {
				sb.append(" --azure-image ").append(getImage());
			}
			if (StringUtils.isNotBlank(getMachineLocation())) {
				sb.append(" --azure-location ").append(getMachineLocation());
			}
			if (StringUtils.isNotBlank(getResourceGroup())) {
				sb.append(" --azure-resource-group ").append(getResourceGroup());
			}
			if (StringUtils.isNotBlank(getSize())) {
				sb.append(" --azure-size ").append(getSize());
			}
			if (StringUtils.isNotBlank(getSshUser())) {
				sb.append(" --azure-ssh-user ").append(getSshUser());
			}
			if (StringUtils.isNotBlank(getVnet())) {
				sb.append(" --azure-vnet ").append(getVnet());
			}
			if (StringUtils.isNotBlank(getSubnet())) {
				sb.append(" --azure-subnet ").append(getSubnet());
			}
			if (StringUtils.isNotBlank(getSubnetPrefix())) {
				sb.append(" --azure-subnet-prefix ").append(getSubnetPrefix());
			}
			if (StringUtils.isNotBlank(getAvailabilitySet())) {
				sb.append(" --azure-availability-set ").append(getAvailabilitySet());
			}
			if (StringUtils.isNotBlank(getOpenPort().toString())) {
				sb.append(" --azure-open-port ").append(getOpenPort());
			}
			if (StringUtils.isNotBlank(getPrivateIpAddress())) {
				sb.append(" --azure-private-ip-address ").append(getPrivateIpAddress());
			}
			if (StringUtils.isNotBlank(getUsePrivateIp())) {
				sb.append(" --azure-use-private-ip ").append(getUsePrivateIp());
			}
			if (StringUtils.isNotBlank(getNoPublicIp())) {
				sb.append(" --azure-no-public-ip ").append(getNoPublicIp());
			}
			if (StringUtils.isNotBlank(getStaticPublicIp())) {
				sb.append(" --azure-static-public-ip ").append(getStaticPublicIp());
			}
			if (StringUtils.isNotBlank(getDockerPort())) {
				sb.append(" --azure-docker-port ").append(getDockerPort());
			}
			
		}
	};
	// Start of user code Machinemicrosoftazureconnector_constructor
	/**
	 * Constructs a machinemicrosoftazure connector.
	 */
	MachinemicrosoftazureConnector() {
		LOGGER.debug("Constructor called on " + this);
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//

	// Start of user code MachinemicrosoftazureocciCreate
	/**
	 * Called when this Machinemicrosoftazure instance is completely created.
	 */
	@Override
	public void occiCreate() {
		LOGGER.debug("occiCreate() called on " + this);
		start();
	}
	// End of user code

	// Start of user code Machinemicrosoftazure_occiRetrieve_method
	/**
	 * Called when this Machinemicrosoftazure instance must be retrieved.
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

	// Start of user code Machinemicrosoftazure_occiUpdate_method
	/**
	 * Called when this Machinemicrosoftazure instance is completely updated.
	 */
	@Override
	public void occiUpdate() {
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code MachinemicrosoftazureocciDelete_method
	/**
	 * Called when this Machinemicrosoftazure instance will be deleted.
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
	// Machinemicrosoftazure actions.
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
