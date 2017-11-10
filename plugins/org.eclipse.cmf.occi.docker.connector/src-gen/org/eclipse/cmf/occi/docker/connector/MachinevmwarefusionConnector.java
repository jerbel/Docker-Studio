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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connector implementation for the OCCI kind: - scheme:
 * http://occiware.org/occi/docker# - term: machinevmwarefusion - title: Machine
 * on VMware Fusion
 */
public class MachinevmwarefusionConnector extends org.eclipse.cmf.occi.docker.impl.MachinevmwarefusionImpl {
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(MachinevmwarefusionConnector.class);

	// Start of user code Machinevmwarefusionconnector_constructor
	/**
	 * Constructs a machinevmwarefusion connector.
	 */
	MachinevmwarefusionConnector() {
		LOGGER.debug("Constructor called on " + this);
		// TODO: Implement this constructor.
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//

	// Start of user code MachinevmwarefusionocciCreate
	/**
	 * Called when this Machinevmwarefusion instance is completely created.
	 */
	@Override
	public void occiCreate() {
		LOGGER.debug("occiCreate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code Machinevmwarefusion_occiRetrieve_method
	/**
	 * Called when this Machinevmwarefusion instance must be retrieved.
	 */
	@Override
	public void occiRetrieve() {
		LOGGER.debug("occiRetrieve() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code Machinevmwarefusion_occiUpdate_method
	/**
	 * Called when this Machinevmwarefusion instance is completely updated.
	 */
	@Override
	public void occiUpdate() {
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code MachinevmwarefusionocciDelete_method
	/**
	 * Called when this Machinevmwarefusion instance will be deleted.
	 */
	@Override
	public void occiDelete() {
		LOGGER.debug("occiDelete() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	//
	// Machinevmwarefusion actions.
	//
}
