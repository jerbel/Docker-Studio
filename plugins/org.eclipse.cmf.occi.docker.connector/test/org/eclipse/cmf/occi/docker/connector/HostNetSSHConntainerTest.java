package org.eclipse.cmf.occi.docker.connector;

import java.nio.file.Paths;

import org.eclipse.cmf.occi.core.util.OcciRegistry;
import org.eclipse.cmf.occi.docker.connector.manager.DockerClientManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HostNetSSHConntainerTest {
	
	@BeforeAll
	public static void setUpExtensions() {
		OcciRegistry.getInstance().registerExtension("http://schemas.modmacao.org/modmacao#",Paths.get("testextensions/modmacao.occie").toString());
		OcciRegistry.getInstance().registerExtension("http://schemas.modmacao.org/occi/platform#",Paths.get("testextensions/platform.occie").toString());
		OcciRegistry.getInstance().registerExtension("http://schemas.ogf.org/occi/infrastructure#",Paths.get("testextensions/Infrastructure.occie").toString());
		OcciRegistry.getInstance().registerExtension("http://schemas.ogf.org/occi/core#",Paths.get("testextensions/Core.occie").toString());
		OcciRegistry.getInstance().registerExtension("http://schemas.modmacao.org/occi/ansible#",Paths.get("testextensions/ansibleconfiguration.occie").toString());
		OcciRegistry.getInstance().registerExtension("http://occiware.org/occi/docker#",Paths.get("/org.eclipse.cmf.occi.docker/model/docker.occie").toString());
	}

	@Test
	void createHostNetSshContainer() {
		MachineConnector machineConnector = new MachineConnector();
		machineConnector.setName("LocalhostMachine");
		
		machineConnector.occiCreate();
		
		ContainerConnector containerConnector = new ContainerConnector();
		containerConnector.setName(OpenstackLocalMachineTest.getRandomContainerName());
		containerConnector.setImage(DockerClientManager.DOCKER_SSH_PORTABLE_IMAGE);
		
		containerConnector.setPorts("2235");
//		containerConnector.setNet("host");
		
		ContainsConnector containsConnector = new ContainsConnector();
		containsConnector.occiCreate();
		
		containsConnector.setSource(machineConnector);
		containerConnector.getRlinks().add(containsConnector);
		
		containerConnector.start();

		machineConnector.occiDelete();
		containerConnector.getRlinks().remove(0);
		containerConnector.occiDelete();
	}

}
