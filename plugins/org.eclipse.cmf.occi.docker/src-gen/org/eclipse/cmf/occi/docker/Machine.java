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

import org.eclipse.cmf.occi.infrastructure.Compute;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Machine</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Machine Resource
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getEngineInstallURL <em>Engine Install URL</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getEngineOpt <em>Engine Opt</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getEngineInsecureRegistry <em>Engine Insecure Registry</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getEngineRegistryMirror <em>Engine Registry Mirror</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getEngineLabel <em>Engine Label</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getEngineStorageDriver <em>Engine Storage Driver</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getEngineEnv <em>Engine Env</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getSwarm <em>Swarm</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getSwarmImage <em>Swarm Image</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getSwarmMaster <em>Swarm Master</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getSwarmDiscovery <em>Swarm Discovery</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getSwarmStrategy <em>Swarm Strategy</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getSwarmOpt <em>Swarm Opt</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getSwarmHost <em>Swarm Host</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getSwarmAddr <em>Swarm Addr</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getSwarmExperimental <em>Swarm Experimental</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machine#getTlsSan <em>Tls San</em>}</li>
 * </ul>
 *
 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine()
 * @model
 * @generated
 */
public interface Machine extends Compute {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_Name()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Engine Install URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Engine Install URL</em>' attribute.
	 * @see #setEngineInstallURL(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_EngineInstallURL()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getEngineInstallURL();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getEngineInstallURL <em>Engine Install URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Engine Install URL</em>' attribute.
	 * @see #getEngineInstallURL()
	 * @generated
	 */
	void setEngineInstallURL(String value);

	/**
	 * Returns the value of the '<em><b>Engine Opt</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Engine Opt</em>' attribute.
	 * @see #setEngineOpt(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_EngineOpt()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getEngineOpt();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getEngineOpt <em>Engine Opt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Engine Opt</em>' attribute.
	 * @see #getEngineOpt()
	 * @generated
	 */
	void setEngineOpt(String value);

	/**
	 * Returns the value of the '<em><b>Engine Insecure Registry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Engine Insecure Registry</em>' attribute.
	 * @see #setEngineInsecureRegistry(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_EngineInsecureRegistry()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getEngineInsecureRegistry();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getEngineInsecureRegistry <em>Engine Insecure Registry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Engine Insecure Registry</em>' attribute.
	 * @see #getEngineInsecureRegistry()
	 * @generated
	 */
	void setEngineInsecureRegistry(String value);

	/**
	 * Returns the value of the '<em><b>Engine Registry Mirror</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Engine Registry Mirror</em>' attribute.
	 * @see #setEngineRegistryMirror(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_EngineRegistryMirror()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getEngineRegistryMirror();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getEngineRegistryMirror <em>Engine Registry Mirror</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Engine Registry Mirror</em>' attribute.
	 * @see #getEngineRegistryMirror()
	 * @generated
	 */
	void setEngineRegistryMirror(String value);

	/**
	 * Returns the value of the '<em><b>Engine Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Engine Label</em>' attribute.
	 * @see #setEngineLabel(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_EngineLabel()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getEngineLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getEngineLabel <em>Engine Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Engine Label</em>' attribute.
	 * @see #getEngineLabel()
	 * @generated
	 */
	void setEngineLabel(String value);

	/**
	 * Returns the value of the '<em><b>Engine Storage Driver</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Engine Storage Driver</em>' attribute.
	 * @see #setEngineStorageDriver(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_EngineStorageDriver()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getEngineStorageDriver();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getEngineStorageDriver <em>Engine Storage Driver</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Engine Storage Driver</em>' attribute.
	 * @see #getEngineStorageDriver()
	 * @generated
	 */
	void setEngineStorageDriver(String value);

	/**
	 * Returns the value of the '<em><b>Engine Env</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Engine Env</em>' attribute.
	 * @see #setEngineEnv(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_EngineEnv()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getEngineEnv();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getEngineEnv <em>Engine Env</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Engine Env</em>' attribute.
	 * @see #getEngineEnv()
	 * @generated
	 */
	void setEngineEnv(String value);

