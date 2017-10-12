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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.cmf.occi.docker.Machine;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.docker.connector.helpers.DockerConfigurationHelper;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;

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
	
	
	
	
}
