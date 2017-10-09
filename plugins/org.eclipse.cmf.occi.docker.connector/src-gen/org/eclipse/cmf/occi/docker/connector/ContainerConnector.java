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
 * - term: container
 * - title: Container Resource
 */
public class ContainerConnector extends org.eclipse.cmf.occi.docker.impl.ContainerImpl
{
	/**
	 * Initialize the logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(ContainerConnector.class);

	// Start of user code Containerconnector_constructor
	/**
	 * Constructs a container connector.
	 */
	ContainerConnector()
	{
		LOGGER.debug("Constructor called on " + this);
		// TODO: Implement this constructor.
	}
	// End of user code
	//
	// OCCI CRUD callback operations.
	//
	
	// Start of user code ContainerocciCreate
	/**
	 * Called when this Container instance is completely created.
	 */
	@Override
	public void occiCreate()
	{
		LOGGER.debug("occiCreate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code Container_occiRetrieve_method
	/**
	 * Called when this Container instance must be retrieved.
	 */
	@Override
	public void occiRetrieve()
	{
		LOGGER.debug("occiRetrieve() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code Container_occiUpdate_method
	/**
	 * Called when this Container instance is completely updated.
	 */
	@Override
	public void occiUpdate()
	{
		LOGGER.debug("occiUpdate() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	// Start of user code ContainerocciDelete_method
	/**
	 * Called when this Container instance will be deleted.
	 */
	@Override
	public void occiDelete()
	{
		LOGGER.debug("occiDelete() called on " + this);
		// TODO: Implement this callback or remove this method.
	}
	// End of user code

	//
	// Container actions.
	//
	// Start of user code Container_Kind_create_action
	/**
	 * Implement OCCI action:
     * - scheme: http://occiware.org/occi/docker/container/action#
     * - term: create
     * - title: 
	 */
	@Override
	public void create()
	{
		LOGGER.debug("Action create() called on " + this);

		// TODO: Implement how to create this container.
	}
		// End of user code

	// Start of user code Container_Kind_stop_action
	/**
	 * Implement OCCI action:
     * - scheme: http://occiware.org/occi/docker/container/action#
     * - term: stop
     * - title: 
	 */
	@Override
	public void stop()
	{
		LOGGER.debug("Action stop() called on " + this);

		// TODO: Implement how to stop this container.
	}
		// End of user code

	// Start of user code Container_Kind_run_action
	/**
	 * Implement OCCI action:
     * - scheme: http://occiware.org/occi/docker/container/action#
     * - term: run
     * - title: 
	 */
	@Override
	public void run()
	{
		LOGGER.debug("Action run() called on " + this);

		// TODO: Implement how to run this container.
	}
		// End of user code

	// Start of user code Container_Kind_pause_action
	/**
	 * Implement OCCI action:
     * - scheme: http://occiware.org/occi/docker/container/action#
     * - term: pause
     * - title: 
	 */
	@Override
	public void pause()
	{
		LOGGER.debug("Action pause() called on " + this);

		// TODO: Implement how to pause this container.
	}
		// End of user code

	// Start of user code Container_Kind_unpause_action
	/**
	 * Implement OCCI action:
     * - scheme: http://occiware.org/occi/docker/container/action#
     * - term: unpause
     * - title: 
	 */
	@Override
	public void unpause()
	{
		LOGGER.debug("Action unpause() called on " + this);

		// TODO: Implement how to unpause this container.
	}
		// End of user code

	// Start of user code Container_Kind_kill_action
	/**
	 * Implement OCCI action:
     * - scheme: http://occiware.org/occi/docker/container/action#
     * - term: kill
     * - title: 
	 */
	@Override
	public void kill(final String signal)
	{
		LOGGER.debug("Action kill(" + "signal=" + signal + ") called on " + this);

		// TODO: Implement how to kill this container.
	}
		// End of user code

}	
