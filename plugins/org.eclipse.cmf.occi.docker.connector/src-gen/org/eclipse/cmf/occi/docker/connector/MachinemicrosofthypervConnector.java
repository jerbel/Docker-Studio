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
 * - term: machinemicrosofthyperv
 * - title: Machine on Microsoft Hyper-V
 */
public class MachinemicrosofthypervConnector extends org.eclipse.cmf.occi.docker.impl.MachinemicrosofthypervImpl
{
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(MachinemicrosofthypervConnector.class);

	// Start of user code Machinemicrosofthypervconnector_constructor
	/**
	 * Constructs a machinemicrosofthyperv connector.
	 */
	MachinemicrosofthypervConnector()
	{
		LOGGER.debug("Constructor called on " + this);
		// TODO: Implement this constructor.
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//
	
	// Start of user code MachinemicrosofthypervocciCreate
	/**
	 * Called when this Machinemicrosofthyperv instance is completely created.
	 */
	@Override
	public void occiCreate()
	{
		LOGGER.debug("occiCreate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code Machinemicrosofthyperv_occiRetrieve_method
	/**
	 * Called when this Machinemicrosofthyperv instance must be retrieved.
	 */
	@Override
	public void occiRetrieve()
	{
		LOGGER.debug("occiRetrieve() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code Machinemicrosofthyperv_occiUpdate_method
	/**
	 * Called when this Machinemicrosofthyperv instance is completely updated.
	 */
	@Override
	public void occiUpdate()
	{
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code MachinemicrosofthypervocciDelete_method
	/**
	 * Called when this Machinemicrosofthyperv instance will be deleted.
	 */
	@Override
	public void occiDelete()
	{
		LOGGER.debug("occiDelete() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	//
	// Machinemicrosofthyperv actions.
	//
}	
