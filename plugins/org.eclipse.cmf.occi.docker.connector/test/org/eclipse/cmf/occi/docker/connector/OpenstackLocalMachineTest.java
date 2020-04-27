package org.eclipse.cmf.occi.docker.connector;

import java.nio.file.Paths;
import java.util.Random;

import org.eclipse.cmf.occi.core.util.OcciRegistry;
import org.eclipse.cmf.occi.docker.Container;
import org.eclipse.cmf.occi.docker.Contains;
import org.eclipse.cmf.occi.docker.Machinevirtualbox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OpenstackLocalMachineTest {
	/**
	 * This name points to a virtualbox machine that should permanently running on the localhost.
	 * This allowes the fast creation of docker occi machine instances without creating a new virtual machine in every test.
	 * Therefore when this name is used creating a machine in a test, the machine must not be deleted.
	 */
	public static final String DEFAULT_MACHINE_NAME = "DefaultJUnitMachine";
	
	@BeforeAll
	public static void setUpExtensions() {
		OcciRegistry.getInstance().registerExtension("http://occiware.org/occi/docker#",Paths.get("/org.eclipse.cmf.occi.docker/model/docker.occie").toString());
		OcciRegistry.getInstance().registerExtension("http://schemas.ogf.org/occi/infrastructure#",Paths.get("testextensions/Infrastructure.occie").toString());
	}
	
	/**
	 * Creates a MachineConnector (using the preexisting default junit virtualbox vm) and a ContainerConnector connects them with a 
	 */
	@Test
	public void createVBMachineTest() {
		MachinevirtualboxConnector machineConnector = new MachinevirtualboxConnector();
		machineConnector.setName(DEFAULT_MACHINE_NAME);
		machineConnector.occiCreate();
		
		ContainerConnector containerConnector = new ContainerConnector();
		try {
			containerConnector.setName(getRandomContainerName());
			
			ContainsConnector containsConnector = new ContainsConnector();
			
			containerConnector.occiCreate();
			
			containsConnector.setSource(machineConnector);
			containerConnector.getRlinks().add(containsConnector);
			
			containerConnector.run();
		} finally {
			containerConnector.occiDelete();
		}
	}
	
	private String getRandomContainerName() {
		Random random = new Random();
		return "container" + random.nextInt();
	}
	
	@Test
	public void createVBMachineWithFactoryTest() {
		ConnectorFactory connectorFactory = new ConnectorFactory();
		
		Machinevirtualbox machinevirtualbox = connectorFactory.createMachinevirtualbox();
		machinevirtualbox.setName(DEFAULT_MACHINE_NAME);
		machinevirtualbox.occiCreate();
		
		Container container = connectorFactory.createContainer();
		
		try {
			container.setName(getRandomContainerName());
			
			container.occiCreate();
			
			Contains contains = connectorFactory.createContains();
			
			contains.setSource(machinevirtualbox);
			container.getRlinks().add(contains);
			
			contains.occiCreate();
			
			container.start();
		} finally {
			container.occiDelete();
		}
	}
	
	@Test
	public void createNewVBMachineTest() {
		MachinevirtualboxConnector machineConnector = new MachinevirtualboxConnector();
		machineConnector.setName("CreateNewVBTestMachine");
		
		try {
			machineConnector.occiCreate();
			
			ContainerConnector containerConnector = new ContainerConnector();
			containerConnector.setName(getRandomContainerName());
			
			ContainsConnector containsConnector = new ContainsConnector();
			
			containerConnector.occiCreate();
			
			
			containsConnector.setSource(machineConnector);
			containerConnector.getRlinks().add(containsConnector);
			
			containerConnector.run();
		} finally {
			machineConnector.occiDelete();
		}
	}
	
	/**
	 * Creates a MachineConnector and a ContainerConnector (not using the preexisting virtual box vm).
	 */
	@Test
	public void localMachineTest() {
		MachineConnector machineConnector = new MachineConnector();
		machineConnector.setName("LocalhostMachine");
		
		machineConnector.occiCreate();
		
		ContainerConnector containerConnector = new ContainerConnector();
		containerConnector.setName(getRandomContainerName());
		
		ContainsConnector containsConnector = new ContainsConnector();
		containsConnector.occiCreate();
		
		containsConnector.setSource(machineConnector);
		containerConnector.getRlinks().add(containsConnector);
		
		containerConnector.start();
//		
		containerConnector.occiDelete();
		machineConnector.occiDelete();
	}
}
