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

import org.eclipse.cmf.occi.docker.Container;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemoryManager {
	private static Logger LOGGER = LoggerFactory.getLogger(MemoryManager.class);

	/**
	 * 
	 * @param host
	 * @param privateKey
	 * @param container
	 * @param value
	 * @throws DockerException
	 */
	public void setMemValue(String host, String privateKey, Container container, String value) throws DockerException {

		if (host == null) {
			throw new DockerException("Known host is not set");
		}
		if (privateKey == null) {
			throw new DockerException("private key is not set");
		}
		if (value == null) {
			throw new DockerException("mem value is not set");
		}
		CgroupHelper.SetValue(host, privateKey, container, CgroupHelper.memory_subsystem, CgroupHelper.memory_max_mem,
				value);

	}

	/**
	 * 
	 * @param host
	 * @param privateKey
	 * @param container
	 * @param value
	 * @throws DockerException
	 */
	public void setSwapValue(String host, String privateKey, Container container, String value) throws DockerException {

		if (host == null) {
			throw new DockerException("Known host is not set");
		}
		if (privateKey == null) {
			throw new DockerException("private key is not set");
		}
		if (value == null) {
			throw new DockerException("swap value is not set");
		}

		CgroupHelper.SetValue(host, privateKey, container, CgroupHelper.memory_subsystem, CgroupHelper.memory_swap,
				value);
	}
}
