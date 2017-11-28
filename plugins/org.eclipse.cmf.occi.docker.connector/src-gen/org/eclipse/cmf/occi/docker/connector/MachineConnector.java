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

import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.docker.connector.exceptions.ValueNotSetException;
import org.eclipse.cmf.occi.docker.connector.utils.ModelHandler;
import org.eclipse.cmf.occi.infrastructure.RestartMethod;
import org.eclipse.cmf.occi.infrastructure.StopMethod;
import org.eclipse.cmf.occi.infrastructure.SuspendMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connector implementation for the OCCI kind: - scheme:
 * http://occiware.org/occi/docker# - term: machine - title: Machine Resource
 */
public class MachineConnector extends org.eclipse.cmf.occi.docker.impl.MachineImpl {
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(MachineConnector.class);

	private ComputeStateMachine<MachineConnector> stateMachine = new ComputeStateMachine<MachineConnector>(this);
	
	// Start of user code Machineconnector_constructor
	/**
	 * Constructs a machine connector.
	 */
	MachineConnector() {
		LOGGER.debug("Constructor called on " + this);
		// TODO: Implement this constructor.
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//

	// Start of user code MachineocciCreate
	/**
	 * Called when this Machine instance is completely created.
	 */
	@Override
	public void occiCreate() {
		LOGGER.debug("occiCreate() called on " + this);
		start();
	}
	// End of user code

	// Start of user code Machine_occiRetrieve_method
	/**
	 * Called when this Machine instance must be retrieved.
	 */
	@Override
	public void occiRetrieve() {
		LOGGER.debug("occiRetrieve() called on " + this);
		MachineManager machineManager = new MachineManager(this) {
			
			@Override
			public String getDriverName() {
				// TODO : Check if localhost machine is applicable with dockerMachine !
				return "machine";
			}
			
			@Override
			public void appendDriverParameters(StringBuilder sb) throws ValueNotSetException {
				sb = new StringBuilder();
			}
		};
		try {
			machineManager.synchronize();
		} catch (DockerException ex) {
			LOGGER.error("Error while retrieving informations about this machine : " + this.getName() + " --> " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	// End of user code
	
	
	// Start of user code Machine_occiUpdate_method
	/**
	 * Called when this Machine instance is completely updated.
	 */
	@Override
	public void occiUpdate() {
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code MachineocciDelete_method
	/**
	 * Called when this Machine instance will be deleted.
	 */
	@Override
	public void occiDelete() {
		LOGGER.debug("occiDelete() called on " + this);
	}
	// End of user code

	@Override
	public void restart(RestartMethod method) {
		// TODO Auto-generated method stub
		super.restart(method);
	}

	@Override
	public void start() {
		try {
			stateMachine.start();
		} catch (DockerException ex) {
			LOGGER.error("Exception thrown while starting the machine : " + getName());
			ex.printStackTrace();
		}
	}

	@Override
	public void stop(StopMethod method) {
		try {
			stateMachine.stop(method);
		} catch (DockerException ex) {
			LOGGER.error("Exception thrown while stopping the machine : " + getName());
			ex.printStackTrace();
		}
	}

	@Override
	public void suspend(SuspendMethod method) {
		try {
			stateMachine.suspend(method);
		} catch (DockerException ex) {
			LOGGER.error("Exception thrown while suspending the machine : " + getName());
			ex.printStackTrace();
		}
	}

	//
	// Machine actions.
	//
}
