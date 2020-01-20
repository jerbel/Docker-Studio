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
 * http://occiware.org/occi/docker# - term: machinevirtualbox - title: Machine
 * on VirtualBox
 */
public class MachinevirtualboxConnector extends org.eclipse.cmf.occi.docker.impl.MachinevirtualboxImpl {
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(MachinevirtualboxConnector.class);
	private MachineObserver machineObserver = null;
	
	protected MachineManager manager = new MachineManager(this) {
		
		@Override
		public String getDriverName() {
			
			return Provider.virtualbox.toString();
		}
		
		@Override
		public void appendDriverParameters(StringBuilder sb) throws ValueNotSetException {
			if (getDiskSize() > 0) {
				sb.append(" --virtualbox-disk-size ").append(getDiskSize());
			}
			if (getOcciComputeMemory() > 0.0F) {
				sb.append(" --virtualbox-memory ").append(getOcciComputeMemory().intValue());
			} else if (getOcciComputeMemory() == 0.0F) {
				sb.append(" --virtualbox-memory ").append(1024);
			}
			if (getOcciComputeCores() > 0) {
				sb.append(" --virtualbox-cpu-count ").append(getOcciComputeCores());
			} else if (getOcciComputeCores() == 0) {
				sb.append(" --virtualbox-cpu-count ").append(-1);
			}
			if (!StringUtils.isEmpty(getBoot2dockerURL())) {
				sb.append(" --virtualbox-boot2docker-url ").append(getBoot2dockerURL());
			}else{
				// TODO : Update version, use configuration file for this value, this must not be set on hard as this..
				// Use boot2docker v1.11.2
				sb.append(" --virtualbox-boot2docker-url ").append("https://github.com/boot2docker/boot2docker/releases/download/v1.11.2/boot2docker.iso");
			}
			if (isHostDNSResolver()) {
				sb.append(" --virtualbox-host-dns-resolver ").append(isHostDNSResolver());
			}
			if (!StringUtils.isEmpty(getImportBoot2DockerVM())) {
				sb.append(" --virtualbox-import-boot2docker-vm ").append(getImportBoot2DockerVM());
			}
			if (isHostDNSResolver()) {
				sb.append(" --virtualbox-host-dns-resolver ").append(isHostDNSResolver());
			}
			if (!StringUtils.isEmpty(getHostOnlyNICType())) {
				sb.append(" --virtualbox-hostonly-nictype ").append(getHostOnlyNICType());
			}
			if (isNoShare()) {
				sb.append(" --virtualbox-no-share ").append(isNoShare());
			}
			if (isNoDNSProxy()) {
				sb.append(" --virtualbox-no-dns-proxy ").append(isNoDNSProxy());
			}
			if (isNoVTXCheck()) {
				sb.append(" --virtualbox-no-vtx-check ").append(isNoVTXCheck());
			}
			if (!StringUtils.isEmpty(getShareFolder())) {
				sb.append(" --virtualbox-share-folder ").append(getShareFolder());
			}
			
		}
	};
	// Start of user code Machinevirtualboxconnector_constructor
	/**
	 * Constructs a machinevirtualbox connector.
	 */
	MachinevirtualboxConnector() {
		LOGGER.debug("Constructor called on " + this);
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//

	// Start of user code MachinevirtualboxocciCreate
	/**
	 * Called when this Machinevirtualbox instance is completely created.
	 */
	@Override
	public void occiCreate() {
		LOGGER.debug("occiCreate() called on " + this);
		start();
	}
	// End of user code

	// Start of user code Machinevirtualbox_occiRetrieve_method
	/**
	 * Called when this Machinevirtualbox instance must be retrieved.
	 */
	@Override
	public void occiRetrieve() {
		LOGGER.debug("occiRetrieve() called on " + this);
		// TODO : Describe machine.... DO NOT use synchronize method here !!!
		// try {
		//	manager.synchronize();
		//} catch (DockerException ex) {
		//	LOGGER.error("Exception thrown while retrieving informations about this machine : " + this.getName());
		//	ex.printStackTrace();
		//}
	}
	// End of user code

	// Start of user code Machinevirtualbox_occiUpdate_method
	/**
	 * Called when this Machinevirtualbox instance is completely updated.
	 */
	@Override
	public void occiUpdate() {
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code MachinevirtualboxocciDelete_method
	/**
	 * Called when this Machinevirtualbox instance will be deleted.
	 */
	@Override
	public void occiDelete() {
		LOGGER.debug("occiDelete() called on " + this);
		try {
			manager.removeMachine(this);
			if (machineObserver != null) {
				 machineObserver.removeListener(this);
			}
		} catch (DockerException ex) {
			ex.printStackTrace();
		}
	}
	// End of user code

	//
	// Machinevirtualbox actions.
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
			if (machineObserver == null) {
				machineObserver = new MachineObserver();
				machineObserver.listener(this);
			}
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
