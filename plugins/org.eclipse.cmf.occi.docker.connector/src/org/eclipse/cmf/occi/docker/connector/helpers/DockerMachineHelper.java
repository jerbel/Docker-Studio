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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.cmf.occi.core.Link;
import org.eclipse.cmf.occi.docker.Container;
import org.eclipse.cmf.occi.docker.Contains;
import org.eclipse.cmf.occi.docker.Machine;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.eclipse.cmf.occi.infrastructure.ComputeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.command.InspectContainerResponse;

/**
 * Define docker-machine command helper to use on local compute.
 * 
 * @author Christophe Gourdin
 *
 */
public class DockerMachineHelper {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DockerMachineHelper.class);
	
	/**
	 * Return the address of the compute machine with format: tcp://192.168.1.1
	 * @param compute
	 * @return tcp endpoint.
	 */
	public static String getEndpoint(final Compute compute) throws DockerException {
		String endpoint = null;
		
		if (compute == null) {
			// Considered local.
			LOGGER.info("Use docker on local machine");
			return "tcp://127.0.0.1";
		}
		
		if (compute instanceof Machine) {
			Machine machine = (Machine) compute;
			// Get url from docker-machine command output.
			endpoint = executeUrlCommand(Runtime.getRuntime(), machine.getName());
			LOGGER.warn("Endpoint : " + endpoint);
		} else {
			// TODO : include other extension providers like vmware instance, aws instance etc without pass by docker-machine for sample : else if (compute instanceof InstanceVMware) { instancevmware.getGuestIpv4Address();}
			// Use infrastructure extension to find public ipaddress with networkinterface and mixin ipNetworkInterface.
			throw new DockerException("Not a supported machine, will come in future !");
		}
		return endpoint;
	}
	
	
	/**
	 * Get the endpoint from docker-machine command.
	 * @param runtime
	 * @param machineName
	 * @return
	 */
	public static String executeUrlCommand(Runtime runtime, String machineName) throws DockerException {
		String command = DockerMachineCommandFactory.createUrlCommand(machineName);
		String result = ProcessManager.getOutputCommand(command, runtime);
		return result;
	}
	
	/**
	 * 
	 * @param runtime
	 * @param compute
	 * @return
	 * @throws DockerException
	 */
	public static boolean createHostCmd(Runtime runtime, Compute compute) throws DockerException {
		String command = "";
		String machineName;
		LOGGER.warn(" Run ::==> " + command);
		ProcessManager.runCommand(command, runtime, true);
		// Set machine state
		compute.setOcciComputeState(ComputeStatus.ACTIVE);
		
		if (compute instanceof Machine) {
			machineName = ((Machine)compute).getName();
			return setEnvCmd(runtime, machineName);
		} else {
			// TODO : use external multicloud extensions like vmware extension (if compute instanceof InstanceVMware) set environment on this compute....
			// No op.
			return true;
		}
	}

	/**
	 * 
	 * @param runtime
	 * @return
	 */
	public static boolean listMachinesCmd(Runtime runtime) throws DockerException {
		boolean result = false; 
		if (DockerMachineCommandFactory.OS.equalsIgnoreCase("osx")){
			String command = DockerMachineCommandFactory.createLsCommand();
			result = ProcessManager.runCommand(command, runtime, true);
		} else {
			result = ProcessManager.runCommand("docker-machine ls", runtime, true);
		}
		return result;
	}

	/**
	 * 
	 * @param runtime
	 * @param machineName
	 * @return
	 */
	public static boolean regenerateCert(Runtime runtime, String machineName) throws DockerException {
		String command = DockerMachineCommandFactory.createCertCommand(machineName);
		boolean result = ProcessManager.runCommand(command, runtime, true);
		return result;
	}

	/**
	 * 
	 * @param runtime
	 * @param machine
	 * @return
	 * @throws DockerException
	 */
	public static String inspectHostCmd(Runtime runtime, String machine) throws DockerException {
	    String command = DockerMachineCommandFactory.createInfoCommand(machine);
		return ProcessManager.getOutputCommand(command, runtime);
	}

	
	public static String listHostCmd(Runtime runtime) throws DockerException {
		String command = DockerMachineCommandFactory.createLsCmd();
		return ProcessManager.getOutputCommand(command, runtime);
	}

	public static boolean setEnvCmd(Runtime runtime, String machineName) throws DockerException {
		String command = DockerMachineCommandFactory.createEnvCmd(machineName);
		return ProcessManager.runCommand(command, runtime, true);
	}
	
	public static String getEnvCmd(Runtime runtime, String machineName) throws DockerException {
		String command = DockerMachineCommandFactory.getEnvCmd(machineName);
		return ProcessManager.getOutputCommand(command, runtime);
	}

	public static boolean startCmd(Runtime runtime, String machineName) throws DockerException {
		String command = DockerMachineCommandFactory.createStartCommand(machineName);
		return ProcessManager.runCommand(command, runtime, true);
	}

	public static boolean stopCmd(Runtime runtime, String machineName) throws DockerException {
		String command = DockerMachineCommandFactory.createStopCommand(machineName);
		return ProcessManager.runCommand(command, runtime, true);
	}

	public static boolean restartCmd(Runtime runtime, String machineName) throws DockerException {
		String command = DockerMachineCommandFactory.createReStartCommand(machineName);
		return ProcessManager.runCommand(command, runtime, true);
	}

	public static boolean removeCmd(Runtime runtime, String machineName) throws DockerException {
		String command = DockerMachineCommandFactory.createRemoveCommand(machineName);
		return ProcessManager.runCommand(command, runtime, true);
	}
	
	public static String ipCmd(Runtime runtime, String machineName) throws DockerException {
		String command = DockerMachineCommandFactory.createUrlCommand(machineName);
		String temp = ProcessManager.getOutputCommand(command, runtime).replace("tcp://", "");
		int index = temp.indexOf(":");
		String result = temp.substring(0, index);
		return result;
	}
	
	/**
	 * 
	 * @param machineName
	 * @return
	 * @throws DockerException
	 */
	public static String getCertificatePath(Compute compute) throws DockerException {
		String machineName;
		if (compute == null) {
			machineName = "default";
		} else if (compute instanceof Machine) {
			machineName = ((Machine)compute).getName();
		} else {
			machineName = compute.getTitle();
		}
		String data = getEnvCmd(Runtime.getRuntime(), machineName);
		List<String[]> hosts = new ArrayList<>();
		String[] result = null;
		String dockerCertPathChars = "DOCKER_CERT_PATH";
		if (data != null) {
			String[] st = data.split("\\r?\\n");
			for (String line : st) {
				if (line.startsWith("export") && line.contains(dockerCertPathChars)) {
					String[] lsCmd = line.split("\\s+");
					hosts.add(lsCmd);
					String currentLine = lsCmd[1];
					result = currentLine.split("=");
					return result[1].replaceAll("\"", "");
				}
			}
		}
		String defaultMachineCertPath = System.getProperty("user.home") + File.separator + ".docker" + File.separator + "machine" + File.separator + "machines" + machineName; 
		
		if (new File(defaultMachineCertPath).canRead()) {
			return defaultMachineCertPath; // else DockerException because null certPath
		} else {
			// Must never be here.
			throw new DockerException("No machine certificate path could be found !, please set environnement variable with DOCKER_CERT_PATH");
		}	
	}
	
	/**
	 * List all the containers contains by this compute (on model only).
	 * @param compute the compute where to list the containers.
	 * @return
	 */
	public static List<Container> listContainerModels(Compute compute) {
		// TODO : To export to a specific model management class (to get mixins, get linked entities etc.).
		List<Container> containers = new ArrayList<>();
		for (Link link : compute.getLinks()) {
			if (link instanceof Contains && link.getTarget() instanceof Container) {
				Container container = (Container) link.getTarget();
				containers.add(container);
			}
		}
		return containers;
	}
	
}
