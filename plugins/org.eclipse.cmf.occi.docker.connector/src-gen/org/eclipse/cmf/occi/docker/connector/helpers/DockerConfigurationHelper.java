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
import java.net.URI;
import java.util.Properties;

import org.eclipse.cmf.occi.docker.Machine;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

/**
 * Helper class to read properties config from docker.properties file located on
 * output designed.
 * 
 * @author Christophe Gourdin
 *
 */
public class DockerConfigurationHelper {

	public static final String DOCKER_PROPERTIES_FILENAME = "docker-credential.properties";

	public static final String KEY_DOCKER_HOST = "DOCKER_HOST";
	public static final String KEY_DOCKER_TLS_VERIFY = "DOCKER_TLS_VERIFY";
	public static final String KEY_DOCKER_CERT_PATH = "DOCKER_CERT_PATH";
	public static final String KEY_DOCKER_CONFIG = "DOCKER_CONFIG";
	public static final String KEY_DOCKER_USERNAME = "registry.username";
	public static final String KEY_DOCKER_PASSWORD = "registry.password";
	public static final String KEY_DOCKER_API_VERSION = "api.version";
	public static final String KEY_DOCKER_API_URL = "registry.url";
	public static final String KEY_DOCKER_EMAIL = "registry.email";
	public static final String DEFAULT_DOCKER_API_TLS_PORT = "2376"; // without TLS : 2375.
	public static final String DEFAULT_DOCKER_API_PORT = "2375";
	public static final String DEFAULT_LOCALHOST_MACHINE_ADDRESS = "tcp://127.0.0.1";
	public static final String DEFAULT_API_VERSION = "1.23";
	public static final String DEFAULT_DOCKER_CERT_PATH = System.getProperty("user.home") + File.separator + ".docker"
			+ File.separator + "machine" + File.separator + "certs";
	public static final String DEFAULT_DOCKER_HOME = System.getProperty("user.home") + File.separator + ".docker";

	private static Logger LOGGER = LoggerFactory.getLogger(DockerConfigurationHelper.class);

