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

class ContainerOnVirtualBoxVMTest {
	
	static final String IP_ADDRESS = "localhost";
	
	public void setUpExtensions() {
		OcciRegistry.getInstance().registerExtension("http://occiware.org/occi/docker#",Paths.get("/org.eclipse.cmf.occi.docker/model/docker.occie").toString());
		OcciRegistry.getInstance().registerExtension("http://schemas.ogf.org/occi/infrastructure#",Paths.get("testextensions/Infrastructure.occie").toString());
	}
	
	@BeforeEach
	public void setUp() {
		setUpExtensions();
	}

	@Test
	void test() {
		MachinevirtualboxConnector machineConnector = new MachinevirtualboxConnector();
		machineConnector.setTitle("machine1");
		machineConnector.setName("machine1");
		machineConnector.setSwarm(false);
		machineConnector.setSwarmMaster(false);
		machineConnector.setOcciComputeMemory((float)0.0F); //mit diesem value setzt der machinevirtualboxmanager den wert selbst
		machineConnector.setOcciComputeCores(0); //selbe Situation
		
		Networkinterface nic = InfrastructureFactory.eINSTANCE.createNetworkinterface();
		Ipnetworkinterface ipNetworkMixinBase = InfrastructureFactory.eINSTANCE.createIpnetworkinterface();
		
		AttributeState ipaddress = OCCIFactory.eINSTANCE.createAttributeState();
		ipaddress.setName("occi.networkinterface.address");
		
		ipaddress.setValue(IP_ADDRESS);
		ipNetworkMixinBase.setOcciNetworkinterfaceAddress(IP_ADDRESS);
		ipNetworkMixinBase.getAttributes().add(ipaddress);
		nic.getParts().add(ipNetworkMixinBase);
		
		machineConnector.getLinks().add(nic);
		
		ContainerConnector containerConnector = new ContainerConnector();
		containerConnector.setName(getRandomContainerName());
		
		Contains containsLink = DockerFactory.eINSTANCE.createContains();
		containsLink.setSource(machineConnector);
		containerConnector.getRlinks().add(containsLink);
		
		containerConnector.occiCreate();
		containerConnector.start();
		containerConnector.stop();
	}
	
	private String getRandomContainerName() {
		Random random = new Random();
		return "container" + random.nextInt();
	}

}
