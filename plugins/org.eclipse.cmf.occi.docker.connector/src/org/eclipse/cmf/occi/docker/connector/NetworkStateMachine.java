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
import org.eclipse.cmf.occi.infrastructure.NetworkStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * State machine network class.
 * @author Christophe Gourdin
 *
 * @param <T>
 */
public class NetworkStateMachine<T extends Network> {
	
	private static Logger LOGGER = LoggerFactory.getLogger(NetworkStateMachine.class);
	
	private String className = this.getClass().getName();
	
	/**
	 * Reference to the Network resource.
	 */
	protected T network;
	/**
	 * Construct a compute state machine for a given Compute resource.
	 */
	public NetworkStateMachine(T n) {
		network = n;
	}
	
	public void create() {
		NetworkStatus networkState = network.getOcciNetworkState();
		System.out.println(className + ":create() - current state is " + networkState);
		
		switch (networkState) {
		case INACTIVE:
			System.out.println(className + ":create() - move from inactive to active state");
			create_from_inactive_state();
			network.setOcciNetworkState(NetworkStatus.ACTIVE);
			break;
		case ACTIVE:
			System.out.println(className + ":create() - already active state");
			create_from_active_state();
			break;
		case ERROR:
			System.out.println(className + ":create() - move from error to active state");
			create_from_error_state();
			network.setOcciNetworkState(NetworkStatus.ACTIVE);
			break;
		default:
			throw new RuntimeException("Must never happen!");
		}
		networkState = network.getOcciNetworkState();
		System.out.println(className + ":create() - final state is " + networkState);
		if (!networkState.equals(NetworkStatus.ACTIVE)) {
			throw new RuntimeException("Must never happen!");
		}
	}
	
	/**
	 * This method implements the transition from error state for the create action.
	 *
	 * By public voidault, this method calls the error_execute method.
	 */
	public void create_from_error_state() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}

	/**
	 * This method implements the transition from active state for the create action.
	 *
	 * By public voidault, this method calls the create_execute method.
	 */
	public void create_from_active_state() {
		create_execute();
	}

	/**
	 * This method implements the transition from inactive state for the create action.
	 *
	 * By public voidault, this method calls the create_execute method.
	 */
	public void create_from_inactive_state() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}

	/**
	 * 
	 */
	public void create_execute() {
		System.out.println(className + ":create_execute() - DO NOTHING");
	}
	
	
}
