package org.eclipse.cmf.occi.docker.connector;

public class MoDContainerConnector extends ContainerConnector {
	@Override
	public void occiCreate() {
		if(hasCompute()) {
			super.occiCreate();
		}
	}
	
	public boolean hasCompute() {
		return getCompute() != null;
	}
}
