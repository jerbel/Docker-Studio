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
import org.eclipse.cmf.occi.docker.connector.DockerClientManager;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CgroupHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(CgroupHelper.class);

	// For more information go to:
	// https://access.redhat.com/documentation/en-US/Red_Hat_Enterprise_Linux/6/html/Resource_Management_Guide/index.html
	static final String cGroupPath = "/sys/fs/cgroup/";

	// List of Subsystems
	public static final String blkio_subsystem = "blkio";
	public static final String cpuset_subsystem = "cpuset";
	public static final String cpu_subsystem = "cpu";
	public static final String cpuacct_subsystem = "cpuacct";
	public static final String devices_subsystem = "devices";
	public static final String freezer_subsystem = "freezer";
	public static final String memory_subsystem = "memory";
	public static final String netcls_subsystem = "net_cls";

	// List of Files
	public static final String blkio_write = "blkio.throttle.write_bps_device";
	public static final String blkio_read = "blkio.throttle.read_bps_device";

	public static final String memory_max_mem = "memory.limit_in_bytes";
	public static final String memory_swap = "memory.memsw.limit_in_bytes";

	public static final String cpuset_cpus = "cpuset.cpus";
	public static final String net_cls_classid = "net_cls.classid";

	public static final String cpu_cfs_period = "cpu.cfs_period_us";
	public static final String cpu_cfs_quota = "cpu.cfs_quota_us";

	/**
	 * 
	 * @param host
	 * @param privateKey
	 * @param container
	 * @param subsystem
	 * @param file
	 * @param value
	 * @throws DockerException
	 */
	public static void SetValue(String host, String privateKey, Container container, String subsystem, String file,
			String value) throws DockerException {
		String FilePath = cGroupPath + subsystem + "/docker/" + container.getContainerid() + "/" + file;
		String command = "";
		DockerClientManager dockerClientManager = new DockerClientManager();

		if (file.equalsIgnoreCase(memory_max_mem)) {
			command = "echo '" + Integer.parseInt("" + Float.parseFloat(value)) + "' > " + FilePath;
			LOGGER.info("EXECUTE COMMAND: {}", command);
			dockerClientManager.connect(host, privateKey, command);
		} else if (file.equalsIgnoreCase(cpuset_cpus)) {
			command = "echo '" + cpuSetGenerator(value, container) + "' > " + FilePath;
			LOGGER.info("EXECUTE COMMAND: {}", command);
			dockerClientManager.connect(host, privateKey, command);
		} else if (file.equalsIgnoreCase(net_cls_classid)) {
			command = "echo '" + Integer.parseInt("" + Float.parseFloat(value)) + "' > " + FilePath;
			LOGGER.info("EXECUTE COMMAND: {}", command);
			dockerClientManager.connect(host, privateKey, command);
		} else if (file.equalsIgnoreCase(memory_swap)) {
			command = "echo '" + Integer.parseInt("" + Float.parseFloat(value)) + "' > " + FilePath;
			LOGGER.info("EXECUTE COMMAND: {}", command);
			dockerClientManager.connect(host, privateKey, command);
		}

		// val String command = "echo '" + cpuSetGenerator(value, container) + "' > " +
		// FilePath
		// println("EXECUTE COMMAND: "+ command)
		// dockerContainerManager.connect(host, privateKey, command)
	}

	/**
	 * 
	 * @param nbCores
	 * @param container
	 * @return
	 */
	public static String cpuSetGenerator(String nbCores, Container container) {
		if (Integer.valueOf(nbCores) > 1 && Integer.valueOf(nbCores) <= container.getCoreMax()) {
			String cpuSet = String.format("0-%s", nbCores);
			return cpuSet;
		}
		return "0";
	}

}
