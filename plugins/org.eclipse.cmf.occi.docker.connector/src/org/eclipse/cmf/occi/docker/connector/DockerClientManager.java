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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.cmf.occi.core.Resource;
import org.eclipse.cmf.occi.docker.ArrayOfString;
import org.eclipse.cmf.occi.docker.Container;
import org.eclipse.cmf.occi.docker.Machine;
import org.eclipse.cmf.occi.docker.Network;
import org.eclipse.cmf.occi.docker.Networklink;
import org.eclipse.cmf.occi.docker.Volumesfrom;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.docker.connector.helpers.DockerConfigurationHelper;
import org.eclipse.cmf.occi.docker.connector.helpers.DockerMachineHelper;
import org.eclipse.cmf.occi.docker.connector.observer.StatsCallBack;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.CreateNetworkCmd;
import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.exception.InternalServerErrorException;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Link;
import com.github.dockerjava.api.model.LxcConf;
import com.github.dockerjava.api.model.Network.Ipam.Config;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports.Binding;
import com.github.dockerjava.api.model.RestartPolicy;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.api.model.VolumesFrom;
import com.github.dockerjava.core.command.PullImageResultCallback;
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

	private DockerClient dockerClient = null;

	private Compute compute = null;

	private Map<String, List<String>> images = new HashMap<>();

	private static Logger LOGGER = LoggerFactory.getLogger(DockerClientManager.class);

	// private PreferenceValues properties = new PreferenceValues();

	public static final String DEFAULT_IMAGE_NAME = "busybox";

	public DockerClientManager(Compute compute) throws DockerException {
		this.compute = compute;
		// Build a docker client related to this compute, if compute is null,
		// dockerclient will be relative to this local machine.
		this.dockerClient = DockerConfigurationHelper.buildDockerClient(compute);
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
	 * @return A response docker-java object CreateContainerResponse.
	 * @throws DockerException
	 */
	public CreateContainerResponse createContainer(Compute computeMachine, Container container) throws DockerException {

		preCheckDockerClient(computeMachine);

		CreateContainerCmd createContainer = containerBuilder(container, null);

		CreateContainerResponse createContainerResponse = createContainer.exec();
		container.setContainerid(createContainerResponse.getId());
		LOGGER.info("Created container: {}", container.getContainerid());

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
	public CreateContainerResponse createContainer(Compute computeMachine, Container container,
			Multimap<String, String> containerDependency) throws DockerException {
		preCheckDockerClient(computeMachine);
		CreateContainerCmd createContainer = containerBuilder(container, containerDependency);

		CreateContainerResponse createContainerResponse = createContainer.exec();
		container.setContainerid(createContainerResponse.getId());
		LOGGER.info("Created container: {}", container.getContainerid());

		return createContainerResponse;
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

		if (container.getImage() == null || container.getImage().trim().isEmpty()) {
			createContainer = this.dockerClient.createContainerCmd(DEFAULT_IMAGE_NAME);
		} else {
			createContainer = this.dockerClient.createContainerCmd(container.getImage().trim());
		}

		String command = container.getCommand(); // internal command to execute on creation.
		if (command != null && !command.trim().isEmpty()) {
			String[] commands = (StringUtils.deleteWhitespace(command)).split(",");
			createContainer.withCmd(commands);
		} else if (!StringUtils.isNotBlank(container.getImage())) { // else overrides image command if any (often)
			createContainer.withCmd("sleep", "9999");
		}

		if (container.getCpuShares() > 0) {
			createContainer.withCpuShares(container.getCpuShares());
		}

		// Add hostname container.
		if (container.getOcciComputeHostname() != null && !container.getOcciComputeHostname().trim().isEmpty()) {
			createContainer.withHostName(StringUtils.deleteWhitespace(container.getOcciComputeHostname()));
		}

		// Add hostnames to /etc/hosts in the container.
		ArrayOfString addHosts = container.getAddHost();

		if (addHosts != null && !addHosts.getValues().isEmpty()) {
			createContainer.withExtraHosts(addHosts.getValues());
		}

		if (StringUtils.isNotBlank(container.getCpuSetCpus())) {
			createContainer.withCpusetCpus(container.getCpuSetCpus());
		}

		if (StringUtils.isNotBlank(container.getCpuSetMems())) {
			createContainer.withCpusetCpus(container.getCpuSetMems());
		}
		if (container.isPrivileged()) {
			createContainer.withPrivileged(container.isPrivileged());
		}

		if (container.getDns() != null && !container.getDns().getValues().isEmpty()) {
			createContainer.withDns(container.getDns().getValues());
		}

		if (container.getEnvironment() != null && !container.getEnvironment().getValues().isEmpty()) {
			createContainer.withEnv(container.getEnvironment().getValues());
		}
		// Define exposed port and port binding access.
		ArrayOfString ports = container.getPorts();
		if (ports != null && ports.getValues().isEmpty()) {
			LOGGER.info("Container ports : ");
			List<ExposedPort> exposedPorts = new LinkedList<>();
			List<PortBinding> portBindings = new LinkedList<>();

			for (String port : ports.getValues()) {
				LOGGER.info("port: " + port);
				String[] lrports = port.split(":"); // ex: 2000:80
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
		if (StringUtils.isNotBlank(createContainer.getName())) {
			createContainer.withName(StringUtils.deleteWhitespace(container.getName()));
		}
		if (StringUtils.isNotBlank(container.getNet())) {
			createContainer.withNetworkMode(StringUtils.deleteWhitespace(container.getNet()));
		}
		if (container.isPublishAll()) {
			createContainer.withPublishAllPorts(container.isPublishAll());
		}
		if (container.isStdinOpen()) {
			createContainer.withStdinOpen(container.isStdinOpen());
		}
		if (StringUtils.isNotBlank(container.getUser())) {
			createContainer.withUser(container.getUser());
		}
		if (container.getVolumes() != null && !container.getVolumes().getValues().isEmpty()) {
			List<Volume> vs = new LinkedList<Volume>();
			for (String volume : container.getVolumes().getValues()) {
				Volume newVolume = new Volume(volume);
				vs.add(newVolume);
			}
			createContainer.withVolumes(vs);
		}

		if (container.getMemLimit() > 0) {
			// TODO : Replace integer by Long in specification model occie.
			createContainer.withMemory(Long.valueOf(container.getMemLimit()));
		}

		if (container.getMemorySwap() > 0) {
			// TODO : Replace integer by Long in specification model occie.
			createContainer.withMemory(Long.valueOf(container.getMemorySwap()));
		}

		if (container.getLxcConf() != null && !container.getLxcConf().getValues().isEmpty()) {
			List<LxcConf> lxcConfigs = new LinkedList<>();
			// Example : lxc.aa_profile:unconfined etc.
			for (String lxcConf : container.getLxcConf().getValues()) {
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

		if (StringUtils.isNotBlank(container.getDomainName())) {
			createContainer.withDomainName(container.getDomainName());
		}

		if (container.getDnsSearch() != null && !container.getDnsSearch().getValues().isEmpty()) {
			createContainer.withDnsSearch(container.getDnsSearch().getValues());
		}

		if (StringUtils.isNotBlank(container.getEntrypoint())) {
			// TODO : Convert to ArrayOfString in model occie.
			String[] entrypoint = container.getEntrypoint().split(",");
			createContainer.withEntrypoint(entrypoint);
		}

		if (StringUtils.isNotBlank(container.getPid())) {
			createContainer.withPidMode(StringUtils.deleteWhitespace(container.getPid()));
		}

		if (container.isReadOnly()) {
			createContainer.withReadonlyRootfs(container.isReadOnly());
		}

		if (container.isTty()) {
			createContainer.withTty(container.isTty());
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
				LOGGER.info(c.getName());
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
			// Must never be thrown here.
			throw new DockerException("No docker client found to execute createContainer.");
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
			throw new DockerException("Container id is not set !");
		}

		InspectContainerResponse containerResponse = dockerClient.inspectContainerCmd(containerId).exec();

		return containerResponse;
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

	/**
	 * Start a container.
	 * 
	 * @param computeMachine
	 * @param container
	 * @throws DockerException
	 */
	public void startContainer(Compute computeMachine, Container container) throws DockerException {
		preCheckDockerClient(computeMachine);

		dockerClient.startContainerCmd(container.getContainerid()).exec();

		if (container.isMonitored()) { // Allow the monitoring of a container.
			// Collect monitoring data
			LOGGER.info("Starting metrics collection");

			// Load new docker client to fix blocking thread problem
			this.dockerClient = DockerConfigurationHelper.buildDockerClient(computeMachine);
			dockerClient.statsCmd(container.getContainerid()).exec(new StatsCallBack(container));
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
		dockerClient.stopContainerCmd(container.getContainerid()).exec();
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
		List<com.github.dockerjava.api.model.Container> containers = dockerClient.listContainersCmd().withShowAll(true)
				.exec();
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
			containerImage = "busybox";
			LOGGER.info("Use the default Docker Image: {}", containerImage);
		}
		LOGGER.info("Downloading image: ->" + containerImage);
		// Download a pre-built image
		try {
			// If the given image tag doesn't contain a version number, add "latest" as tag
			if (containerImage.indexOf(':') < 0) {
				dockerClient.pullImageCmd(containerImage).withTag("latest").exec(new PullImageResultCallback())
						.awaitSuccess();
			} else {
				dockerClient.pullImageCmd(containerImage).exec(new PullImageResultCallback()).awaitSuccess();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new DockerException(e.getMessage(), e);
		}
		LOGGER.info("Download is finished");
		return this.dockerClient;
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
			LOGGER.info("Identity added ..");

			String exCommand = "sudo sh -c " + "\"" + command + "\"";
			LOGGER.info(exCommand);

			// TODO : Support ssh connection on other ports than 22.
			session = jsc.getSession(user, host, 22);
			LOGGER.info("Session created ..");
			session.setConfig(config);
			LOGGER.info("Session config ..");

			session.connect();
			LOGGER.info("Session connected ..");

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(exCommand);
			((ChannelExec) channel).setErrStream(System.err);
			channel.connect();
		} catch (JSchException e) {
			LOGGER.info(e.getMessage());

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
				LOGGER.info(ip + " ssh-rsa " + key);

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

}
