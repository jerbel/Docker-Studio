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
package org.eclipse.cmf.occi.docker;

import org.eclipse.cmf.occi.infrastructure.Storage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Volume</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Volume Disk
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.cmf.occi.docker.Volume#getDriver <em>Driver</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Volume#getLabels <em>Labels</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Volume#getOptions <em>Options</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Volume#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Volume#getDestination <em>Destination</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Volume#getMode <em>Mode</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Volume#getRw <em>Rw</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Volume#getPropagation <em>Propagation</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Volume#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.cmf.occi.docker.DockerPackage#getVolume()
 * @model
 * @generated
 */
public interface Volume extends Storage {
	/**
	 * Returns the value of the '<em><b>Driver</b></em>' attribute.
	 * The default value is <code>"local"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Driver</em>' attribute.
	 * @see #setDriver(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getVolume_Driver()
	 * @model default="local" dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getDriver();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Volume#getDriver <em>Driver</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Driver</em>' attribute.
	 * @see #getDriver()
	 * @generated
	 */
	void setDriver(String value);

	/**
	 * Returns the value of the '<em><b>Labels</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Labels</em>' attribute.
	 * @see #setLabels(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getVolume_Labels()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getLabels();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Volume#getLabels <em>Labels</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Labels</em>' attribute.
	 * @see #getLabels()
	 * @generated
	 */
	void setLabels(String value);

	/**
	 * Returns the value of the '<em><b>Options</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Options</em>' attribute.
	 * @see #setOptions(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getVolume_Options()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getOptions();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Volume#getOptions <em>Options</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Options</em>' attribute.
	 * @see #getOptions()
	 * @generated
	 */
	void setOptions(String value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Source</em>' attribute.
	 * @see #setSource(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getVolume_Source()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getSource();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Volume#getSource <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' attribute.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(String value);

	/**
	 * Returns the value of the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Destination</em>' attribute.
	 * @see #setDestination(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getVolume_Destination()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getDestination();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Volume#getDestination <em>Destination</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destination</em>' attribute.
	 * @see #getDestination()
	 * @generated
	 */
	void setDestination(String value);

	/**
	 * Returns the value of the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Mode</em>' attribute.
	 * @see #setMode(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getVolume_Mode()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getMode();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Volume#getMode <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mode</em>' attribute.
	 * @see #getMode()
	 * @generated
	 */
	void setMode(String value);

	/**
	 * Returns the value of the '<em><b>Rw</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Rw</em>' attribute.
	 * @see #setRw(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getVolume_Rw()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getRw();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Volume#getRw <em>Rw</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rw</em>' attribute.
	 * @see #getRw()
	 * @generated
	 */
	void setRw(String value);

	/**
	 * Returns the value of the '<em><b>Propagation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Propagation</em>' attribute.
	 * @see #setPropagation(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getVolume_Propagation()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getPropagation();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Volume#getPropagation <em>Propagation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Propagation</em>' attribute.
	 * @see #getPropagation()
	 * @generated
	 */
	void setPropagation(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getVolume_Name()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Volume#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Volume
