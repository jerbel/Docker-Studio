package org.eclipse.cmf.occi.docker.connector;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

class DockerJavaAPITest {

	@Test
	void test() {
		DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
		DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();
		String name = "testContainer";
		CreateContainerResponse container
		  = dockerClient.createContainerCmd("mongo:3.6")
		    .withCmd("--bind_ip_all")
		    .withName("mongo")
		    .withHostName("baeldung")
		    .withEnv("MONGO_LATEST_VERSION=3.6")
		    .withPortBindings(PortBinding.parse("9999:27017"))
		    .withBinds(Bind.parse("/Users/baeldung/mongo/data/db:/data/db")).exec();
		
		InspectContainerResponse inspectContainerResponse 
		  = dockerClient.inspectContainerCmd(container.getId()).exec();
		
		List<com.github.dockerjava.api.model.Container> containers = 
				dockerClient.listContainersCmd()
				.withShowAll(true).exec();
		
		dockerClient.startContainerCmd(container.getId()).exec();
		 
		
		dockerClient.stopContainerCmd(container.getId()).exec();
		 
		dockerClient.removeContainerCmd(container.getId()).exec();
	}
	
	@Test
	void test2() {
		DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
		DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();
		
		CreateContainerResponse createContainerResponse = dockerClient.createContainerCmd("ubuntu")
				.withName("JUnitTestContainer")
				.withTty(true)
				.exec();
		
		dockerClient.startContainerCmd(createContainerResponse.getId()).exec();
	}

}
