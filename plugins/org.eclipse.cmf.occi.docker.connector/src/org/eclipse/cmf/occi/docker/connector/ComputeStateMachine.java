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

import org.eclipse.cmf.occi.infrastructure.Compute;
import org.eclipse.cmf.occi.infrastructure.ComputeStatus;
import org.eclipse.cmf.occi.infrastructure.RestartMethod;
import org.eclipse.cmf.occi.infrastructure.StopMethod;
import org.eclipse.cmf.occi.infrastructure.SuspendMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Christophe Gourdin
 *
 */
public class ComputeStateMachine<T extends Compute> {
	private static Logger LOGGER = LoggerFactory.getLogger(ComputeStateMachine.class);
	
	private String className = this.getClass().getName();
	
	/**
	 * Reference to the Compute resource.
	 */
	protected T compute;
	/**
	 * Construct a compute state machine for a given Compute resource.
	 */
	public ComputeStateMachine(T c) {
		compute = c;
	}
	
	/**
	 * Start OCCI Action.
	 */
	public void start() {
		
		ComputeStatus currentState = compute.getOcciComputeState();
		
		LOGGER.info(className + ":start() - current state is " + currentState);
		
		switch (currentState) {
		case INACTIVE:
			LOGGER.info(className + ":start() - move from inactive to active state");
			start_from_inactive_state();
			compute.setOcciComputeState(ComputeStatus.ACTIVE);
			break;
		case ACTIVE:
			LOGGER.info(className + ":start() - already active state");
			start_from_active_state();
			break;
		case SUSPENDED:
			LOGGER.info(className + ":start() - move from suspended to active state");
			start_from_suspended_state();
			compute.setOcciComputeState(ComputeStatus.ACTIVE);
			break;
		default:
			throw new RuntimeException("Must never happen!");
		}
		
		LOGGER.info(className + ":start() - final state is " + compute.getOcciComputeState());
		
		if (!compute.getOcciComputeState().equals(ComputeStatus.ACTIVE)) { 
			throw new RuntimeException("Must never happen!");
		}
	}

	/**
	 * StartAll Modeler Action.
	 */
	public void startAll() {
		ComputeStatus currentState = compute.getOcciComputeState();
		LOGGER.info(className + ":startAll() - current state is " + currentState);
		
		switch (currentState) {
		case INACTIVE:
			LOGGER.info(className + ":startAll() - move from inactive to active state");
			startAll_from_inactive_state();
			compute.setOcciComputeState(ComputeStatus.ACTIVE);
			break;
		case ACTIVE:
			LOGGER.info(className + ":startAll() - already active state");
			start_from_active_state();
			break;
		case SUSPENDED:
			LOGGER.info(className + ":startAll() - move from suspended to active state");
			start_from_suspended_state();
			compute.setOcciComputeState(ComputeStatus.ACTIVE);
			break;
		default:
			throw new RuntimeException("Must never happen!");
		}
		LOGGER.info(className + ":startAll() - final state is " + compute.getOcciComputeState());
		
		if (!compute.getOcciComputeState().equals(ComputeStatus.ACTIVE)) { 
			throw new RuntimeException("Must never happen!");
		}
	}

	/**
	 * This method implements the transition from inactive state for the start action.
	 *
	 * By default, this method calls the start_execute method.
	 */
	public void start_from_inactive_state() {
		start_execute();
	}

	public void startAll_from_inactive_state() {
		startAll_execute();
	}

	/**
	 * This method implements the transition from active state for the start action.
	 *
	 * By default, this method does nothing.
	 */
	public void start_from_active_state() {
		LOGGER.info(className + ":start_from_active_state() - DO NOTHING");
		startAll_execute();
	}

	/**
	 * This method implements the transition from suspended state for the start action.
	 *
	 * By default, this method calls the start_execute method.
	 */
	public void start_from_suspended_state() {
		start_execute();
	}

	/**
	 * This method is the default implementation of the start action.
	 */
	public void start_execute() {
		LOGGER.info(className + ":start_execute() - DO NOTHING");
	}

	public void startAll_execute() {
		LOGGER.info(className + ":startAll_execute() - DO NOTHING");
	}

	/**
	 * Stop OCCI Action.
	 */
	public void stop(StopMethod method) {
		ComputeStatus currentState = compute.getOcciComputeState();
		LOGGER.info(className + ":stop(" + method + ") - current state is " + compute.getOcciComputeState());
		
		switch (currentState) {
		case INACTIVE:
			LOGGER.info(className + ":stop() - already inactive state");
			stop_from_inactive_state(method);
			break;
		case ACTIVE:
			LOGGER.info(className + ":stop() - move from active to inactive state");
			stop_from_active_state(method);
			compute.setOcciComputeState(ComputeStatus.INACTIVE);
			break;
		case SUSPENDED:
			LOGGER.info(className + ":stop() - move from suspended to inactive state");
			stop_from_suspended_state(method);
			compute.setOcciComputeState(ComputeStatus.INACTIVE);
			break;
		default:
			throw new RuntimeException("Must never happen!");
		}
		
		LOGGER.info(className + ":stop() - final state is " + compute.getOcciComputeState());
		if (!compute.getOcciComputeState().equals(ComputeStatus.INACTIVE)) {
			throw new RuntimeException("Must never happen!");
		}
		
	}

