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

import junit.textui.TestRunner;

import org.eclipse.cmf.occi.docker.DockerFactory;
import org.eclipse.cmf.occi.docker.Machineibmsoftlayer;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Machineibmsoftlayer</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class MachineibmsoftlayerTest extends MachineTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MachineibmsoftlayerTest.class);
	}

	/**
	 * Constructs a new Machineibmsoftlayer test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MachineibmsoftlayerTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Machineibmsoftlayer test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Machineibmsoftlayer getFixture() {
		return (Machineibmsoftlayer)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(DockerFactory.eINSTANCE.createMachineibmsoftlayer());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //MachineibmsoftlayerTest
