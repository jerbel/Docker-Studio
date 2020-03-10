package org.eclipse.cmf.occi.docker.connector;

import java.nio.file.Paths;

import org.eclipse.cmf.occi.core.AttributeState;
import org.eclipse.cmf.occi.core.OCCIFactory;
import org.eclipse.cmf.occi.core.OCCIPackage;
import org.eclipse.cmf.occi.core.util.OcciRegistry;
import org.eclipse.cmf.occi.docker.Contains;
import org.eclipse.cmf.occi.docker.DockerFactory;
import org.eclipse.cmf.occi.docker.DockerPackage;
import org.eclipse.cmf.occi.docker.Machine;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.eclipse.cmf.occi.infrastructure.InfrastructureFactory;
import org.eclipse.cmf.occi.infrastructure.InfrastructurePackage;
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
		machineConnector = createMachineConnector();
		
		Contains containsLink = DockerFactory.eINSTANCE.createContains();
		containsLink.setSource(machineConnector);
		containerConnector.getRlinks().add(containsLink);
	}
	
	public void setUpExtensions() {
		InfrastructurePackage.eINSTANCE.eClass();
		DockerPackage.eINSTANCE.eClass();
		OCCIPackage.eINSTANCE.eClass();
		OcciRegistry.getInstance().registerExtension("http://occiware.org/occi/docker#",Paths.get("/org.eclipse.cmf.occi.docker/model/docker.occie").toString());
		OcciRegistry.getInstance().registerExtension("http://schemas.ogf.org/occi/infrastructure#",Paths.get("testextensions/Infrastructure.occie").toString());
		OcciRegistry.getInstance().registerExtension("http://schemas.ogf.org/occi/core#",Paths.get("testextensions/Core.occie").toString());
	}
	
	public MachineConnector createMachineConnector() {
//		Machine machine = DockerFactory.eINSTANCE.createMachine();
		MachineConnector machineConnector = new MachineConnector();
		machineConnector.setTitle("machine1");
		
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
	public void testOcciCreate() {
		containerConnector.occiCreate();
	}

}
