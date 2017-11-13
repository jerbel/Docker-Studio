package org.eclipse.cmf.occi.docker.connector;

import org.eclipse.cmf.occi.docker.Network;
import org.eclipse.cmf.occi.infrastructure.NetworkStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		LOGGER.info(className + ":create() - current state is " + networkState);
		
		switch (networkState) {
		case INACTIVE:
			LOGGER.info(className + ":create() - move from inactive to active state");
			create_from_inactive_state();
			network.setOcciNetworkState(NetworkStatus.ACTIVE);
			break;
		case ACTIVE:
			LOGGER.info(className + ":create() - already active state");
			create_from_active_state();
			break;
		case ERROR:
			LOGGER.info(className + ":create() - move from error to active state");
			create_from_error_state();
			network.setOcciNetworkState(NetworkStatus.ACTIVE);
			break;
		default:
			throw new RuntimeException("Must never happen!");
		}
		networkState = network.getOcciNetworkState();
		LOGGER.info(className + ":create() - final state is " + networkState);
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
		LOGGER.info(className + ":create_execute() - DO NOTHING");
	}
	
	
}
