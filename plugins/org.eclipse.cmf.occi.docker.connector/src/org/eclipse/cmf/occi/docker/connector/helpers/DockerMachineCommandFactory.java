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

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * This helper factory build docker-machine generic shell commands like >"docker-machine ls"
 * @author cgourdin
 *
 */
public class DockerMachineCommandFactory {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DockerMachineCommandFactory.class);
	
	public static final String OS = System.getProperty("os.name").toLowerCase();
	public static final String DOCKER_MACHINE = "/usr/local/bin/docker-machine";
	
	public static final String dockerMachineCmd = getDockerMachineCommand();
	
	public static String getDockerMachineCommand() {
		String command = "docker-machine";
		if (getOS().equalsIgnoreCase("osx")) {
			command = DOCKER_MACHINE;
		}
		LOGGER.info("Machine OS={}", getOS());
		return command;
	}
	
	// docker-machine command part.
	public static String createInfoCommand(String machineName) {
		String command = String.format("%s inspect %s", dockerMachineCmd, machineName);
		return command;
	}

	public static String createLsCmd() {
		String command = String.format("%s ls", dockerMachineCmd);
		return command;
	}

	public static String createEnvCmd(String machineName) {
		String command = String.format("eval \"$(%s env %s)\" ", dockerMachineCmd, machineName);
		return command;
	}

	public static String getEnvCmd(String machineName) {
		String command = String.format("%s env %s", dockerMachineCmd, machineName);
		return command;
	}

	public static String createStartCommand(String machineName) {
		String command = String.format("%s start %s", dockerMachineCmd, machineName);
		return command;
	}

	public static String createStopCommand(String machineName) {
		String command = String.format("%s stop %s", dockerMachineCmd, machineName);
		return command;
	}

	public static String createReStartCommand(String machineName) {
		String command = String.format("%s restart %s", dockerMachineCmd, machineName);
		return command;
	}

	public static String createRemoveCommand(String machineName) {
		String command = String.format("%s rm %s", dockerMachineCmd, machineName);
		return command;
	}
	
	public static String createUrlCommand(String machineName) {
		String command = String.format("%s url %s", dockerMachineCmd, machineName);
		return command;
	}

	public static String createCertCommand(String machineName) {
		String command = String.format("%s regenerate-certs %s -f", dockerMachineCmd, machineName);
		return command;
	}

	public static String createLsCommand() {
		String command = String.format("%s ls", dockerMachineCmd);
		return command;
	}
	
	public static String createRemoveStagingCommand(String machineName) {
		String command = String.format("%s rm %s staging", dockerMachineCmd, machineName);
		return command;
	}

	public static String createActivateCommand(String machineName) {
		String command = String.format("%s activate %s staging", dockerMachineCmd, machineName);
		return command;
	}
	
	// OS local running part.
	
	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}

	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}

	/**
	 * Get the current OS running on this compute (where designer / connector is executed).
	 * @return
	 */
	public static String getOS() {
		if (isWindows()) {
			return "win";
		} else if (isMac()) {
			return "osx";
		} else if (isUnix()) {
			return "uni";
		} else if (isSolaris()) {
			return "sol";
		} else {
			return "err";
		}
	}
	
}