	/**
	 * Returns the value of the '<em><b>Swarm</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Swarm</em>' attribute.
	 * @see #setSwarm(Boolean)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_Swarm()
	 * @model dataType="org.eclipse.cmf.occi.docker.Bool"
	 * @generated
	 */
	Boolean getSwarm();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getSwarm <em>Swarm</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Swarm</em>' attribute.
	 * @see #getSwarm()
	 * @generated
	 */
	void setSwarm(Boolean value);

	/**
	 * Returns the value of the '<em><b>Swarm Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Swarm Image</em>' attribute.
	 * @see #setSwarmImage(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_SwarmImage()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getSwarmImage();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getSwarmImage <em>Swarm Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Swarm Image</em>' attribute.
	 * @see #getSwarmImage()
	 * @generated
	 */
	void setSwarmImage(String value);

	/**
	 * Returns the value of the '<em><b>Swarm Master</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Swarm Master</em>' attribute.
	 * @see #setSwarmMaster(Boolean)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_SwarmMaster()
	 * @model dataType="org.eclipse.cmf.occi.docker.Bool"
	 * @generated
	 */
	Boolean getSwarmMaster();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getSwarmMaster <em>Swarm Master</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Swarm Master</em>' attribute.
	 * @see #getSwarmMaster()
	 * @generated
	 */
	void setSwarmMaster(Boolean value);

	/**
	 * Returns the value of the '<em><b>Swarm Discovery</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Swarm Discovery</em>' attribute.
	 * @see #setSwarmDiscovery(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_SwarmDiscovery()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getSwarmDiscovery();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getSwarmDiscovery <em>Swarm Discovery</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Swarm Discovery</em>' attribute.
	 * @see #getSwarmDiscovery()
	 * @generated
	 */
	void setSwarmDiscovery(String value);

	/**
	 * Returns the value of the '<em><b>Swarm Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Swarm Strategy</em>' attribute.
	 * @see #setSwarmStrategy(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_SwarmStrategy()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getSwarmStrategy();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getSwarmStrategy <em>Swarm Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Swarm Strategy</em>' attribute.
	 * @see #getSwarmStrategy()
	 * @generated
	 */
	void setSwarmStrategy(String value);

	/**
	 * Returns the value of the '<em><b>Swarm Opt</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Swarm Opt</em>' attribute.
	 * @see #setSwarmOpt(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_SwarmOpt()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getSwarmOpt();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getSwarmOpt <em>Swarm Opt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Swarm Opt</em>' attribute.
	 * @see #getSwarmOpt()
	 * @generated
	 */
	void setSwarmOpt(String value);

	/**
	 * Returns the value of the '<em><b>Swarm Host</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Swarm Host</em>' attribute.
	 * @see #setSwarmHost(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_SwarmHost()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getSwarmHost();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getSwarmHost <em>Swarm Host</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Swarm Host</em>' attribute.
	 * @see #getSwarmHost()
	 * @generated
	 */
	void setSwarmHost(String value);

	/**
	 * Returns the value of the '<em><b>Swarm Addr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Swarm Addr</em>' attribute.
	 * @see #setSwarmAddr(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_SwarmAddr()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getSwarmAddr();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getSwarmAddr <em>Swarm Addr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Swarm Addr</em>' attribute.
	 * @see #getSwarmAddr()
	 * @generated
	 */
	void setSwarmAddr(String value);

	/**
	 * Returns the value of the '<em><b>Swarm Experimental</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Swarm Experimental</em>' attribute.
	 * @see #setSwarmExperimental(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_SwarmExperimental()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getSwarmExperimental();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getSwarmExperimental <em>Swarm Experimental</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Swarm Experimental</em>' attribute.
	 * @see #getSwarmExperimental()
	 * @generated
	 */
	void setSwarmExperimental(String value);

	/**
	 * Returns the value of the '<em><b>Tls San</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Tls San</em>' attribute.
	 * @see #setTlsSan(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachine_TlsSan()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getTlsSan();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machine#getTlsSan <em>Tls San</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tls San</em>' attribute.
	 * @see #getTlsSan()
	 * @generated
	 */
	void setTlsSan(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Start all implies this machine AND all containers inside
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	void startall();

} // Machine
