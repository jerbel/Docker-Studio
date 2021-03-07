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
package org.eclipse.cmf.occi.docker.connector.manager;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.cmf.occi.core.AttributeState;
import org.eclipse.cmf.occi.core.MixinBase;
import org.eclipse.cmf.occi.core.Resource;
import org.eclipse.cmf.occi.docker.Container;
import org.eclipse.cmf.occi.docker.Contains;
import org.eclipse.cmf.occi.docker.Machine;
import org.eclipse.cmf.occi.docker.Network;
import org.eclipse.cmf.occi.docker.Networklink;
import org.eclipse.cmf.occi.docker.Volumesfrom;
import org.eclipse.cmf.occi.docker.connector.ContainerConnector;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.docker.connector.helpers.DockerConfigurationHelper;
import org.eclipse.cmf.occi.docker.connector.helpers.DockerMachineHelper;
import org.eclipse.cmf.occi.docker.connector.helpers.ProcessManager;
import org.eclipse.cmf.occi.docker.connector.observer.StatsCallBack;
import org.eclipse.cmf.occi.docker.connector.utils.EventCallBack;
import org.eclipse.cmf.occi.docker.connector.utils.ModelHandler;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.eclipse.cmf.occi.infrastructure.ComputeStatus;
import org.eclipse.cmf.occi.infrastructure.Networkinterface;
import org.eclipse.cmf.occi.infrastructure.Ssh_key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.CreateNetworkCmd;
import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.command.ExecCreateCmd;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.exception.InternalServerErrorException;
import com.github.dockerjava.api.exception.NotModifiedException;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.ContainerNetwork;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Link;
import com.github.dockerjava.api.model.LxcConf;
import com.github.dockerjava.api.model.Network.Ipam.Config;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports.Binding;
import com.github.dockerjava.api.model.RestartPolicy;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.api.model.VolumesFrom;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import com.google.common.collect.Multimap;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * Manage the docker client and used by connector when executing actions.
 * 
 * @author Christophe Gourdin
 *
 */
public class DockerClientManager {
	
	public static final String DEFAULT_IMAGE = "ubuntu";
	
	//the name of the image that is capable of dynamic port configuration for ssh server. See lennse/ubuntuworkflow docker hub page.
	public static final String DOCKER_SSH_PORTABLE_IMAGE = "lennse/ubuntuworkflow:portable";
	public static final String IMAGE_PORTABLE_SUBSTRING = "portable";

	private DockerClient dockerClient = null;

	private Compute compute = null;

	private Map<String, List<String>> images = new HashMap<>();

	private static Logger LOGGER = LoggerFactory.getLogger(DockerClientManager.class);
	
	private boolean onLocalHost;

	// private PreferenceValues properties = new PreferenceValues();

	public static final String DEFAULT_IMAGE_NAME = "ubuntu";

	public DockerClientManager(Compute compute) throws DockerException {
		this.compute = compute;
		// Build a docker client related to this compute, if compute is null,
		// dockerclient will be relative to this local machine.
		this.dockerClient = DockerConfigurationHelper.buildDockerClient(compute);
		setOnLocalHost();
	}

	public DockerClientManager(Compute compute, EventCallBack event) throws DockerException {
		this.compute = compute;
		this.dockerClient = DockerConfigurationHelper.buildDockerClient(compute);
		this.dockerClient.eventsCmd().exec(event);
		setOnLocalHost();
	}
	
	public void setOnLocalHost() {
		try {
			onLocalHost = DockerMachineHelper.getDockerHost(compute) == null;
		} catch (DockerException e) {
			onLocalHost = true;
		}
		if(onLocalHost) {
			LOGGER.info("Target docker host is localhost");
		} else {
			LOGGER.info("Target docker host is not localhost");
		}
	}
	
	public boolean isOnLocalHost() {
		return onLocalHost;
	}

	public DockerClientManager() {

	}

	public DockerClient getDockerClient() {
		return dockerClient;
	}

	public void setDockerClient(DockerClient dockerClient) {
		this.dockerClient = dockerClient;
	}

	public Compute getCompute() {
		return compute;
	}

	public void setCompute(Compute compute) {
		this.compute = compute;
	}

	public Map<String, List<String>> getImages() {
		return images;
	}

	public void setImages(Map<String, List<String>> images) {
		this.images = images;
	}

	// Action methods part.

