package org.eclipse.cmf.occi.docker.connector;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Paths;

import org.eclipse.cmf.occi.core.util.OcciRegistry;
import org.eclipse.core.runtime.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContainerConnectorTest {
	
	ContainerConnector connector;

	@BeforeEach
	void setUp() throws Exception {
		OcciRegistry.getInstance().registerExtension("http://occiware.org/occi/docker#",Paths.get("/org.eclipse.cmf.occi.docker/model/docker.occie").toString());
		connector = new ContainerConnector();
	}

	@Test
	void testOcciCreate() {
//		connector.occiCreate();
	}

}
