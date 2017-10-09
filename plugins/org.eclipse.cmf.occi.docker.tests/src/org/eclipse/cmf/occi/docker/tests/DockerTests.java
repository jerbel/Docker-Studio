/**
 * Copyright (c) 2015-2017 Obeo, Inria
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 	
 * Contributors:
 * - William Piers <william.piers@obeo.fr>
 * - Philippe Merle <philippe.merle@inria.fr>
 * - Faiez Zalila <faiez.zalila@inria.fr>
 */
package org.eclipse.cmf.occi.docker.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>docker</b></em>' package.
 * <!-- end-user-doc -->
 * @generated
 */
public class DockerTests extends TestSuite {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new DockerTests("docker Tests");
		suite.addTestSuite(ContainerTest.class);
		suite.addTestSuite(LinkTest.class);
		suite.addTestSuite(NetworklinkTest.class);
		suite.addTestSuite(VolumesfromTest.class);
		suite.addTestSuite(ContainsTest.class);
		suite.addTestSuite(MachineTest.class);
		suite.addTestSuite(VolumeTest.class);
		suite.addTestSuite(NetworkTest.class);
		suite.addTestSuite(MachinegenericTest.class);
		suite.addTestSuite(Machineamazonec2Test.class);
		suite.addTestSuite(MachinedigitaloceanTest.class);
		suite.addTestSuite(MachinegooglecomputeengineTest.class);
		suite.addTestSuite(MachineibmsoftlayerTest.class);
		suite.addTestSuite(MachinemicrosoftazureTest.class);
		suite.addTestSuite(MachinemicrosofthypervTest.class);
		suite.addTestSuite(MachineopenstackTest.class);
		suite.addTestSuite(MachinerackspaceTest.class);
		suite.addTestSuite(MachinevirtualboxTest.class);
		suite.addTestSuite(MachinevmwarefusionTest.class);
		suite.addTestSuite(MachinevmwarevcloudairTest.class);
		suite.addTestSuite(MachinevmwarevsphereTest.class);
		suite.addTestSuite(MachineexoscaleTest.class);
		suite.addTestSuite(Machinegrid5000Test.class);
		suite.addTestSuite(ClusterTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DockerTests(String name) {
		super(name);
	}

} //DockerTests
