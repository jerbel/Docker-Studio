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

/**
 * Connector EFactory for the OCCI extension: - name: docker - scheme:
 * http://occiware.org/occi/docker#
 */
public class ConnectorFactory extends org.eclipse.cmf.occi.docker.impl.DockerFactoryImpl {
	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: container - title: Container Resource
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Container createContainer() {
		return new ContainerConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: link - title: Link between containers
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Link createLink() {
		return new LinkConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: networklink - title:
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Networklink createNetworklink() {
		return new NetworklinkConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: volumesfrom - title: VolumsFrom
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Volumesfrom createVolumesfrom() {
		return new VolumesfromConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: contains - title:
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Contains createContains() {
		return new ContainsConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: machine - title: Machine Resource
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Machine createMachine() {
		return new MachineConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: volume - title: Volume Disk
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Volume createVolume() {
		return new VolumeConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: network - title: Network resource
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Network createNetwork() {
		return new NetworkConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: machinegeneric - title: Create machines using an existing VM/Host with
	 * SSH
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Machinegeneric createMachinegeneric() {
		return new MachinegenericConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: machineamazonec2 - title:
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Machineamazonec2 createMachineamazonec2() {
		return new Machineamazonec2Connector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: machinedigitalocean - title: Machine on Digital Ocean
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Machinedigitalocean createMachinedigitalocean() {
		return new MachinedigitaloceanConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: machinegooglecomputeengine - title: Machine on Google Compute Engine
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Machinegooglecomputeengine createMachinegooglecomputeengine() {
		return new MachinegooglecomputeengineConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: machineibmsoftlayer - title: Machine on IBM SoftLayer
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Machineibmsoftlayer createMachineibmsoftlayer() {
		return new MachineibmsoftlayerConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: machinemicrosoftazure - title: Machine on Microsoft Azure
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Machinemicrosoftazure createMachinemicrosoftazure() {
		return new MachinemicrosoftazureConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: machinemicrosofthyperv - title: Machine on Microsoft Hyper-V
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Machinemicrosofthyperv createMachinemicrosofthyperv() {
		return new MachinemicrosofthypervConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: machineopenstack - title: Machine on OpenStack
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Machineopenstack createMachineopenstack() {
		return new MachineopenstackConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: machinerackspace - title: Machine on Rackspace cloud
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Machinerackspace createMachinerackspace() {
		return new MachinerackspaceConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: machinevirtualbox - title: Machine on VirtualBox
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Machinevirtualbox createMachinevirtualbox() {
		return new MachinevirtualboxConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: machinevmwarefusion - title: Machine on VMware Fusion
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Machinevmwarefusion createMachinevmwarefusion() {
		return new MachinevmwarefusionConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: machinevmwarevcloudair - title: Machine on VMware vCloud Air
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Machinevmwarevcloudair createMachinevmwarevcloudair() {
		return new MachinevmwarevcloudairConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: machinevmwarevsphere - title: Machine on VMware vSphere
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Machinevmwarevsphere createMachinevmwarevsphere() {
		return new MachinevmwarevsphereConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: machineexoscale - title: Machine Exoscale
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Machineexoscale createMachineexoscale() {
		return new MachineexoscaleConnector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: machinegrid5000 - title: Grid5000
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Machinegrid5000 createMachinegrid5000() {
		return new Machinegrid5000Connector();
	}

	/**
	 * EFactory method for OCCI kind: - scheme: http://occiware.org/occi/docker# -
	 * term: cluster - title: Docker cluster
	 */
	@Override
	public org.eclipse.cmf.occi.docker.Cluster createCluster() {
		return new ClusterConnector();
	}

}
