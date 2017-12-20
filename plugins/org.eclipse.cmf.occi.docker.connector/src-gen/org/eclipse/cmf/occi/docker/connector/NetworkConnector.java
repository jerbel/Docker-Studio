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

import org.eclipse.cmf.occi.docker.Network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connector implementation for the OCCI kind: - scheme:
 * http://occiware.org/occi/docker# - term: network - title: Network resource
 */
public class NetworkConnector extends org.eclipse.cmf.occi.docker.impl.NetworkImpl {
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(NetworkConnector.class);
	
	// TODO : TO Implement.
	
	private NetworkStateMachine<Network> stateMachine = new NetworkStateMachine<Network>(this) {
		/**
		 * Start the Docker container.
		 */
		@Override
		public void create_execute() {
			System.out.println("EXECUTING Network create action.");
		}
	};

	// Start of user code Networkconnector_constructor
	/**
	 * Constructs a network connector.
	 */
	NetworkConnector() {
		LOGGER.debug("Constructor called on " + this);
		
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//

	// Start of user code NetworkocciCreate
	/**
	 * Called when this Network instance is completely created.
	 */
	@Override
	public void occiCreate() {
		LOGGER.debug("occiCreate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code Network_occiRetrieve_method
	/**
	 * Called when this Network instance must be retrieved.
	 */
	@Override
	public void occiRetrieve() {
		LOGGER.debug("occiRetrieve() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code Network_occiUpdate_method
	/**
	 * Called when this Network instance is completely updated.
	 */
	@Override
	public void occiUpdate() {
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code NetworkocciDelete_method
	/**
	 * Called when this Network instance will be deleted.
	 */
	@Override
	public void occiDelete() {
		LOGGER.debug("occiDelete() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	//
	// Network actions.
	//

}