	/**
	 * Property file is defaulted to user home directory.
	 * 
	 * @throws IOException
	 */
	public static Properties loadDockerConfig() throws IOException {
		String homePath = System.getProperty("user.home") + File.separator;
		InputStream in;
		Properties prop = new Properties();

		try {
			in = new FileInputStream(homePath + DOCKER_PROPERTIES_FILENAME);
			prop.load(in);
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
					// no op.
				}
			}
		} catch (FileNotFoundException ex) {
			LOGGER.warn("cannot read docker configuration file on path :  " + homePath + DOCKER_PROPERTIES_FILENAME);
			LOGGER.warn("Reading environnement variables...");
			prop = System.getProperties();

			if (prop == null) {
				throw ex;
			}
			// TODO : Read env variable for docker account (username, password, email, api
			// version and api url.
			// TODO : Read attributes from a special account resource model : DockerAccount
			// (to create).
			// LOGGER.warn("Reading ENV file");
			// prop = loadDockerEnvVariable();
		}

		if (!prop.containsKey(KEY_DOCKER_USERNAME)) {
			throw new IOException("docker account username not found on docker configuration file : "
					+ KEY_DOCKER_USERNAME + "=" + "myusername");
		}
		if (!prop.containsKey(KEY_DOCKER_PASSWORD)) {
			throw new IOException("docker account password not found on docker configuration file."
					+ KEY_DOCKER_PASSWORD + "=" + "mypassword");
		}
		if (!prop.containsKey(KEY_DOCKER_API_VERSION)) {
			LOGGER.warn(
					"Docker api version not found on configuration file, set to 1.24 by default, field may be set as this : "
							+ KEY_DOCKER_API_VERSION + "=" + DEFAULT_API_VERSION);
			prop.setProperty(KEY_DOCKER_API_VERSION, DEFAULT_API_VERSION);
		}
		if (!prop.containsKey(KEY_DOCKER_API_URL)) {
			LOGGER.warn(
					"Docker api url not found on configuration file, set to \"https://index.docker.io/v1/\" by default, this property may be set as this : "
							+ KEY_DOCKER_API_URL + "=" + "https://index.docker.io/v1/");
			prop.setProperty(KEY_DOCKER_API_URL, "https://index.docker.io/v1/");
		}

		return prop;

	}

	/**
	 * Create a new docker client with configuration loaded.
	 * 
	 * @param compute
	 *            a compute machine for use with docker-machine and later with other
	 *            cloud extensions. If compute is null, the containers will be
	 *            managed locally.
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

		URI endpoint = DockerMachineHelper.getEndpoint(compute);
		System.out.println("Compute endpoint : " + endpoint);

		String certPath = null;
		certPath = DockerMachineHelper.getCertificatePath(compute);

		if (certPath == null) {
			// Read docker property certPath.
			LOGGER.warn("Reading property " + KEY_DOCKER_CERT_PATH);
			certPath = prop.getProperty(KEY_DOCKER_CERT_PATH);
			if (certPath == null) {
				// Assign default certPath.
				certPath = DEFAULT_DOCKER_CERT_PATH;
			}
		}
		DefaultDockerClientConfig config;
		System.out.println("Certificate path : " + certPath);

		String dockerHost = endpoint.toString();

		System.out.println("Docker host : " + dockerHost);
		String dockerHome = DEFAULT_DOCKER_HOME;
		System.out.println("Default Docker home : " + dockerHome);
		System.out.println("Defined Docker home : " + prop.getProperty(KEY_DOCKER_CONFIG));
		String tlsVerify = prop.getProperty(KEY_DOCKER_TLS_VERIFY);
		boolean withTlsCheck = false;
		if (("1").equals(tlsVerify)) {
			withTlsCheck = true;
			System.out.println("TLS mode on");
		} else {
			System.out.println("TLS mode off");
		}

		if (localMachine) {
			// Build generic default client.
			config = DefaultDockerClientConfig.createDefaultConfigBuilder()
					.withDockerHost(DEFAULT_LOCALHOST_MACHINE_ADDRESS + ":" + DEFAULT_DOCKER_API_PORT)
					.withApiVersion(prop.getProperty(KEY_DOCKER_API_VERSION)).withDockerCertPath(certPath)
					.withDockerTlsVerify(false).withRegistryEmail(prop.getProperty(KEY_DOCKER_EMAIL))
					.withRegistryUsername(prop.getProperty(KEY_DOCKER_USERNAME))
					.withRegistryPassword(prop.getProperty(KEY_DOCKER_PASSWORD))
					.withRegistryUrl(prop.getProperty(KEY_DOCKER_API_URL)).withDockerConfig(dockerHome).build();

		} else if (compute instanceof Machine) {
			// Build docker client for this compute machine.
			config = DefaultDockerClientConfig.createDefaultConfigBuilder().withDockerHost(dockerHost)
					.withApiVersion(prop.getProperty(KEY_DOCKER_API_VERSION)).withDockerCertPath(certPath)
					.withDockerTlsVerify(withTlsCheck).withDockerCertPath(prop.getProperty(KEY_DOCKER_CERT_PATH))
					.withDockerConfig(prop.getProperty(KEY_DOCKER_CONFIG))
					.withRegistryEmail(prop.getProperty(KEY_DOCKER_EMAIL))
					.withRegistryUsername(prop.getProperty(KEY_DOCKER_USERNAME))
					.withRegistryPassword(prop.getProperty(KEY_DOCKER_PASSWORD))
					.withRegistryUrl(prop.getProperty(KEY_DOCKER_API_URL)).withDockerConfig(dockerHome).build();

		} else {
			// Throw dockerException, it will be supported in the future.
			throw new DockerException("Other infrastructure extensions are not supported at this time.");
		}
		dockerClient = DockerClientBuilder.getInstance(config).build();

		return dockerClient;
	}

}
