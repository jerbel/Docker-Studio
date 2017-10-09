/**
 * Copyright (c) 2016-2017 Inria
 *  
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * - Philippe Merle <philippe.merle@inria.fr>
 * - Faiez Zalila <faiez.zalila@inria.fr>
 *
 * Generated at Mon Oct 02 09:35:55 CEST 2017 from platform:/resource/org.eclipse.cmf.occi.docker/model/docker.occie by org.eclipse.cmf.occi.core.gen.connector
 */
package org.eclipse.cmf.occi.docker.connector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connector implementation for the OCCI kind:
 * - scheme: http://occiware.org/occi/docker#
 * - term: machineopenstack
 * - title: Machine on OpenStack
 */
public class MachineopenstackConnector extends org.eclipse.cmf.occi.docker.impl.MachineopenstackImpl
{
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(MachineopenstackConnector.class);

	// Start of user code Machineopenstackconnector_constructor
	/**
	 * Constructs a machineopenstack connector.
	 */
	MachineopenstackConnector()
	{
		LOGGER.debug("Constructor called on " + this);
		// TODO: Implement this constructor.
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
	public void occiCreate()
	{
		LOGGER.debug("occiCreate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code Machineopenstack_occiRetrieve_method
	/**
	 * Called when this Machineopenstack instance must be retrieved.
	 */
	@Override
	public void occiRetrieve()
	{
		LOGGER.debug("occiRetrieve() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code Machineopenstack_occiUpdate_method
	/**
	 * Called when this Machineopenstack instance is completely updated.
	 */
	@Override
	public void occiUpdate()
	{
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code MachineopenstackocciDelete_method
	/**
	 * Called when this Machineopenstack instance will be deleted.
	 */
	@Override
	public void occiDelete()
	{
		LOGGER.debug("occiDelete() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	//
	// Machineopenstack actions.
	//
}	
