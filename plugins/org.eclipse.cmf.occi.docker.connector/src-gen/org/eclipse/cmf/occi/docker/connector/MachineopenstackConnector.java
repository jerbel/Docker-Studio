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
 * http://occiware.org/occi/docker# - term: machineopenstack - title: Machine on
 * OpenStack
 */
public class MachineopenstackConnector extends org.eclipse.cmf.occi.docker.impl.MachineopenstackImpl {
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(MachineopenstackConnector.class);

	private MachineObserver machineObserver = null;
	
	protected MachineManager manager = new MachineManager(this) {
		
		@Override
		public String getDriverName() {
			
			return Provider.openstack.toString();
		}
		
		@Override
		public void appendDriverParameters(StringBuilder sb) throws ValueNotSetException {
			Preconditions.checkNotNull(getAuthUrl(), "authurl is null");
			Preconditions.checkNotNull(getFlavorId(), "flavorid is null");
			Preconditions.checkNotNull(getImageId(), "imageid is null");
			Preconditions.checkNotNull(getTenantId(), "tenantid is null");
			Preconditions.checkNotNull(getTenantName(), "tenantname is null");
			Preconditions.checkNotNull(getUsername(), "username is null");
			Preconditions.checkNotNull(getPassword(), "password is null");
			Preconditions.checkNotNull(getFloatingIpPool(), "floatingippool is null");
			if (!getAuthUrl().isEmpty()) {
				sb.append(" --openstack-auth-url ").append(getAuthUrl());
			}
			if (StringUtils.isNotBlank(getFlavorId())) {
				sb.append(" --openstack-flavor-id ").append(getFlavorId());
			}
			if (StringUtils.isNotBlank(getFlavorName())) {
				sb.append(" --openstack-flavor-name ").append(getFlavorName());
			}
			if (StringUtils.isNotBlank(getImageId())) {
				sb.append(" --openstack-image-id ").append(getImageId());
			}
			if (StringUtils.isNotBlank(getImageName())) {
				sb.append(" --openstack-image-name ").append(getImageName());
			}
			if (StringUtils.isNotBlank(getTenantId())) {
				sb.append(" --openstack-tenant-id ").append(getTenantId());
			}
			if (StringUtils.isNotBlank(getTenantName())) {
				sb.append(" --openstack-tenant-name ").append(getTenantName());
			}
			if (StringUtils.isNotBlank(getUsername())) {
				sb.append(" --openstack-username ").append(getUsername());
			}
			if (StringUtils.isNotBlank(getPassword())) {
				sb.append(" --openstack-password ").append(getPassword());
			}
			if (StringUtils.isNotBlank(getFloatingIpPool())) {
				sb.append(" --openstack-floatingip-pool ").append(getFloatingIpPool());
			}
			if (StringUtils.isNotBlank(getRegion())) {
				sb.append(" --openstack-region ").append(getRegion());
			}
			if (StringUtils.isNotBlank(getNetId())) {

				sb.append(" --openstack-net-id ").append(getNetId());
			}
			if (StringUtils.isNotBlank(getNetName())) {

				sb.append(" --openstack-net-name ").append(getNetName());
			}
			if (StringUtils.isNotBlank(getDomainId())) {

				sb.append(" --openstack-domain-id ").append(getDomainId());
			}
			if (StringUtils.isNotBlank(getDomainName())) {

				sb.append(" --openstack-domain-name ").append(getDomainName());
			}
			if (StringUtils.isNotBlank(getAvailabilityZone())) {
				sb.append(" --openstack-availability-zone ").append(getAvailabilityZone());
			}
			if (getActiveTimeOut() != 200) {
				sb.append(" --openstack-availability-zone ").append(getActiveTimeOut());
			}
			if (StringUtils.isNotBlank(getPrivateKeyFile())) {

				sb.append(" --openstack-private-key-file ").append(getPrivateKeyFile());
			}
			if (StringUtils.isNotBlank(getSshPort().toString())) {

				sb.append(" --openstack-ssh-port ").append(getSshPort());
			}
			if (StringUtils.isNotBlank(getSshUser())) {

				sb.append(" --openstack-ssh-user ").append(getSshUser());
			}
			if (isInsecure()) {
				sb.append(" --openstack-insecure ").append(isInsecure());
			}
			if (StringUtils.isNotBlank(getEndpointType())) {

				sb.append(" --openstack-endpoint-type ").append(getEndpointType());
			}
			if (StringUtils.isNotBlank(getSecGroups())) {
				sb.append(" --openstack-sec-groups ").append(getSecGroups());
			} else {
				sb.append(" --openstack-sec-groups ").append("default");
			}
		}
	};
	// Start of user code Machineopenstackconnector_constructor
	/**
	 * Constructs a machineopenstack connector.
	 */
	MachineopenstackConnector() {
		LOGGER.debug("Constructor called on " + this);
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//

	// Start of user code MachineopenstackocciCreate
	/**
	 * Called when this Machineopenstack instance is completely created.
	 */
	@Override
	public void occiCreate() {
		LOGGER.debug("occiCreate() called on " + this);
		start();
	}
	// End of user code

	// Start of user code Machineopenstack_occiRetrieve_method
	/**
	 * Called when this Machineopenstack instance must be retrieved.
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

	// Start of user code Machineopenstack_occiUpdate_method
	/**
	 * Called when this Machineopenstack instance is completely updated.
	 */
	@Override
	public void occiUpdate() {
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code MachineopenstackocciDelete_method
	/**
	 * Called when this Machineopenstack instance will be deleted.
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
	// Machineopenstack actions.
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