	/**
	 * Is the container exist on real infra and exist in compute machine.
	 * 
	 * @param compute
	 * @param containerId
	 *            to check
	 * @return true if the container exist on infastructure and exist on compute.
	 * @throws DockerException
	 */
	public boolean containerIsInsideMachine(Compute compute, final String containerId) throws DockerException {

		if(containerId == null) {
			return false;
		}
		// Check if it exist on compute.
		InspectContainerResponse containerResponse = inspectContainer(compute, containerId);
		String name = containerResponse.getName().replaceAll("/", "");

		// On model level...
		List<Container> listContainer = DockerMachineHelper.listContainerModels(compute);

		for (Container ec : listContainer) {
			if (ec.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param compute
	 * @param container
	 * @return
	 * @throws DockerException
	 */
	public boolean containerIsInsideMachine(Compute compute, final Container container) throws DockerException {

		// Check if it exist on compute.
		InspectContainerResponse containerResponse = inspectContainer(compute, container);
		if (containerResponse == null) {
			// Not found on real virtal machine.
			return false;
		}
		String name = containerResponse.getName().replaceAll("/", "");

		// On model level...
		List<Container> listContainer = DockerMachineHelper.listContainerModels(compute);

		for (Container ec : listContainer) {
			if (ec.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param compute
	 * @param container
	 * @return A response docker-java object CreateContainerResponse.
	 * @throws DockerException
	 */
	public CreateContainerResponse createContainer(Compute computeMachine, Container container) throws DockerException {

		preCheckDockerClient(computeMachine);

		CreateContainerCmd createContainer = containerBuilder(container, null);

		CreateContainerResponse createContainerResponse = createContainer.exec();
		container.setContainerid(createContainerResponse.getId());
		System.out.println("Created container:" + container.getContainerid());

		return createContainerResponse;
	}

	/**
	 * 
	 * @param computeMachine
	 * @param container
	 * @param containerDependency
	 * @return
	 * @throws DockerException
	 */
	public Map<DockerClient, CreateContainerResponse> createContainer(Compute computeMachine, Container container,
			Multimap<String, String> containerDependency) throws DockerException {
		preCheckDockerClient(computeMachine);
		CreateContainerCmd createContainer = containerBuilder(container, containerDependency);

		CreateContainerResponse createContainerResponse = createContainer.exec();
		container.setContainerid(createContainerResponse.getId());
		System.out.println("Created container: " + container.getContainerid());

		Map<DockerClient, CreateContainerResponse> result = new LinkedHashMap<DockerClient, CreateContainerResponse>();
		result.put(dockerClient, createContainerResponse);

		return result;
	}
	
	/**
	 * Checks if the container has the suited portable ssh image and a ssh port specified.
	 * For more information check out the image description of lennse/ubuntuworkflow on Docker Hub.
	 * 
	 * @param container
	 * @return
	 */
	public static boolean checkSshPortKonfiguration(Container container) {
		if(container.getImage() == null)
			return false;
		return isImagePortable(container.getImage()) && getSshPort(container) != null && hasSshKey(container);
	}
	
	private static boolean hasSshKey(Container container) {
		if(getKey(container).equals("")) {
			return false;
		}
		return true;
	}

	public static boolean isImagePortable(String image) {
		if(image.toLowerCase().contains(IMAGE_PORTABLE_SUBSTRING)){
			return true;
		} else if(image.toLowerCase().contains("rwm")) {
			return true;
		}
		return false;
	}
	
	public static boolean isInHostNetwork(Container container) {
		if(container.getNet() == null)
			return false;
		return container.getNet().equals("host");
	}
	
	public static void verifySshPortKonfiguration(Container container) {
		if(container.getImage() != null) {
			if(isImagePortable(container.getImage()) && getSshPort(container) == null) {
				LOGGER.error("The image contains " + IMAGE_PORTABLE_SUBSTRING + " in its name but no ssh port was specified");
			}
		}
	}
	
	/**
	 * Checks if the ports string has a entry without an portmapping (no ':'). In
	 * this case its a ssh port used in host networks.
	 * 
	 * @param container
	 * @return
	 */
	public static String getSshPort(Container container) {
		return readSshPort(container.getPorts());
	}
	
	public static String readSshPort(String ports) {
		if(ports == null)
			return null;
		String[] portsArray = ports.split(";");
		for(String port : portsArray) {
			if(port.split(":").length == 1) {
				if (port.contains("/tcp")) {
					port = port.replace("/tcp", "");
				}
				if (port.contains("/udp")) {
					port = port.replace("/udp", "");
				}
				return port;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param container
	 * @param containerDependency
	 *            may be null if no dependencies.
	 * @return
	 * @throws DockerException
	 */
	public CreateContainerCmd containerBuilder(Container container, Multimap<String, String> containerDependency)
			throws DockerException {
		CreateContainerCmd createContainer = null;
		
		verifySshPortKonfiguration(container);
		
		if (container.getImage() == null || container.getImage().trim().isEmpty()) {
			createContainer = this.dockerClient.createContainerCmd(DEFAULT_IMAGE_NAME);
		} else {
			createContainer = this.dockerClient.createContainerCmd(container.getImage().trim());
		}

		if (container.getCpuShares() > 0) {
			createContainer.withCpuShares(container.getCpuShares());
		}

		// Add hostname container.
		if (container.getOcciComputeHostname() != null && !container.getOcciComputeHostname().trim().isEmpty()) {
			createContainer.withHostName(StringUtils.deleteWhitespace(container.getOcciComputeHostname()));
		}

		// Add hostnames to /etc/hosts in the container.

		// ArrayOfString addHosts = container.getAddHost();

		if (container.getAddHost() != null && !container.getAddHost().trim().isEmpty()) {

			createContainer.withExtraHosts(container.getAddHost().split(";"));
			// createContainer.withExtraHosts(addHosts.getValues());
		}

		if (StringUtils.isNotBlank(container.getCpuSetCpus())) {
			createContainer.withCpusetCpus(container.getCpuSetCpus());
		}

		if (StringUtils.isNotBlank(container.getCpuSetMems())) {
			createContainer.withCpusetCpus(container.getCpuSetMems());
		}
		if (container.getPrivileged()) {
			createContainer.withPrivileged(container.getPrivileged());
		}

		if (container.getDns() != null && !container.getDns().trim().isEmpty()) {
			createContainer.withDns(container.getDns().split(";"));
		}

		// if (container.getDns() != null && !container.getDns().getValues().isEmpty())
		// {
		// createContainer.withDns(container.getDns().getValues());
		// }

		if (container.getEnvironment() != null && !container.getEnvironment().trim().isEmpty()) {
			createContainer.withEnv(container.getEnvironment().split(";"));
		}

		// if (container.getEnvironment() != null &&
		// !container.getEnvironment().getValues().isEmpty()) {
		// createContainer.withEnv(container.getEnvironment().getValues());
		// }
		
		/*
		 * Verifies that the container is not in a host network.
		 * In this case the port mapping wouldn't make sense because the ports of the host machine are directly used.
		 */
		if(!isInHostNetwork(container)) {
			// Define exposed port and port binding access.
	
			// ArrayOfString ports = container.getPorts();
			List<String> ports = new ArrayList<>();
			// Get the original tab.
			String portsValues = container.getPorts();
			if (portsValues != null && !portsValues.trim().isEmpty()) {
				// example: 8080:80;4043:443 etc. ; is the separator for tab and : port
				// separator.
				String[] portsTab = container.getPorts().split(";");
				// Build the ports list.
				for (String port : portsTab) {
					ports.add(port); // 8080:80..
				}
			}
	
			if (ports != null && !ports.isEmpty()) {
				System.out.println("Container ports : ");
				List<ExposedPort> exposedPorts = new LinkedList<>();
				List<PortBinding> portBindings = new LinkedList<>();
	
				for (String port : ports) {
					System.out.println("port: " + port);
					String[] lrports = port.split(":"); // ex: 2000:80
					if (lrports[0].contains("/tcp")) {
						lrports[0] = lrports[0].replace("/tcp", "");
					}
					ExposedPort tcp = ExposedPort.tcp(Integer.parseInt(lrports[0]));
					PortBinding portBinding = null;
					// Exposed port is set with lrPorts[0]
					// Binding port is set with lrPorts[1]
					if (lrports.length == 2) {
						if (StringUtils.isNotBlank(lrports[1])) {
							portBinding = new PortBinding(Binding.bindPort(Integer.parseInt(lrports[1])), tcp);
						} else {
							portBinding = new PortBinding(Binding.bindPort(32768), tcp); // TODO Create dynamic port number
						}
						portBindings.add(portBinding);
					}
					exposedPorts.add(tcp);
				}
				if (!exposedPorts.isEmpty()) {
					createContainer.withExposedPorts(exposedPorts);
				}
				if (!portBindings.isEmpty()) {
					createContainer.withPortBindings(portBindings);
				}
			} else {
				LOGGER.warn("No exposed nor binding ports defined for the container : " + container.getName());
			}
		}
		
		String command = container.getCommand(); // internal command to execute on creation.
		if(checkSshPortKonfiguration(container)) {
			LOGGER.warn("The specified command of the container will be overriden with because the special ssh connection settings of the container are met!");
			String sshPort = getSshPort(container);
			String key = getKey(container);
			
			//See image description of lennse/ubuntuworkflow on Docker Hub
			
			//createContainer.withCmd("/bin/bash", "-c","./start.sh " + sshPort + " '" + key +"'; sleep infinity");
			//LOGGER.info("Command that is executed on the Container: " + Arrays.asList(createContainer.getCmd()));
		} else {
			if (command != null && !command.trim().isEmpty()) {
				// String[] commands = (StringUtils.deleteWhitespace(command)).split(",");
	
				String[] commands = getCmdArray(command);
				createContainer.withCmd(commands);
				// createContainer.withCmd("/bin/sh", "-c", "httpd -p 8000 -h /www; tail -f
				// /dev/null");
			} 
			else if (!StringUtils.isNotBlank(container.getImage())) { // else overrides image command if any (often)
				createContainer.withCmd("sleep", "9999");
			}
		}
		
		if (StringUtils.isNotBlank(container.getName())) {
			createContainer.withName(StringUtils.deleteWhitespace(container.getName()));
		}
		if (StringUtils.isNotBlank(container.getNet())) {
			createContainer.withNetworkMode(StringUtils.deleteWhitespace(container.getNet()));
		}
		if (container.getPublishAll()) {
			createContainer.withPublishAllPorts(container.getPublishAll());
		}
		if (container.getStdinOpen()) {
			createContainer.withStdinOpen(container.getStdinOpen());
		}
		if (StringUtils.isNotBlank(container.getUser())) {
			createContainer.withUser(container.getUser());
		}

		String volumesStr = container.getVolumes();
		if (volumesStr != null && !volumesStr.trim().isEmpty()) {
			String[] volumes = volumesStr.split(";");
			List<Volume> vs = new LinkedList<Volume>();
			for (String volumeName : volumes) {
				Volume newVolume = new Volume(volumeName);
				vs.add(newVolume);
			}
			//Commented out. sometimes volumes are declared in dockerfile
			//createContainer.withVolumes(vs);
		}

		// With ArraysOfString datatype.
		// if (container.getVolumes() != null &&
		// !container.getVolumes().getValues().isEmpty()) {
		// List<Volume> vs = new LinkedList<Volume>();
		// for (String volume : container.getVolumes().getValues()) {
		// Volume newVolume = new Volume(volume);
		// vs.add(newVolume);
		// }
		// createContainer.withVolumes(vs);
		// }

		if (container.getMemLimit() != null && container.getMemLimit() > 0) {
			// TODO : Replace integer by Long in specification model occie.
			createContainer.withMemory(Long.valueOf(container.getMemLimit()));
		}

		if (container.getMemorySwap() != null && container.getMemorySwap() > 0) {
			// TODO : Replace integer by Long in specification model occie.
			createContainer.withMemory(Long.valueOf(container.getMemorySwap()));
		}

		String lxcConfStr = container.getLxcConf();
		if (lxcConfStr != null && !lxcConfStr.trim().isEmpty()) {
			List<LxcConf> lxcConfigs = new LinkedList<>();
			// Example : lxc.aa_profile:unconfined etc.
			String[] lxcConfs = lxcConfStr.split(";");
			for (String lxcConf : lxcConfs) {
				String[] lxcKeyVal = lxcConf.split(":");
				if (lxcConf.length() == 2) {
					LxcConf lxcCon = new LxcConf(lxcKeyVal[0], lxcKeyVal[1]);
					lxcConfigs.add(lxcCon);
				} else {
					throw new DockerException(
							"Lxc conf format must be like this one --> lxc.aa_profile:unconfined --> key:value");
				}
			}
			if (!lxcConfigs.isEmpty()) {
				createContainer.withLxcConf(lxcConfigs);
			}
		}

		// With ArraysOfString datatype.
		// if (container.getLxcConf() != null &&
		// !container.getLxcConf().getValues().isEmpty()) {
		// List<LxcConf> lxcConfigs = new LinkedList<>();
		// // Example : lxc.aa_profile:unconfined etc.
		// for (String lxcConf : container.getLxcConf().getValues()) {
		// String[] lxcKeyVal = lxcConf.split(":");
		// if (lxcConf.length() == 2) {
		// LxcConf lxcCon = new LxcConf(lxcKeyVal[0], lxcKeyVal[1]);
		// lxcConfigs.add(lxcCon);
		// } else {
		// throw new DockerException(
		// "Lxc conf format must be like this one --> lxc.aa_profile:unconfined -->
		// key:value");
		// }
		// }
		// if (!lxcConfigs.isEmpty()) {
		// createContainer.withLxcConf(lxcConfigs);
		// }
		// }

		if (StringUtils.isNotBlank(container.getDomainName())) {
			createContainer.withDomainName(container.getDomainName());
		}

		if (container.getDnsSearch() != null && !container.getDnsSearch().trim().isEmpty()) {
			createContainer.withDnsSearch(container.getDnsSearch().split(";"));
		}

		// with ArraysOfString datatype.
		// if (container.getDnsSearch() != null &&
		// !container.getDnsSearch().getValues().isEmpty()) {
		// createContainer.withDnsSearch(container.getDnsSearch().getValues());
		// }

		if (StringUtils.isNotBlank(container.getEntrypoint())) {
			// TODO : Convert to ArrayOfString in model occie.
			String[] entrypoint = container.getEntrypoint().split(",");
			createContainer.withEntrypoint(entrypoint);
		}

		if (StringUtils.isNotBlank(container.getPid())) {
			createContainer.withPidMode(StringUtils.deleteWhitespace(container.getPid()));
		}

		if (container.getReadOnly()) {
			createContainer.withReadonlyRootfs(container.getReadOnly());
		}

		if (container.getTty()) {
			createContainer.withTty(container.getTty());
		}

		if (StringUtils.isNotBlank(container.getRestart())) {
			createContainer
					.withRestartPolicy(RestartPolicy.parse(StringUtils.deleteWhitespace(container.getRestart())));
		}

		if (StringUtils.isNotBlank(container.getWorkingDir())) {
			createContainer.withWorkingDir(StringUtils.deleteWhitespace(container.getWorkingDir()));
			// createContainer.getCpusetCpus();
		}

		List<Container> containersWithVolumes = new LinkedList<>();

		List<org.eclipse.cmf.occi.docker.Volume> volumesInsideHost = new LinkedList<>();

		for (Resource r : containersWithVolumes(container)) {
			if (r instanceof Container) {
				containersWithVolumes.add((Container) r);
			}
			if (r instanceof org.eclipse.cmf.occi.docker.Volume) {
				volumesInsideHost.add((org.eclipse.cmf.occi.docker.Volume) r);
			}
		}

		if (!containersWithVolumes.isEmpty()) {
			List<VolumesFrom> volumesFrom = new LinkedList<>();
			for (Container c : containersWithVolumes) {
				volumesFrom.add(new VolumesFrom(c.getName()));
				System.out.println(c.getName());
			}
			createContainer.withVolumesFrom(volumesFrom);
		}

		if (!volumesInsideHost.isEmpty()) {
			List<Bind> volumesBind = new LinkedList<>();
			List<Volume> vs = new ArrayList<>();
			for (org.eclipse.cmf.occi.docker.Volume v : volumesInsideHost) {
				Volume newVolume = null;
				if (!StringUtils.isBlank(v.getDestination())) {
					newVolume = new Volume(v.getDestination());
					vs.add(newVolume);
				}

				if (!StringUtils.isBlank(v.getSource())) {
					Bind newBind = new Bind(v.getSource(), newVolume);
					volumesBind.add(newBind);
				}
			}
			createContainer.withVolumes(vs);
			createContainer.withBinds(volumesBind);
		} else {
			if(container.getVolumes() != null) {
				System.out.println("Volumes defined in attribute");
				System.out.println("Adding: -v " + container.getVolumes());
				createContainer.withBinds(Bind.parse(container.getVolumes()));
			}
		}

		// Define container network links if any.
		if (containerDependency != null && containerDependency.containsKey(container.getName())) {
			List<String> depdupeContainers = new ArrayList<String>(
					new LinkedHashSet<String>(containerDependency.get(container.getName())));

			List<Link> dockeClientlinks = new ArrayList<>();
			Link dockeClientlink = null;
			for (String entry : depdupeContainers) {
				dockeClientlink = new Link(entry, container.getName() + "LinkTo" + entry);
				dockeClientlinks.add(dockeClientlink);
			}
			if (depdupeContainers.size() > 1) {
				createContainer.withLinks(dockeClientlinks);
			} else if (depdupeContainers.size() == 1) {
				createContainer.withLinks(dockeClientlink);
			}
		}

		return createContainer;
	}

	public static String getKey(Container container) {
		for(MixinBase mixB: container.getParts()) {
			if(mixB instanceof Ssh_key) {
				Ssh_key key = (Ssh_key) mixB;
				if(key.getOcciCredentialsSshPublickey() != null &&
						key.getOcciCredentialsSshPublickey().equals("") == false) {
					return key.getOcciCredentialsSshPublickey();
				} else {
					for(AttributeState as: mixB.getAttributes()) {
						if(as.getName().equals("occi.credentials.ssh.publickey")) {
							return as.getValue();
						}
					}
				}
			}
		}
		return "";
	}

	public String[] getCmdArray(String command) {
		String[] cmdArray;
		if (command != null && !command.isEmpty()) {
			cmdArray = command.split(",");
			// Scan for space before and space end...
			for (int i = 0; i < cmdArray.length; i++) {
				cmdArray[i] = cmdArray[i].trim();
			}
			return cmdArray;
		}

		return new String[0];
	}

	/**
	 * List target volumes resources from a container.
	 * 
	 * @param c
	 *            a model container
	 * @return
	 */
	public List<Resource> containersWithVolumes(Container c) {
		List<Resource> containersFrom = new ArrayList<>();
		for (org.eclipse.cmf.occi.core.Link l : c.getLinks()) {
			if (l instanceof Volumesfrom) {
				containersFrom.add(l.getTarget());
			}
		}
		return containersFrom;
	}

	/**
	 * Control if docker client is set on the good machine.
	 * 
	 * @param computeMachine
	 * @return
	 * @throws DockerException
	 */
	public void preCheckDockerClient(Compute computeMachine) throws DockerException {
		if (this.dockerClient == null) {
			// Build a new Docker client for this machine.
			this.dockerClient = DockerConfigurationHelper.buildDockerClient(computeMachine);
			this.compute = computeMachine;
		}

		if (this.dockerClient == null) {
			// Must never be thrown here.
			throw new DockerException("No docker client found !");
		}
		if (compute != null && compute instanceof Machine && computeMachine instanceof Machine
				&& !(((Machine) compute).getName().equalsIgnoreCase(((Machine) computeMachine).getName()))) {

			this.dockerClient = DockerConfigurationHelper.buildDockerClient(computeMachine);
			this.compute = computeMachine;
		}
	}

	/**
	 * Inspect/describe a container.
	 * 
	 * @param computeMachine
	 * @param containerId
	 * @return
	 * @throws DockerException
	 */
	public InspectContainerResponse inspectContainer(Compute computeMachine, final String containerId)
			throws DockerException {
		preCheckDockerClient(computeMachine);
		if (containerId == null) {
			throw new DockerException("Container id is not set ! (Id:" + containerId + ")");
		}
		

		InspectContainerResponse containerResponse = dockerClient.inspectContainerCmd(containerId).exec();

		return containerResponse;
	}

	/**
	 * Used when container id is unknown or must be refreshed via occiRetrieve().
	 * 
	 * @param computeMachine
	 * @param container
	 * @return
	 * @throws DockerException
	 */
	public InspectContainerResponse inspectContainer(Compute computeMachine, final Container container)
			throws DockerException {
		preCheckDockerClient(computeMachine);
		if (container == null || container.getName() == null) {
			throw new DockerException("Container model or container name is not set !");
		}
		// Search the containerId for this container.
		List<com.github.dockerjava.api.model.Container> containers = listContainer(computeMachine);
		String inspectName;
		String containerId = null;
		for (com.github.dockerjava.api.model.Container con : containers) {
			if (con.getNames() != null && con.getNames().length > 0 && con.getNames()[0] != null) {
				inspectName = con.getNames()[0];
				inspectName = inspectName.replaceAll("/", "");
				if (inspectName.equalsIgnoreCase(container.getName())) {
					containerId = con.getId();
					break;
				}
			}
		}
		if (containerId == null) {
			LOGGER.warn("No id defined for this container, cannot retrieve its informations.");
		} else {
			InspectContainerResponse containerResponse = this.inspectContainer(computeMachine, containerId);
			return containerResponse;
		}

		return null;
	}

	/**
	 * 
	 * @param computeMachine
	 * @param network
	 * @return networkId (String)
	 * @throws DockerException
	 */
	public String createNetwork(Compute computeMachine, Network network) throws DockerException {

		// Set dockerClient
		preCheckDockerClient(computeMachine);

		List<Config> ipamConfigs = new ArrayList<>();
		com.github.dockerjava.api.model.Network.Ipam ipam = null;

		if (StringUtils.isNotBlank(network.getSubnet())) {
			ipamConfigs.add(new com.github.dockerjava.api.model.Network.Ipam.Config().withSubnet(network.getSubnet()));
		} else {
			// TODO : Add default value : 10.67.79.0/24 on specification (model occie) +
			// documentation of what a subnet means.
			ipamConfigs.add(new com.github.dockerjava.api.model.Network.Ipam.Config().withSubnet("10.67.79.0/24"));
		}

		if (StringUtils.isNotBlank(network.getGateway())) {
			ipamConfigs
					.add(new com.github.dockerjava.api.model.Network.Ipam.Config().withGateway(network.getGateway()));
		}
		if (StringUtils.isNotBlank(network.getIpRange())) {
			ipamConfigs
					.add(new com.github.dockerjava.api.model.Network.Ipam.Config().withIpRange(network.getIpRange()));
		}

		try {
			// TODO : Check this getIpam() reference address.
			ipam = new com.github.dockerjava.api.model.Network().getIpam().withConfig(ipamConfigs);

		} catch (Exception e) {
			LOGGER.error("Exception:" + e.getMessage());
			throw new DockerException(e.getMessage(), e);
		}

		// Initiate the NetworkCommand
		CreateNetworkCmd createNetworkCmd = dockerClient.createNetworkCmd().withIpam(ipam);
		if (StringUtils.isNotBlank(network.getName())) {
			createNetworkCmd = createNetworkCmd.withName(network.getName());
		}
		if (StringUtils.isNotBlank(network.getDriver())) {
			createNetworkCmd = createNetworkCmd.withDriver(network.getDriver());
		}

		CreateNetworkResponse createNetworkResponse = null;
		com.github.dockerjava.api.model.Network updateNetwork = null;
		try {
			// Create an overlay network
			createNetworkResponse = createNetworkCmd.withCheckDuplicate(true).exec();
		} catch (InternalServerErrorException exception) {
			LOGGER.error(exception.getMessage());
			createNetworkResponse = null;
			updateNetwork = dockerClient.inspectNetworkCmd().withNetworkId(network.getName()).exec();
			updateNetwork.getId();
		}
		if (createNetworkResponse != null) {
			return createNetworkResponse.getId();
		} else {
			return updateNetwork.getId();
		}
	}

	/**
	 * 
	 * @param computeMachine
	 * @param networks
	 * @throws DockerException
	 */
	public void connectToNetwork(Compute computeMachine, Map<Container, Set<Networklink>> networks)
			throws DockerException {
		// Set dockerClient
		preCheckDockerClient(computeMachine);

		if (networks.size() > 0) {
			for (Map.Entry<Container, Set<Networklink>> entry : networks.entrySet()) {
				for (Networklink netLink : entry.getValue()) {
					try {
						dockerClient.connectToNetworkCmd().withNetworkId(((Network) netLink.getTarget()).getNetworkId())
								.withContainerId(entry.getKey().getContainerid()).exec();
					} catch (InternalServerErrorException exception) {
						LOGGER.error("InternalServerErrorException: " + exception.getMessage());
						throw new DockerException(exception);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param computeMachine
	 * @param containerId
	 */
	public void removeContainer(Compute computeMachine, String containerId) throws DockerException {
		preCheckDockerClient(computeMachine);
		// TODO : Check response !!!
		dockerClient.removeContainerCmd(containerId).exec();
	}
	
	public void removeLocalContainer(String containerId) {
		dockerClient.removeContainerCmd(containerId).exec();
	}

	public void killContainer(Compute computeMachine, String containerId) throws DockerException {
		preCheckDockerClient(computeMachine);
		dockerClient.killContainerCmd(containerId);
	}

	/**
	 * Start a container.
	 * 
	 * @param computeMachine
	 * @param container
	 * @throws DockerException
	 */
	public void startContainer(Compute computeMachine, Container container, StatsCallBack statsCallBack) throws DockerException {
		preCheckDockerClient(computeMachine);
		try {
			dockerClient.startContainerCmd(container.getContainerid()).exec();

			if (container.getMonitored()) { // Allow the monitoring of a container.
				// Collect monitoring data
				System.out.println("Starting metrics collection");

				// Load new docker client to fix blocking thread problem
				this.dockerClient = DockerConfigurationHelper.buildDockerClient(computeMachine);
				if (statsCallBack != null) {
					System.out.println("Launch docker stats command for container : " + container.getName());
					dockerClient.statsCmd(container.getContainerid()).exec(statsCallBack);
				}
			}
		
			String cid = container.getName();
	
			if(this.isOnLocalHost()) {
				String sshPort = getSshPort(container);
				String key = getKey(container);
				
				System.out.println("Post startup execution on localhost!");
				localExec(cid, "touch /tmp/test");
				localExec(cid, "echo Port " + sshPort + " >> /etc/ssh/sshd_config");
				localExec(cid, "echo " + key + " >> ~/.ssh/authorized_keys");
				localExec(cid, "echo " + key + " >> /home/ubuntu/.ssh/authorized_keys");
				localExec(cid, "systemctl restart ssh");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DockerException(ex.getMessage(), ex);
		}
	}

	public void localExec(String cid, String args) {
		
		try {
			String result = ProcessManager.getOutputCommand("docker exec -d -u 0 " + cid + " sh -c '" + args + "'", Runtime.getRuntime());
		} catch (DockerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * Stop a container.
	 * 
	 * @param computeMachine
	 * @param container
	 * @throws DockerException
	 */
	public void stopContainer(Compute computeMachine, Container container) throws DockerException {
		preCheckDockerClient(computeMachine);
		if (container.getMonitored()) {
			System.out.println("Stopping monitoring container : " + container.getName());
			// Stop the statscallbacks and recreate a new one.
			try {
				((ContainerConnector)container).getStatsCallBack().close();
				((ContainerConnector)container).setStatsCallBack(new StatsCallBack(container));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Effectively stop the container : " + container.getName());
		dockerClient.stopContainerCmd(container.getContainerid()).exec();
	}
	
	public void stopLocalContainer(Container container) {
//		if (container.getMonitored()) {
//			System.out.println("Stopping monitoring container : " + container.getName());
//			// Stop the statscallbacks and recreate a new one.
//			try {
//				((ContainerConnector)container).getStatsCallBack().close();
//				((ContainerConnector)container).setStatsCallBack(new StatsCallBack(container));
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
		System.out.println("Effectively stop the container : " + container.getName() + " on local docker host");
		try {
		dockerClient.stopContainerCmd(container.getContainerid()).exec();
		} catch (NotModifiedException e) {
			LOGGER.warn("Exception thrown while stopping container. Probably the container isn't running anymore.", e);
		}
	}

	/**
	 * Suspend a container (wait container).
	 * 
	 * @param computeMachine
	 * @param container
	 * @throws DockerException
	 */
	public void suspendContainer(Compute computeMachine, Container container) throws DockerException {
		preCheckDockerClient(computeMachine);
		dockerClient.waitContainerCmd(container.getContainerid()).exec(new WaitContainerResultCallback())
				.awaitStatusCode();
	}

	/**
	 * rename a container.
	 * 
	 * @param computeMachine
	 * @param container
	 * @throws DockerException
	 */
	public void renameContainer(Compute computeMachine, Container container, String name) throws DockerException {
		preCheckDockerClient(computeMachine);
		dockerClient.renameContainerCmd(container.getContainerid()).withName(name).exec();
	}

	/**
	 * Delete a container from a compute machine.
	 * 
	 * @param computeMachine
	 * @param container
	 * @throws DockerException
	 */
	public void removeContainer(Compute computeMachine, Container container) throws DockerException {
		String machineName;
		preCheckDockerClient(computeMachine);

		if (computeMachine instanceof Machine) {
			machineName = ((Machine) computeMachine).getName();
		} else {
			// TODO : other computes from different extensions (based on infrastructure and
			// extended like this docker extension).
			// For now we use title from the entity.
			machineName = computeMachine.getTitle();
		}

		if (machineName == null || machineName.trim().isEmpty()) {
			throw new DockerException("Cannot remove a container without compute machine name");
		}

		this.dockerClient.removeContainerCmd(container.getContainerid()).exec();
	}

	/**
	 * List containers from a machine with machine name, empty list if none is
	 * found.
	 * 
	 * @param machineName
	 * @return
	 * @throws DockerException
	 */
	public List<com.github.dockerjava.api.model.Container> listContainer(Compute computeMachine)
			throws DockerException {
		preCheckDockerClient(computeMachine);
		List<com.github.dockerjava.api.model.Container> containers = null;
		try {
			containers = dockerClient.listContainersCmd().withShowAll(true).exec();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DockerException(ex.getMessage(), ex);
		}
		if (containers == null) {
			containers = new LinkedList<>();
		}
		return containers;
	}

	/**
	 * Pull an image on a compute machine.
	 * 
	 * @param computeMachine
	 * @param image
	 * @return
	 * @throws DockerException
	 */
	public DockerClient pullImage(Compute computeMachine, String image) throws DockerException {
		preCheckDockerClient(computeMachine);

		String containerImage = image;
		if (!StringUtils.isNotBlank(containerImage)) {
			containerImage = DEFAULT_IMAGE;
			System.out.println("Use the default Docker Image: " + containerImage);
		}
		System.out.println("Downloading image: ->" + containerImage);
		// Download a pre-built image
		try {
			// If the given image tag doesn't contain a version number, add "latest" as tag
			if (containerImage.indexOf(':') < 0) {
				dockerClient.pullImageCmd(containerImage).withTag("latest").exec(new PullImageResultCallback()).awaitCompletion();
			} else {
				dockerClient.pullImageCmd(containerImage).exec(new PullImageResultCallback()).awaitCompletion();
			}
		} catch (Exception e) {
			throw new DockerException(e);
		}
		System.out.println("Download is finished");
		return this.dockerClient;
	}
	
	public void runImage(Compute compute, String image) throws DockerException {
		preCheckDockerClient(compute);
		
		LOGGER.info("Running Container");
		if (!StringUtils.isNotBlank(image)) {
			image = DEFAULT_IMAGE;
			System.out.println("Use the default Docker Image: " + image);
		}
		
		dockerClient.createContainerCmd(image).exec();
	}

	/**
	 * 
	 * @param machine
	 * @param image
	 * @return
	 */
	public boolean machineContainsImage(final String machine, final String image) {
		if (images.get(machine) != null) {
			return images.get(machine).contains(image);
		}
		return false;
	}

	/**
	 * 
	 * @param machine
	 * @param image
	 */
	public void addImageToMachine(final String machine, final String image) {
		List<String> tempList;
		if (!images.containsKey(machine)) {
			tempList = new ArrayList<>();
			tempList.add(image);
			images.put(machine, tempList);
		} else {
			tempList = images.get(machine);
			tempList.add(image);
			images.put(machine, tempList);
		}
	}

	public void connect(String host, String privateKey, String command) throws DockerException {
		Session session = null;
		String user = "docker";
		String tempDir = createTempDir("knowHosts").getAbsolutePath();
		File test = new File(tempDir + "/hosts");

		// Do not remove an existing file
		if (!test.exists()) {
			try {
				test.createNewFile();
			} catch (IOException ex) {
				throw new DockerException(ex);
			}
		}

		try {
			JSch jsc = new JSch();

			jsc.setKnownHosts("/dev/null");
			Properties config = new Properties();
			// TODO : Support host key checking... with ssh connection.
			config.put("StrictHostKeyChecking", "no");
			jsc.addIdentity(privateKey);
			System.out.println("Identity added ..");

			String exCommand = "sudo sh -c " + "\"" + command + "\"";
			System.out.println(exCommand);

			// TODO : Support ssh connection on other ports than 22.
			session = jsc.getSession(user, host, 22);
			System.out.println("Session created ..");
			session.setConfig(config);
			System.out.println("Session config ..");

			session.connect();
			System.out.println("Session connected ..");

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(exCommand);
			((ChannelExec) channel).setErrStream(System.err);
			channel.connect();
		} catch (JSchException e) {
			System.out.println(e.getMessage());

		}
		session.disconnect();
	}

	/**
	 * 
	 * @param key
	 * @param ip
	 * @param knowHosts
	 * @throws DockerException
	 */
	public void addHost(String key, String ip, String knowHosts) throws DockerException {
		try {
			FileWriter tmpwriter = new FileWriter(knowHosts, true);
			String newLine = ip + " ssh-rsa " + key + "\n";
			if (!hostAlreadyExist(newLine, knowHosts)) {
				tmpwriter.append(newLine);
				System.out.println(ip + " ssh-rsa " + key);

				tmpwriter.flush();
				tmpwriter.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param newLine
	 * @param knowHosts
	 * @return
	 * @throws DockerException
	 */
	public boolean hostAlreadyExist(String newLine, String knowHosts) throws DockerException {
		try {
			File hostFile = new File(knowHosts);
			BufferedReader br = new BufferedReader(new FileReader(hostFile));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.trim().equalsIgnoreCase(newLine.trim())) {
					return true;
				}
			}
			br.close();
			return false;
		} catch (IOException ex) {
			throw new DockerException(ex);
		}
	}

	/**
	 * 
	 * @param baseName
	 * @return
	 */
	public File createTempDir(String baseName) throws DockerException {
		File baseDir = new File(System.getProperty("java.io.tmpdir"));
		File tempDir = new File(baseDir, baseName);
		if (!tempDir.exists()) {
			if (tempDir.mkdir()) {
				return tempDir;
			}
		} else {
			return tempDir;
		}
		throw new DockerException("Cannot locate a temp directory.");
	}

	/**
	 * Check if a container name already exist on a compute.
	 * 
	 * @param containerName
	 * @param compute
	 * @return true if container name exist, false if not.
	 */
	public boolean containerNameExists(final String containerName, final Compute compute) throws DockerException {
		List<com.github.dockerjava.api.model.Container> containers = listContainer(compute);
		String nameTmp = "";
		List<String> names;
		String linkName = "LinkTo";
		for (com.github.dockerjava.api.model.Container container : containers) {
			names = Arrays.asList(container.getNames());
			if (names != null) {
				for (String name : names) {
					int index = name.indexOf(linkName);
					if (index == -1) {
						nameTmp = name.replaceAll("/", "");
					} else {
						nameTmp = name.substring(index + linkName.length());
					}
					if (nameTmp.equals(containerName)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param computeMachine
	 * @param container
	 * @throws DockerException
	 */
	public void retrieveAndUpdateContainerModel(final Compute computeMachine, Container container)
			throws DockerException {
		InspectContainerResponse resp = this.inspectContainer(computeMachine, container);
		if (resp != null) {
			ModelHandler modelHandler = new ModelHandler();
			modelHandler.updateContainerModel(container, resp);
		}
	}

	public ComputeStatus getCurrentContainerStatus(final Compute computeMachine, final Container container)
			throws DockerException {
		InspectContainerResponse resp = this.inspectContainer(computeMachine, container);
		ComputeStatus computeStatus = ComputeStatus.INACTIVE; // Default status.
		if (resp != null) {
			if (resp.getState().getRunning()) {
				computeStatus = ComputeStatus.ACTIVE;
			}
			if (resp.getState().getPaused()) {
				computeStatus = ComputeStatus.SUSPENDED;
			}

		}
		return computeStatus;
	}
	
	public com.github.dockerjava.api.model.Container getJavaApiContainerObject(String name) {
		ArrayList<String> al = new ArrayList<String>();
		al.add(name);
		List<com.github.dockerjava.api.model.Container> containers = dockerClient.listContainersCmd()
				.withShowAll(true)
				.withNameFilter(al).exec();
		if(containers.size() < 1)
			return null;
		return containers.get(0);
		
	}
	public static Machine getDockerHost(Container container) {
		for(org.eclipse.cmf.occi.core.Link link: container.getRlinks()) {
			if(link instanceof Contains) {
				if(link.getSource() instanceof Machine) {
					return (Machine) link.getSource();
				}
			}
		}
		return null;
	}
	
	public static String getDockerHostIpAddress(Container container) throws DockerException {
		Machine dockerHost = getDockerHost(container);
		if(dockerHost != null) {
			String ipAddress = DockerMachineHelper.getMachineIPAddress(dockerHost);
			if(ipAddress == null) {
				LOGGER.warn("Can't retrieve the ip address of the containers docker host! Maybe the container is placed on local docker host. Continuing with 127.0.0.1.");
				return "127.0.0.1";
			} else {
				return ipAddress;
			}
		}
		LOGGER.error("Container is not connected to a docker host (machine node)");
		return null;
	}
	
	/**
	 * Reads the ip address of the container in the first network its connected to.
	 * 
	 * If container is connected to the host network it returns the ip address of its docker host.
	 * @param networkMap
	 * @param container
	 * @return
	 * @throws DockerException
	 */
	public static String readIPNetworksMap(Map<String, ContainerNetwork> networkMap, Container container) throws DockerException {
		if(networkMap.size() == 1) {
			for(Map.Entry<String, ContainerNetwork> entry : networkMap.entrySet()) {
				if(entry.getKey().equals("host"))
					return getDockerHostIpAddress(container);
				return entry.getValue().getIpAddress();
			}
		} else if(networkMap.size() < 1) {
			LOGGER.error("The container is not connected to a network!");
		} else {
			LOGGER.warn("The container is connected to multiple networks but the current implementation can only maintain one ip address.");
			for(Map.Entry<String, ContainerNetwork> entry : networkMap.entrySet()) {
				LOGGER.warn("'The ip for the connected " + entry.getKey() + " network (" + entry.getValue().getIpAddress() +") will be used as the ip address for the container!");
				return entry.getValue().getIpAddress();
			}
		}
		LOGGER.error("The ip address couldn't be retrieved due to unknown reasons!");
		return null;
	}

	public String getContainerIP(Container container) throws DockerException {
		com.github.dockerjava.api.model.Container javaAPIContainer = getJavaApiContainerObject(container.getName());
		if(javaAPIContainer == null)
			return null;
		return readIPNetworksMap(javaAPIContainer.getNetworkSettings().getNetworks(), container);
	}
}
