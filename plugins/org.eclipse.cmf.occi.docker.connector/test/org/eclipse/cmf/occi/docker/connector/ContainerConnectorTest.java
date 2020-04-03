package org.eclipse.cmf.occi.docker.connector;

import java.nio.file.Paths;
import java.util.Random;

import org.eclipse.cmf.occi.core.AttributeState;
import org.eclipse.cmf.occi.core.OCCIFactory;
import org.eclipse.cmf.occi.core.util.OcciRegistry;
import org.eclipse.cmf.occi.docker.Contains;
import org.eclipse.cmf.occi.docker.DockerFactory;
import org.eclipse.cmf.occi.infrastructure.InfrastructureFactory;
import org.eclipse.cmf.occi.infrastructure.Ipnetworkinterface;
import org.eclipse.cmf.occi.infrastructure.Networkinterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContainerConnectorTest {
	
	static final String IP_ADDRESS = "localhost";
	
	ContainerConnector containerConnector;
	MachineConnector machineConnector;

	@BeforeEach
	public void setUp() throws Exception {
		setUpExtensions();
		containerConnector = new ContainerConnector();
		containerConnector.setName(getRandomContainerName());//muss gesetzt sein
		machineConnector = createMachineConnector();
		
		Contains containsLink = DockerFactory.eINSTANCE.createContains();
		containsLink.setSource(machineConnector);
		containerConnector.getRlinks().add(containsLink);
	}
	
	public void setUpExtensions() {
		OcciRegistry.getInstance().registerExtension("http://occiware.org/occi/docker#",Paths.get("/org.eclipse.cmf.occi.docker/model/docker.occie").toString());
		OcciRegistry.getInstance().registerExtension("http://schemas.ogf.org/occi/infrastructure#",Paths.get("testextensions/Infrastructure.occie").toString());
	}
	
	public MachineConnector createMachineConnector() {
		MachineConnector machineConnector = new MachineConnector();
		machineConnector.setTitle("machine1");
		machineConnector.setName("machine1"); //Muss gesetzt sein , da sonst in preCheckDockerClient des DockerClientManagers Nullponterexception auftritt.
		
		Networkinterface nic = InfrastructureFactory.eINSTANCE.createNetworkinterface();
		Ipnetworkinterface ipNetworkMixinBase = InfrastructureFactory.eINSTANCE.createIpnetworkinterface();
	
		AttributeState ipaddress = OCCIFactory.eINSTANCE.createAttributeState();
		ipaddress.setName("occi.networkinterface.address");
		// we set ip both as AttributeState and member variable, since otherwise we might encounter inconsistencies
		ipaddress.setValue(IP_ADDRESS);
		ipNetworkMixinBase.setOcciNetworkinterfaceAddress(IP_ADDRESS);
		ipNetworkMixinBase.getAttributes().add(ipaddress);
		nic.getParts().add(ipNetworkMixinBase);
		
		machineConnector.getLinks().add(nic);
		
		return machineConnector;
	}

	@Test
	public void testOcciContainerCreation() {
		containerConnector.occiCreate();
		containerConnector.start();
		containerConnector.stop();
	}
	
	private String getRandomContainerName() {
		Random random = new Random();
		return "container" + random.nextInt();
	}

}
