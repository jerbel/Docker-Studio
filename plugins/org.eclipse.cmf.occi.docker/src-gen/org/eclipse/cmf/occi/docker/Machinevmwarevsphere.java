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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Machinevmwarevsphere</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Machine on VMware vSphere
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getUsername <em>Username</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getPassword <em>Password</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getBoot2dockerURL <em>Boot2docker URL</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getComputeIp <em>Compute Ip</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getCpuCount <em>Cpu Count</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getDatacenter <em>Datacenter</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getDatastore <em>Datastore</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getDiskSize <em>Disk Size</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getMemorySize <em>Memory Size</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getNetwork <em>Network</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getPool <em>Pool</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getVcenter <em>Vcenter</em>}</li>
 * </ul>
 *
 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevmwarevsphere()
 * @model
 * @generated
 */
public interface Machinevmwarevsphere extends Machine {
	/**
	 * Returns the value of the '<em><b>Username</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Username</em>' attribute.
	 * @see #setUsername(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevmwarevsphere_Username()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getUsername();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getUsername <em>Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Username</em>' attribute.
	 * @see #getUsername()
	 * @generated
	 */
	void setUsername(String value);

	/**
	 * Returns the value of the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Password</em>' attribute.
	 * @see #setPassword(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevmwarevsphere_Password()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getPassword();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getPassword <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Password</em>' attribute.
	 * @see #getPassword()
	 * @generated
	 */
	void setPassword(String value);

	/**
	 * Returns the value of the '<em><b>Boot2docker URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Boot2docker URL</em>' attribute.
	 * @see #setBoot2dockerURL(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevmwarevsphere_Boot2dockerURL()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getBoot2dockerURL();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getBoot2dockerURL <em>Boot2docker URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Boot2docker URL</em>' attribute.
	 * @see #getBoot2dockerURL()
	 * @generated
	 */
	void setBoot2dockerURL(String value);

	/**
	 * Returns the value of the '<em><b>Compute Ip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Compute Ip</em>' attribute.
	 * @see #setComputeIp(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevmwarevsphere_ComputeIp()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getComputeIp();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getComputeIp <em>Compute Ip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compute Ip</em>' attribute.
	 * @see #getComputeIp()
	 * @generated
	 */
	void setComputeIp(String value);

	/**
	 * Returns the value of the '<em><b>Cpu Count</b></em>' attribute.
	 * The default value is <code>"2"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cpu Count</em>' attribute.
	 * @see #setCpuCount(Integer)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevmwarevsphere_CpuCount()
	 * @model default="2" dataType="org.eclipse.cmf.occi.docker.Int"
	 * @generated
	 */
	Integer getCpuCount();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getCpuCount <em>Cpu Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cpu Count</em>' attribute.
	 * @see #getCpuCount()
	 * @generated
	 */
	void setCpuCount(Integer value);

	/**
	 * Returns the value of the '<em><b>Datacenter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Datacenter</em>' attribute.
	 * @see #setDatacenter(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevmwarevsphere_Datacenter()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getDatacenter();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getDatacenter <em>Datacenter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Datacenter</em>' attribute.
	 * @see #getDatacenter()
	 * @generated
	 */
	void setDatacenter(String value);

	/**
	 * Returns the value of the '<em><b>Datastore</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Datastore</em>' attribute.
	 * @see #setDatastore(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevmwarevsphere_Datastore()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getDatastore();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getDatastore <em>Datastore</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Datastore</em>' attribute.
	 * @see #getDatastore()
	 * @generated
	 */
	void setDatastore(String value);

	/**
	 * Returns the value of the '<em><b>Disk Size</b></em>' attribute.
	 * The default value is <code>"20000"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Disk Size</em>' attribute.
	 * @see #setDiskSize(Integer)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevmwarevsphere_DiskSize()
	 * @model default="20000" dataType="org.eclipse.cmf.occi.docker.Int"
	 * @generated
	 */
	Integer getDiskSize();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getDiskSize <em>Disk Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Disk Size</em>' attribute.
	 * @see #getDiskSize()
	 * @generated
	 */
	void setDiskSize(Integer value);

	/**
	 * Returns the value of the '<em><b>Memory Size</b></em>' attribute.
	 * The default value is <code>"2048"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Memory Size</em>' attribute.
	 * @see #setMemorySize(Integer)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevmwarevsphere_MemorySize()
	 * @model default="2048" dataType="org.eclipse.cmf.occi.docker.Int"
	 * @generated
	 */
	Integer getMemorySize();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getMemorySize <em>Memory Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Memory Size</em>' attribute.
	 * @see #getMemorySize()
	 * @generated
	 */
	void setMemorySize(Integer value);

	/**
	 * Returns the value of the '<em><b>Network</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Network</em>' attribute.
	 * @see #setNetwork(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevmwarevsphere_Network()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getNetwork();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getNetwork <em>Network</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Network</em>' attribute.
	 * @see #getNetwork()
	 * @generated
	 */
	void setNetwork(String value);

	/**
	 * Returns the value of the '<em><b>Pool</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Pool</em>' attribute.
	 * @see #setPool(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevmwarevsphere_Pool()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getPool();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getPool <em>Pool</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pool</em>' attribute.
	 * @see #getPool()
	 * @generated
	 */
	void setPool(String value);

	/**
	 * Returns the value of the '<em><b>Vcenter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Vcenter</em>' attribute.
	 * @see #setVcenter(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevmwarevsphere_Vcenter()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getVcenter();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevmwarevsphere#getVcenter <em>Vcenter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vcenter</em>' attribute.
	 * @see #getVcenter()
	 * @generated
	 */
	void setVcenter(String value);

} // Machinevmwarevsphere
