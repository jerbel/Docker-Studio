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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.eclipse.cmf.occi.docker.Machine;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.sun.security.ntlm.Client;

/**
 * Helper class to read properties config from docker.properties file located on output designed.
 * @author Christophe Gourdin
 *
 */
public class DockerConfigurationHelper {
	
	public static final String DOCKER_PROPERTIES_FILENAME = "docker-credential.properties";
	public static final String KEY_DOCKER_USERNAME = "docker.username";
	public static final String KEY_DOCKER_PASSWORD = "docker.password";
	public static final String KEY_DOCKER_API_VERSION = "docker.api.version";
	public static final String KEY_DOCKER_API_URL = "docker.api.url";
	public static final String DEFAULT_DOCKER_API_TLS_PORT = "2376";  // without TLS : 2375.
	public static final String DEFAULT_DOCKER_API_PORT = "2375";
	public static final String DEFAULT_LOCALHOST_MACHINE_ADDRESS = "tcp://127.0.0.1";
	
	private static Logger LOGGER = LoggerFactory.getLogger(DockerConfigurationHelper.class);
	
	/**
	 * Property file is defaulted to user home directory.
	 * @throws IOException
	 */
	public static Properties loadDockerConfig() throws IOException {
		String homePath = System.getProperty("user.home") + File.separator;
		InputStream in;
		Properties prop = new Properties();
		try {
			in = new FileInputStream(homePath + DOCKER_PROPERTIES_FILENAME);
		} catch (FileNotFoundException ex) {
			LOGGER.warn("cannot read docker configuration file on path :  " + homePath + DOCKER_PROPERTIES_FILENAME);
			throw ex;
			// TODO : Read env variable for docker account (username, password, email, api version and api url.
			// TODO : Read attributes from a special account resource model : DockerAccount (to create).
			// LOGGER.warn("Reading ENV file");
			// prop = loadDockerEnvVariable();
		}
		prop.load(in);
		if (in != null) {
			try {
				in.close();
			} catch (IOException ex) {
				// no op.
			}
		}
		if (!prop.containsKey(KEY_DOCKER_USERNAME)) {
			throw new IOException("docker account username not found on docker configuration file : " + KEY_DOCKER_USERNAME + "=" + "myusername");
		}
		if (!prop.containsKey(KEY_DOCKER_PASSWORD)) {
			throw new IOException("docker account password not found on docker configuration file." + KEY_DOCKER_PASSWORD + "=" + "mypassword");
		}
		if (!prop.containsKey(KEY_DOCKER_API_VERSION)) {
			LOGGER.warn("Docker api version not found on configuration file, set to 1.24 by default, field may be set as this : " + KEY_DOCKER_API_VERSION + "=" + "1.23");
			prop.setProperty(KEY_DOCKER_API_VERSION, "1.23");
		}
		if (!prop.containsKey(KEY_DOCKER_API_URL)) {
			LOGGER.warn("Docker api url not found on configuration file, set to \"https://index.docker.io/v1/\" by default, this property may be set as this : " + KEY_DOCKER_API_URL + "=" + "https://index.docker.io/v1/");
			prop.setProperty(KEY_DOCKER_API_URL, "https://index.docker.io/v1/");
		}
		
		return prop;
		
	}
	
	/**
	 * Create a new docker client with configuration loaded.
	 * @param compute a compute machine for use with docker-machine and later with other cloud extensions. 
	 *  If compute is null, the containers will be managed locally.
	 * @return a new DockerClient object.
	 */
	public static DockerClient buildDockerClient(Compute compute) throws DockerException {
		DockerClient dockerClient = null;
		boolean localMachine = compute == null;
		Properties prop = null;
		
		try {
			prop = loadDockerConfig();
		} catch (IOException ex) {
			LOGGER.error("Error while loading configuration file : " + ex.getMessage());
			throw new DockerException(ex);
		}
		
		String endpoint = DockerMachineHelper.getEndpoint(compute);
		String certPath = DockerMachineHelper.getCertificatePath(compute);
		if (localMachine) {
			// Build generic default client.
			
		} else if (compute instanceof Machine) {
			// Build docker client for this compute machine.
			
		} else {
			// Throw dockerException, it will be supported in the future.
			
		}
		
		
		return dockerClient;
	}
	
	
//	public DockerClient setConfig(String machine, PreferenceValues properties) {
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
	
	
}
