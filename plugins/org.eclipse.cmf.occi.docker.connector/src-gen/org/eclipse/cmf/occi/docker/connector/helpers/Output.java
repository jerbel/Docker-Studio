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
package org.eclipse.cmf.occi.docker.connector.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Define synchronized methods to print and flush outputs (sysout and syserr).
 * 
 * @author Christophe Gourdin
 *
 */
public class Output {

	private static Logger LOGGER = LoggerFactory.getLogger(Output.class);

	public synchronized static void print(final String message) {
		LOGGER.warn(message);
	}

	public synchronized static void printErr(final String message) {
		LOGGER.error(message);
	}

	public synchronized static void printlnErr(final String message) {
		LOGGER.error(message);
	}

	public synchronized static void flushOut() {
		System.out.flush();
	}

	public synchronized static void flushErr() {
		System.err.flush();
	}

}
