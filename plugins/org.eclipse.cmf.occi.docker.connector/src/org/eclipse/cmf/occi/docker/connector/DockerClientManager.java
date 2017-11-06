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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import com.github.dockerjava.api.model.Link;
import org.eclipse.cmf.occi.core.Resource;
import org.eclipse.cmf.occi.docker.ArrayOfString;
import org.eclipse.cmf.occi.docker.Container;
import org.eclipse.cmf.occi.docker.Machine;
import org.eclipse.cmf.occi.docker.Volumesfrom;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.docker.connector.helpers.DockerConfigurationHelper;
import org.eclipse.cmf.occi.docker.connector.helpers.DockerMachineHelper;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.LxcConf;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports.Binding;
import com.github.dockerjava.api.model.RestartPolicy;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.api.model.VolumesFrom;
import com.google.common.collect.Multimap;

/**
 * Manage the docker client and used by connector when executing actions.
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
		// Build a docker client related to this compute, if compute is null, dockerclient will be relative to this local machine.
		this.dockerClient = DockerConfigurationHelper.buildDockerClient(compute);
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
	 * @param compute
	 * @param containerId to check
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
	public CreateContainerResponse createContainer(Compute computeMachine, Container container, Multimap<String, String> containerDependency) throws DockerException {
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
	 * @param containerDependency may be null if no dependencies.
	 * @return
	 * @throws DockerException
	 */
	public CreateContainerCmd containerBuilder(Container container, Multimap<String, String> containerDependency) throws DockerException {
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
		ArrayOfString addHosts =  container.getAddHost();
		
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
		if ( ports != null && ports.getValues().isEmpty()) {
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
			for(String volume : container.getVolumes().getValues()){
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
					throw new DockerException("Lxc conf format must be like this one --> lxc.aa_profile:unconfined --> key:value");
				}
			}
			if (!lxcConfigs.isEmpty()) {
				createContainer.withLxcConf(lxcConfigs);
			}
		}	
		
		if(StringUtils.isNotBlank(container.getDomainName())){
			createContainer.withDomainName(container.getDomainName());
		}
		
		if(container.getDnsSearch() != null && !container.getDnsSearch().getValues().isEmpty()) {
			createContainer.withDnsSearch(container.getDnsSearch().getValues());
		}
		
		if(StringUtils.isNotBlank(container.getEntrypoint())) {
			// TODO : Convert to ArrayOfString in model occie.
			String[] entrypoint = container.getEntrypoint().split(",");
			createContainer.withEntrypoint(entrypoint);
		}
		
		if (StringUtils.isNotBlank(container.getPid())) {
			createContainer.withPidMode(StringUtils.deleteWhitespace(container.getPid()));
		}
		
		if(container.isReadOnly()) {
			createContainer.withReadonlyRootfs(container.isReadOnly());
		}
		
		if(container.isTty()) {
			createContainer.withTty(container.isTty());
		}
		
		if(StringUtils.isNotBlank(container.getRestart())) {
			createContainer.withRestartPolicy(RestartPolicy.parse(StringUtils.deleteWhitespace(container.getRestart())));
		}
		
		if(StringUtils.isNotBlank(container.getWorkingDir())) {
			createContainer.withWorkingDir(StringUtils.deleteWhitespace(container.getWorkingDir()));
			// createContainer.getCpusetCpus();
		}
		
		List<Container> containersWithVolumes = new LinkedList<>();
		
		List<org.eclipse.cmf.occi.docker.Volume> volumesInsideHost = new LinkedList<>();
		
		for (Resource r: containersWithVolumes(container)){
			if(r instanceof Container){
				containersWithVolumes.add((Container)r);
			}
			if(r instanceof org.eclipse.cmf.occi.docker.Volume){
				volumesInsideHost.add((org.eclipse.cmf.occi.docker.Volume)r);
			}
		}
	
		if (!containersWithVolumes.isEmpty()) {
			List<VolumesFrom> volumesFrom = new LinkedList<>();
			for(Container c : containersWithVolumes) {
				volumesFrom.add(new VolumesFrom(c.getName()));
				LOGGER.info(c.getName());
			}
			createContainer.withVolumesFrom(volumesFrom);
		}

		if (!volumesInsideHost.isEmpty()) {
			List<Bind> volumesBind = new LinkedList<>();
			List<Volume> vs = new ArrayList<>();
			for(org.eclipse.cmf.occi.docker.Volume v : volumesInsideHost) {
				Volume newVolume = null;
				if (!StringUtils.isBlank(v.getDestination())) {
					newVolume = new Volume(v.getDestination());
					vs.add(newVolume);
				}
				
				if(!StringUtils.isBlank(v.getSource())) {
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
	 * @param c a model container
	 * @return
	 */
	public List<Resource> containersWithVolumes(Container c) {
		List<Resource> containersFrom = new ArrayList<>(); 
		for (org.eclipse.cmf.occi.core.Link l : c.getLinks()) {
			if(l instanceof Volumesfrom){
				containersFrom.add(l.getTarget());
			}
		}
		return containersFrom;
	}
	
	/**
	 * Control if docker client is set on the good machine.
	 * @param computeMachine
	 * @return
	 * @throws DockerException
	 */
	public void preCheckDockerClient(Compute computeMachine) throws DockerException {
		if (this.dockerClient == null) {
			// Must never be thrown here.
			throw new DockerException("No docker client found to execute createContainer.");
		}
		if (compute != null 
				&& compute instanceof Machine 
				&& computeMachine instanceof Machine 
				&& !(((Machine)compute).getName().equalsIgnoreCase(((Machine)computeMachine).getName()))) {
			
			this.dockerClient = DockerConfigurationHelper.buildDockerClient(compute);
			this.compute = computeMachine;
		}
		
	}

	/**
	 * Inspect/describe a container.
	 * @param computeMachine
	 * @param containerId
	 * @return
	 * @throws DockerException
	 */
	public InspectContainerResponse inspectContainer(Compute computeMachine, final String containerId) throws DockerException {
		preCheckDockerClient(computeMachine);
		if (containerId == null) {
			throw new DockerException("Container id is not set !");
		}
		
		InspectContainerResponse containerResponse = dockerClient.inspectContainerCmd(containerId).exec();
		return containerResponse;
	}
	
	
	
	// TODO : The following migration code..
	
//		
//		if (container.privileged) {
//			create.withPrivileged(container.privileged)
//		}
//		if (StringUtils.isNotBlank(container.dns)) {
//			create.withDns(StringUtils.deleteWhitespace(container.dns))
//		}
//		if (StringUtils.isNotBlank(container.environment)) {
//			var String[] env = StringUtils.deleteWhitespace(container.environment).split(",")
//			create.withEnv(env)
//		}
//		LOGGER.info("Container ports = " + container.ports)
//		if (StringUtils.isNotBlank(container.ports)) {
//			val String[] unparsedPortBindings = container.ports.split(" ")
//			var List<ExposedPort> exposedPortsList = new ArrayList<ExposedPort>
//			var List<PortBinding> portBindingsList = new ArrayList<PortBinding>
//			for (var i = 0; i < unparsedPortBindings.length; i++) {
//				val String[] l_r_ports = unparsedPortBindings.get(i).split(":")
//				var ExposedPort tcp = ExposedPort.tcp(Integer.parseInt(l_r_ports.get(0)))
//				var PortBinding portBindings = null
//				// Exposed port is set with l_r_ports.get(0)
//				// Binding port is set with l_r_ports.get(1)
//				if (l_r_ports.size == 2) {
//					if (StringUtils.isNotBlank(l_r_ports.get(1))) {
//						portBindings = new PortBinding(Binding.bindPort(Integer.parseInt(l_r_ports.get(1))), tcp)
//					} else {
//						portBindings = new PortBinding(Binding.bindPort(32768), tcp) // TODO Create dynamic port number
//					}
//				}
//				exposedPortsList.add(tcp)
//				portBindingsList.add(portBindings)
//			}
//
//			create.withExposedPorts(exposedPortsList).withPortBindings(portBindingsList)
//		}
//		if (StringUtils.isNotBlank(container.name)) {
//			create.withName(StringUtils.deleteWhitespace(container.name))
//		}
//		if (StringUtils.isNotBlank(container.hostname)) {
//			create.withHostName(StringUtils.deleteWhitespace(container.hostname))
//		}
//		if (StringUtils.isNotBlank(container.net)) {
//			create.withNetworkMode(container.net)
//		}
//		if (container.publish_all) {
//			create.withPublishAllPorts(container.publish_all)
//		}
//		if (container.stdin_open) {
//			create.withStdInOnce(container.stdin_open)
//		}
//		if (StringUtils.isNotBlank(container.user)) {
//			create.withUser(container.user)
//		}
//		if (StringUtils.isNotBlank(container.volumes)) {
//			var String[] volumes = StringUtils.deleteWhitespace(container.volumes).split(",")
//			var List<Volume> vs = new ArrayList<Volume>
//			for(v:volumes){
//				 var Volume newVolume = new Volume(v)
//				 vs.add(newVolume)
//			}
//			create.withVolumes(vs)
//		}
//		if (container.mem_limit > 0) {
//			create.withMemory(Long.valueOf(container.mem_limit))
//		}
//		if (container.memory_swap > 0) {
//			create.withMemory(Long.valueOf(container.memory_swap))
//		}
//		if (StringUtils.isNotBlank(container.lxc_conf)) {
//			// TODO lxc_conf should be String array..
//			val LxcConf lxcCon = new LxcConf("key", "value")
//			create.withLxcConf(lxcCon)
//		}		
//		if(StringUtils.isNotBlank(container.domainname)){
//			create.withDomainName(container.domainname)
//		}
//		if(StringUtils.isNotBlank(container.dns_search)){
//			var String[] dnsSearch = container.dns_search.split(",")
//			create.withDnsSearch(dnsSearch)
//		}
//		if(StringUtils.isNotBlank(container.entrypoint)){
//			var String[] entrypoint = container.entrypoint.split(",")
//			create.withEntrypoint(entrypoint)
//		}
//		if(StringUtils.isNotBlank(container.net)){
//			create.withNetworkMode(StringUtils.deleteWhitespace(container.net))
//		}
//		if(StringUtils.isNotBlank(container.pid)){
//			create.withPidMode(StringUtils.deleteWhitespace(container.pid))
//		}
//		if(container.privileged){
//			create.withPrivileged(container.privileged)
//		}		
//		if(container.publish_all){
//			create.withPublishAllPorts(container.publish_all)
//		}		
//		if(container.read_only){
//			create.withReadonlyRootfs(container.read_only)
//		}		
//		if(container.tty){
//			create.withTty(container.tty)
//		}		
//		if(StringUtils.isNotBlank(container.restart)){
//			create.withRestartPolicy(RestartPolicy.parse(StringUtils.deleteWhitespace(container.restart)))
//		}		
//		if(StringUtils.isNotBlank(container.working_dir)){
//			create.withWorkingDir(StringUtils.deleteWhitespace(container.working_dir))
//			create.cpusetCpus
//		}		
//		var List<Container> containersWithVolumes = new ArrayList
//		var List<org.occiware.clouddesigner.occi.docker.Volume> volumesInsideHost = new ArrayList
//		for(Resource r: containersWithVolumes(container)){
//			if(r instanceof Container){
//				containersWithVolumes.add(r as Container)
//			}
//			if(r instanceof org.occiware.clouddesigner.occi.docker.Volume){
//				volumesInsideHost.add(r as org.occiware.clouddesigner.occi.docker.Volume)
//			}
//		}
//		//val List<Container> containersWithVolumes = containersWithVolumes(container)
//		if(!containersWithVolumes.nullOrEmpty){
//			var List<VolumesFrom> volumesFrom = new ArrayList
//			for(Container c : containersWithVolumes){
//				volumesFrom.add(new VolumesFrom(c.name))
//				LOGGER.info(c.name)
//			}
//			create.withVolumesFrom(volumesFrom)
//		}
//
//		if(!volumesInsideHost.nullOrEmpty){
//			var List<Bind> volumesBind = new ArrayList
//			var List<Volume> vs = new ArrayList<Volume>
//			for(org.occiware.clouddesigner.occi.docker.Volume v : volumesInsideHost){
//				var Volume newVolume = null
//				if(!StringUtils.isBlank(v.destination)){
//					newVolume = new Volume(v.destination)
//					vs.add(newVolume)
//				}
//				if(!StringUtils.isBlank(v.source)){
//					var newBind = new Bind(v.source, newVolume)
//					volumesBind.add(newBind)
//				}
//			}
//			create.withVolumes(vs)
//			create.withBinds(volumesBind)
//		}
//		
//		
////		val create = containerFactory(container, dockerClient)
//
//		// Run Execution
//		val CreateContainerResponse rcontainer = create.exec
//
//		// Set container ID
//		container.containerid = rcontainer.id
//		LOGGER.info("Created container: {}", container.containerid)
//
//		result.put(dockerClient, rcontainer)
//		return result
//	}
//
//	def createContainer(Machine machine, Container container, Multimap<String, String> containerDependency) {
//
//		// Set dockerClient
//		if (dockerClient == null) {
//			dockerClient = setConfig(machine.name, properties)
//		} else if (!currentMachine.equalsIgnoreCase(machine.name)) {
//			dockerClient = setConfig(machine.name, properties)
//		}
//		var Map<DockerClient, CreateContainerResponse> result = new LinkedHashMap<DockerClient, CreateContainerResponse>
//		val create = containerFactory(container, dockerClient, containerDependency)
//
//		// Run Execution
//		val CreateContainerResponse rcontainer = create.exec
//
//		// Set container ID
//		container.containerid = rcontainer.id
//		LOGGER.info("Created container: {}", container.containerid)
//
//		result.put(dockerClient, rcontainer)
//		return result
//	}
//
//	def createNetwork(Machine machine, Network network) {
//		// Set dockerClient
//		if (dockerClient == null) {
//			dockerClient = setConfig(machine.name, properties)
//		} else if (!currentMachine.equalsIgnoreCase(machine.name)) {
//			dockerClient = setConfig(machine.name, properties)
//		}
//
//		var List<Config> ipamConfigs = newArrayList()
//		var com.github.dockerjava.api.model.Network.Ipam ipam = null
//
//		if (StringUtils.isNotBlank(network.subnet)) {
//			ipamConfigs.add(new com.github.dockerjava.api.model.Network.Ipam.Config().withSubnet(network.subnet))
//		} else {
//			ipamConfigs.add(new com.github.dockerjava.api.model.Network.Ipam.Config().withSubnet("10.67.79.0/24"))
//		}
//
//		if (StringUtils.isNotBlank(network.gateway)) {
//			ipamConfigs.add(new com.github.dockerjava.api.model.Network.Ipam.Config().withGateway(network.gateway))
//		}
//		if (StringUtils.isNotBlank(network.ip_range)) {
//			ipamConfigs.add(new com.github.dockerjava.api.model.Network.Ipam.Config().withIpRange(network.ip_range))
//		}
//
//		try {
//			ipam = new com.github.dockerjava.api.model.Network().ipam.withConfig(ipamConfigs)
//		} catch (InvocationTargetException exception) {
//			LOGGER.error(" InvocationTargetException: " + exception.cause.message)
//		} catch (Exception e) {
//			LOGGER.error("Exception:" + e.message)
//		}
//		// Initiate the NetworkCommand
//		var CreateNetworkCmd createNetworkCmd = dockerClient.createNetworkCmd().withIpam(ipam)
//		if (StringUtils.isNotBlank(network.name)) {
//			createNetworkCmd = createNetworkCmd.withName(network.name)
//		}
//		if (StringUtils.isNotBlank(network.driver)) {
//			createNetworkCmd = createNetworkCmd.withDriver(network.driver)
//		}
//
//		var CreateNetworkResponse createNetworkResponse = null
//		var com.github.dockerjava.api.model.Network updateNetwork = null
//		try {
//			// Create an overlay network
//			createNetworkResponse = createNetworkCmd.withCheckDuplicate(true).exec()
//		} catch (InternalServerErrorException exception) {
//			LOGGER.error(exception.message)
//			createNetworkResponse = null
//			updateNetwork = dockerClient.inspectNetworkCmd().withNetworkId(network.name).exec()
//			updateNetwork.id
//		}
//		if (createNetworkResponse != null) {
//			return createNetworkResponse.id
//		} else {
//			return updateNetwork.id
//		}
//	}
//
//	def connectToNetwork(Machine machine, Map<Container, Set<NetworkLink>> networks) {
//		// Set dockerClient
//		if (dockerClient == null) {
//			dockerClient = setConfig(machine.name, properties)
//		} else if (!currentMachine.equalsIgnoreCase(machine.name)) {
//			dockerClient = setConfig(machine.name, properties)
//		}
//		if (networks.size > 0) {
//			for (Map.Entry<Container, Set<NetworkLink>> entry : networks.entrySet) {
//				for (NetworkLink netLink : entry.value) {
//					try {
//					dockerClient.connectToNetworkCmd().withNetworkId((netLink.target as Network).networkId).withContainerId(entry.key.containerid).exec()
//					} catch (InternalServerErrorException exception) {
//						LOGGER.error("InternalServerErrorException: " + exception.message)
//					}
//				}
//			}
//		}
//	}
//
//	def void removeContainer(String machineName, String containerId) {
//
//		// Set dockerClient
//		if (dockerClient == null) {
//			dockerClient = setConfig(machineName, properties)
//		} else if (!currentMachine.equalsIgnoreCase(machineName)) {
//			dockerClient = setConfig(machineName, properties)
//		}
//
//		dockerClient.removeContainerCmd(containerId).exec
//	}
//
//
//	def List<Resource> containersWithVolumes(Container c) {
//		var containersFrom = new ArrayList 
//		for (org.occiware.clouddesigner.occi.Link l : c.links) {
//			if(l instanceof Volumesfrom){
//				containersFrom.add(l.target)
////				if (l.target instanceof org.occiware.clouddesigner.occi.docker.Container) {
////					val container = l.target as org.occiware.clouddesigner.occi.docker.Container
////					containersFrom.add(container)
////				}
//			}
//		}
//		return containersFrom
//	}
//
//	def InspectContainerResponse inspectContainer(Map<DockerClient, CreateContainerResponse> map) {
//		val dockerClient = map.keySet().iterator().next() as DockerClient
//		val createContainerResponse = map.get(dockerClient) as CreateContainerResponse
//		val InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(
//			createContainerResponse.getId()).exec()
//		return inspectContainerResponse
//
//	}
//
//	def InspectContainerResponse inspectContainer(Machine machine, String containerId) {
//
//		// Set dockerClient
//		if (dockerClient == null) {
//			dockerClient = setConfig(machine.name, properties)
//		} else if (!currentMachine.equalsIgnoreCase(machine.name)) {
//			dockerClient = setConfig(machine.name, properties)
//			currentMachine = machine.name
//		}
//		val InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(containerId).exec()
//		return inspectContainerResponse
//
//	}
//
//	def startContainer(Machine machine, Container container) {
//
//		// Set dockerClient
//		if (dockerClient != null) {
//			dockerClient = DockerContainerManager.dockerClient
//		} else if (!currentMachine.equalsIgnoreCase(machine.name)) {
//			dockerClient = setConfig(machine.name, properties)
//		}
//
//		// Start the container
//		dockerClient.startContainerCmd(container.containerid).exec
//		
//		if(container.monitored){ // Allow the monitoring of a Container
//		// Collect monitoring data
//		LOGGER.info("Starting metrics collection");
//		
//		// load new docker client to Fix blocking Thread problem.
//		dockerClient = setConfig(machine.name, properties)
//		
//		dockerClient.statsCmd(container.containerid).exec(new StatsCallback(container))			
//			
//		}
//	}
//
//	def startContainer(Machine machine, String containerId) {
//
//		// Set dockerClient
//		if (dockerClient == null) {
//			dockerClient = setConfig(machine.name, properties)
//		} else if (!currentMachine.equalsIgnoreCase(machine.name)) {
//			dockerClient = setConfig(machine.name, properties)
//		}
//		// Start the container
//		dockerClient.startContainerCmd(containerId).exec
//
//		// Collect monitoring data
//		LOGGER.info("Starting metrics collection");
//		// load new docker client to Fix blocking Thread problem.
//		dockerClient = setConfig(machine.name, properties)
//		dockerClient.statsCmd(containerId).exec(new StatsCallback(containerId))
//	}
//
//	def stopContainer(Machine machine, Container container) {
//
//		// Set dockerClient
//		if (dockerClient == null) {
//			dockerClient = setConfig(machine.name, properties)
//		} else if (!currentMachine.equalsIgnoreCase(machine.name)) {
//			dockerClient = setConfig(machine.name, properties)
//		}
//		// Stop the container
//		dockerClient.stopContainerCmd(container.id).exec
//	}
//
//	def stopContainer(Machine machine, String containerId) {
//
//		// Set dockerClient
//		if (dockerClient == null) {
//			dockerClient = setConfig(machine.name, properties)
//		} else if (!currentMachine.equalsIgnoreCase(machine.name)) {
//			dockerClient = setConfig(machine.name, properties)
//		}
//		// Stop the container 
//		dockerClient.stopContainerCmd(containerId).exec
//	}
//
//	def waitContainer(Machine machine, Container container) {
//
//		// Set dockerClient
//		if (dockerClient == null) {
//			dockerClient = setConfig(machine.name, properties)
//		} else if (!currentMachine.equalsIgnoreCase(machine.name)) {
//			dockerClient = setConfig(machine.name, properties)
//		}
//		dockerClient.waitContainerCmd(container.id).exec(new WaitContainerResultCallback()).awaitStatusCode()
//	}
//
//	def renameContainer(Machine machine, String containerId, String newName) {
//
//		// Set dockerClient
//		if (dockerClient == null) {
//			dockerClient = setConfig(machine.name, properties)
//		} else if (!currentMachine.equalsIgnoreCase(machine.name)) {
//			dockerClient = setConfig(machine.name, properties)
//		}
//		// Rename the container 
//		dockerClient.renameContainerCmd(containerId).withName(newName).exec
//	}
//
//	def removeContainer(Machine machine, String containerId) {
//		removeContainer(machine.name, containerId)
//	}
//
//	def List<com.github.dockerjava.api.model.Container> listContainer(String machineName) {
//
//		// Set dockerClient
//		if (dockerClient == null) {
//			dockerClient = setConfig(machineName, properties)
//		} else if (!currentMachine.equalsIgnoreCase(machineName)) {
//			dockerClient = setConfig(machineName, properties)
//		}
//		val List<com.github.dockerjava.api.model.Container> containers = dockerClient.listContainersCmd().
//			withShowAll(true).exec()
//		return containers
//	}
//
//	def pullImage(Machine machine, String image) {
//
//		// Set dockerClient
//		if (dockerClient == null) {
//			dockerClient = setConfig(machine.name, properties)
//		} else if (!currentMachine.equalsIgnoreCase(machine.name)) {
//			dockerClient = setConfig(machine.name, properties)
//		}
//		var containerImage = image
//		if (!StringUtils.isNotBlank(containerImage)) {
//			containerImage = "busybox"
//			LOGGER.info("Use the default Docker Image: {}", containerImage)
//		}
//		var String output = null
//		LOGGER.info("Downloading image: ->" + containerImage)
//		// Download a pre-built image
//		try {
//			//If the given image tag doesn't contain a version number, add "latest" as tag
//			if (containerImage.indexOf(':') < 0) {
//				dockerClient.pullImageCmd(containerImage).withTag("latest").exec(new PullImageResultCallback()).
//				awaitSuccess()
//			} else {
//				dockerClient.pullImageCmd(containerImage).exec(new PullImageResultCallback()).
//				awaitSuccess()
//			}
//		// output = DockerUtil.asString(resp)
//		} catch (Exception e) {
//			LOGGER.error(e.message)
//		}
//		LOGGER.info(output)
//		LOGGER.info("Download is finished")
//		/*
//		 * 
//		 * if (!machineContainsImage(machine.name, containerImage)) {
//		 * 	LOGGER.info("Downloading image: ->" + containerImage)
//		 * 	addImageToMachine(machine.name, containerImage)
//
//		 * 	// Download a pre-built image
//		 * 	try {
//		 * 		dockerClient.pullImageCmd(containerImage).withTag("latest").exec(new PullImageResultCallback()).awaitSuccess()
//		 * 		//output = DockerUtil.asString(resp)
//		 * 	} catch (Exception e) {
//		 * 		LOGGER.error(e.message)
//		 * 	}
//		 * 	LOGGER.info(output)
//		 * 	LOGGER.info("Download is finished")
//		 * }else{
//		 * 	LOGGER.info("Downloading image: ->" + containerImage)
//		 * 	// Download a pre-built image
//		 * 	try {
//		 * 		dockerClient.pullImageCmd(containerImage).withTag("latest").exec(new PullImageResultCallback()).awaitSuccess()
//		 * 		//output = DockerUtil.asString(resp)
//		 * 	} catch (Exception e) {
//		 * 		LOGGER.error(e.message)
//		 * 	}
//		 * 	LOGGER.info(output)
//		 * 	LOGGER.info("Download is finished")
//		 * 	
//		 * }
//		 */
//		return dockerClient
//
//	}
//
//	def boolean machineContainsImage(String machine, String image) {
//		if (images.get(machine) != null) {
//			return images.get(machine).contains(image)
//		}
//		return false
//	}
//
//	def void addImageToMachine(String machine, String image) {
//		if (!images.containsKey(machine)) {
//			var tempList = new ArrayList
//			tempList.add(image)
//			images.put(machine, tempList)
//		} else {
//			var tempList = images.get(machine)
//			tempList.add(image)
//			images.put(machine, tempList)
//		}
//	}
//
//	def DockerClient setConfig(String machine, PreferenceValues properties) {
//		LOGGER.info("Trying to connect inside machine ---> " + machine)
//		var String port = null
//		var String ENDPOINT = DockerMachineManager.urlCmd(Runtime.getRuntime, machine)
//		val String certPath = DockerUtil.getEnv(machine)
//		LOGGER.info("DOCKER_CERT_PATH=" + certPath)
//		var DockerClientConfig config = null
//		val lconfig = new DockerConfig
//		var dockerProperties = lconfig.loadConfig
//		// Set Docker host port
//		port = ":2376"
//
//		LOGGER.info("ENDPOINT : " + ENDPOINT)
//		val dockerHost = if (ENDPOINT == null || ENDPOINT.trim.length == 0) "tcp://127.0.0.1" + port else ENDPOINT.trim
//		LOGGER.info("Connection inside machine: " + machine + " with uri: " + dockerHost.toString)
//		var dockerHome = System.getProperty("user.home") + File.separator + ".docker"
//		try {
//			if (properties.version != null) {
//				config = DefaultDockerClientConfig.createDefaultConfigBuilder.withApiVersion(properties.version.trim).
//					withDockerHost(dockerHost).withDockerTlsVerify(true).withRegistryUsername(properties.username.trim).
//					withRegistryPassword(properties.password.trim).withRegistryEmail(properties.email.trim).
//					withRegistryUrl(properties.url.trim).withDockerCertPath(certPath).withDockerConfig(
//						dockerHome).build()
//
//			}
//		} catch (Exception exception) {
//			LOGGER.error("Loading docker-java properties files ...")
//			config = DefaultDockerClientConfig.createDefaultConfigBuilder.withApiVersion(
//				dockerProperties.get("api.version").toString).withDockerHost(dockerHost).withDockerTlsVerify(true).
//				withRegistryUsername(dockerProperties.get("docker.username").toString).withRegistryPassword(
//					dockerProperties.get("docker.password").toString).withRegistryEmail(
//					dockerProperties.get("docker.email").toString).withRegistryUrl(
//					dockerProperties.get("docker.url").toString).withDockerCertPath(certPath).withDockerConfig(
//					dockerHome).build()
//		}
//		val DockerClient dockerClient = DockerClientBuilder.getInstance(config).build()
//
//		// Set the new machine as the current
//		currentMachine = machine
//		return dockerClient
//	}
//
//	def void connect() {
//		var Session session = null
//		val File test = new File("test")
//		val host = "192.168.99.100"
//		val user = "docker"
//		val int port = 22
//		val privatekey = "/Users/spirals/.docker/machine/machines/ghost/id_rsa"
//
//		try {
//			val JSch jsc = new JSch
//			jsc.setKnownHosts("/dev/null")
//			jsc.addIdentity(privatekey)
//			jsc.knownHosts = "/dev/null"
// 			var Properties config = new Properties();
//        	config.put("StrictHostKeyChecking", "no")			
//			session = jsc.getSession(user, host, port)
//			session.config = config
//			session.connect()
//			println("Connection successfully ...")
//
//		} catch (JSchException e) {
//			LOGGER.info(e.toString)
//			e.printStackTrace
////			addHost(session.getHostKey().getKey(), host, "test")
//		}
//	}
//
//	def void connect(String host, String privateKey, String command) {
//		var Session session = null
//		val user = "docker"
//		val tempDir = createTempDir("knowHosts")
//		val File test = new File(tempDir + "/hosts")
//
//		// Do not remove an existing file
//		if (!test.exists) {
//			test.createNewFile
//		}
//
//
//		try {
//			val JSch jsc = new JSch
//
//			jsc.knownHosts = "/dev/null"
// 			var Properties config = new Properties();
//        	config.put("StrictHostKeyChecking", "no")			
//
//			jsc.setKnownHosts("/dev/null")
//			jsc.addIdentity(privateKey)
//			LOGGER.info("Identity added ..")
//
//			val exCommand = "sudo sh -c " + "\"" + command + "\""
//			LOGGER.info(exCommand)
//
//			session = jsc.getSession(user, host, 22)
//			LOGGER.info("Session created ..")
//			session.setConfig(config)
//			LOGGER.info("Session config ..") 
//
//			session.connect()
//			LOGGER.info("Session connected ..")
//
//			val Channel channel = session.openChannel("exec")
//			(channel as ChannelExec).setCommand(exCommand)
//			(channel as ChannelExec).setErrStream(System.err)
//			channel.connect()
//		} catch (JSchException e) {
//			LOGGER.info(e.toString)
//
//		}
//		session.disconnect()
//	}
//
//	def void addHost(String key, String ip, String knowHosts) {
//		try {
//			val FileWriter tmpwriter = new FileWriter(knowHosts, true)
//			val String newLine = ip + " ssh-rsa " + key + "\n"
//			if (!hostAlreadyExist(newLine, knowHosts)) {
//				tmpwriter.append(newLine)
//				LOGGER.info(ip + " ssh-rsa " + key)
//
//				tmpwriter.flush()
//				tmpwriter.close()
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace()
//		}
//	}
//
//	def boolean hostAlreadyExist(String newLine, String knowHosts) {
//		val File hostFile = new File(knowHosts)
//		var BufferedReader br = new BufferedReader(new FileReader(hostFile))
//		var String line = null
//		while ((line = br.readLine()) != null) {
//			if (line.trim.equalsIgnoreCase(newLine.trim)) {
//				return true
//			}
//		}
//		br.close()
//		return false
//	}
//
//	def File createTempDir(String baseName) {
//		val File baseDir = new File(System.getProperty("java.io.tmpdir"))
//		var File tempDir = new File(baseDir, baseName)
//		if (!tempDir.exists) {
//			if (tempDir.mkdir()) {
//				return tempDir
//			}
//		} else {
//			return tempDir
//		}
//	}
	
	
	
	
	
}
