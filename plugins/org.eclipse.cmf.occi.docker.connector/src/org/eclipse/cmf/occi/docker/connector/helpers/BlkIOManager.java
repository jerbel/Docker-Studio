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
import org.eclipse.cmf.occi.docker.connector.utils.DockerUtil;

public class BlkIOManager {
	/**
	 * 
	 * @param host
	 * @param privateKey
	 * @param container
	 * @param value
	 * @throws DockerException
	 */
	public void setReadValue(String host, String privateKey, Container container, String value) throws DockerException {
		if (host == null) {
			throw new DockerException("Known host is not set");
		}
		if (privateKey == null) {
			throw new DockerException("private key is not set");
		}
		if (value == null) {
			throw new DockerException("read value is not set");
		}
		if (DockerUtil.isInteger(value)) {
			if (value.equals("-1")) {
				CgroupHelper.SetValue(host, privateKey, container, CgroupHelper.blkio_subsystem,
						CgroupHelper.blkio_read, "");
			} else {
				String newValue = "8:0 " + value;
				CgroupHelper.SetValue(host, privateKey, container, CgroupHelper.blkio_subsystem,
						CgroupHelper.blkio_read, newValue);
			}
		}
	}

	/**
	 * 
	 * @param host
	 * @param privateKey
	 * @param container
	 * @param value
	 * @throws DockerException
	 */
	public void setWriteValue(String host, String privateKey, Container container, String value) throws DockerException {
		if (host == null) {
			throw new DockerException("Known host is not set");
		}
		if (privateKey == null) {
			throw new DockerException("private key is not set");
		}
		if (value == null) {
			throw new DockerException("write value is not set");
		}
		
		if (DockerUtil.isInteger(value)) {
			if (value.equals("-1")) {
				CgroupHelper.SetValue(host, privateKey, container, CgroupHelper.blkio_subsystem,
						CgroupHelper.blkio_write, "");
				
			} else {
				String newValue = "8:0 " + value;
						CgroupHelper.SetValue(host, privateKey, container, CgroupHelper.blkio_subsystem,
						CgroupHelper.blkio_write, newValue);
			}
		}
	}
}