	/**
	 * This method implements the transition from inactive state for the stop action.
	 *
	 * By default, this method does nothing.
	 */
	public void stop_from_inactive_state(StopMethod method) {
		LOGGER.info(className + ":stop_from_inactive_state() - DO NOTHING");
	}

	/**
	 * This method implements the transition from active state for the stop action.
	 *
	 * By default, this method calls the stop_execute method.
	 */
	public void stop_from_active_state(StopMethod method) {
		stop_execute(method);
	}

	/**
	 * This method implements the transition from suspended state for the stop action.
	 *
	 * By default, this method calls the stop_execute method.
	 */
	public void stop_from_suspended_state(StopMethod method) {
		stop_execute(method);
	}

	/**
	 * This method is the default implementation of the stop action.
	 */
	public void stop_execute(StopMethod method) {
		LOGGER.info(className + ":stop_execute(" + method + ") - DO NOTHING");
	}

	/**
	 * Restart OCCI Action.
	 */
	public void restart(RestartMethod method) {
		
		ComputeStatus currentState = compute.getOcciComputeState();
		LOGGER.info(className + ":restart(" + method + ") - current state is " + currentState);
		switch (currentState) {
		case INACTIVE:
			LOGGER.info(className + ":restart(" + method + ')' + " - move from inactive to active state");
			restart_from_inactive_state(method);
			compute.setOcciComputeState(ComputeStatus.ACTIVE);
			break;
		case ACTIVE:
			LOGGER.info(className + ":restart(" + method + ')' + " - move from active to active state");
			restart_from_active_state(method);
			compute.setOcciComputeState(ComputeStatus.ACTIVE);
			break;
		case SUSPENDED:
			LOGGER.info(className + ":stop() - move from suspended to active state");
			restart_from_suspended_state(method);
			compute.setOcciComputeState(ComputeStatus.ACTIVE);
			break;
		default:
			throw new RuntimeException("Must never happen!");
		}
		
		LOGGER.info(className + ":restart(" + method + ')' + " - final state is " + compute.getOcciComputeState());
		if(!compute.getOcciComputeState().equals(ComputeStatus.ACTIVE)) 
			throw new RuntimeException("Must never happen!");
	}

	/**
	 * This method implements the transition from inactive state for the restart action.
	 *
	 * By default, this method calls the restart_execute method.
	 */
	public void restart_from_inactive_state(RestartMethod method) {
		restart_execute(method);
	}

	/**
	 * This method implements the transition from active state for the restart action.
	 *
	 * By default, this method calls the restart_execute method.
	 */
	public void restart_from_active_state(RestartMethod method) {
		restart_execute(method);
	}

	/**
	 * This method implements the transition from suspended state for the restart action.
	 *
	 * By default, this method calls the restart_execute method.
	 */
	public void restart_from_suspended_state(RestartMethod method) {
		restart_execute(method);
	}

	/**
	 * This method is the default implementation of the restart action.
	 */
	public void restart_execute(RestartMethod method) {
		LOGGER.info(className + ":restart_execute(" + method + ") - DO NOTHING");
	}

	/**
	 * Suspend OCCI Action.
	 */
	public void suspend(SuspendMethod method) {
		
		ComputeStatus currentState = compute.getOcciComputeState();
		LOGGER.info(className + ":suspend(" + method + ") - current state is " + currentState);
		
		
		switch (currentState) {
		case INACTIVE:
			LOGGER.info(className + ":suspend() - move from inactive to suspended state");
			suspend_from_inactive_state(method);
			compute.setOcciComputeState(ComputeStatus.SUSPENDED);
			break;
		case ACTIVE:
			LOGGER.info(className + ":suspend() - move from active to suspended state");
			suspend_from_active_state(method);
			compute.setOcciComputeState(ComputeStatus.SUSPENDED);
			break;
		case SUSPENDED:
			LOGGER.info(className + ":suspend() - already suspended state");
			suspend_from_suspended_state(method);
			compute.setOcciComputeState(ComputeStatus.SUSPENDED);
			break;
		default:
			throw new RuntimeException("Must never happen!");
		}
		LOGGER.info(className + ":suspend() - final state is " + compute.getOcciComputeState());
		if (!compute.getOcciComputeState().equals(ComputeStatus.SUSPENDED)) { 
			throw new RuntimeException("Must never happen!");
		}
		
	}

	/**
	 * This method implements the transition from inactive state for the suspend action.
	 *
	 * By default, this method calls the suspend_execute method.
	 */
	public void suspend_from_inactive_state(SuspendMethod method) {
		suspend_execute(method);
	}

	/**
	 * This method implements the transition from active state for the suspend action.
	 *
	 * By default, this method calls the suspend_execute method.
	 */
	public void suspend_from_active_state(SuspendMethod method) {
		suspend_execute(method);
	}

	/**
	 * This method implements the transition from suspended state for the suspend action.
	 *
	 * By default, this method does nothing.
	 */
	public void suspend_from_suspended_state(SuspendMethod method) {
		LOGGER.info(className + ":suspend_from_suspended_state() - DO NOTHING");
	}

	/**
	 * This method is the default implementation of the suspend action.
	 */
	public void suspend_execute(SuspendMethod method) {
		LOGGER.info(className + ":suspend_execute(" + method + ") - DO NOTHING");
	}
	
	
}
