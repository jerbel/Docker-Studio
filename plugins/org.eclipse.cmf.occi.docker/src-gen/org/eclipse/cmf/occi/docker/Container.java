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
 * A representation of the model object '<em><b>Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Container Resource
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getContainerid <em>Containerid</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getImage <em>Image</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getBuild <em>Build</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getCommand <em>Command</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getPorts <em>Ports</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getExpose <em>Expose</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getVolumes <em>Volumes</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getEnvironment <em>Environment</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getEnvFile <em>Env File</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getNet <em>Net</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getDns <em>Dns</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getDnsSearch <em>Dns Search</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getCapAdd <em>Cap Add</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getCapDrop <em>Cap Drop</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getWorkingDir <em>Working Dir</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getEntrypoint <em>Entrypoint</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getUser <em>User</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getDomainName <em>Domain Name</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getMemLimit <em>Mem Limit</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getMemorySwap <em>Memory Swap</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#isPrivileged <em>Privileged</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getRestart <em>Restart</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#isStdinOpen <em>Stdin Open</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#isInteractive <em>Interactive</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getCpuShares <em>Cpu Shares</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getPid <em>Pid</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getIpc <em>Ipc</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getAddHost <em>Add Host</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getMacAddress <em>Mac Address</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#isRm <em>Rm</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getSecurityOpt <em>Security Opt</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getDevice <em>Device</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getLxcConf <em>Lxc Conf</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#isPublishAll <em>Publish All</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#isReadOnly <em>Read Only</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#isMonitored <em>Monitored</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getCpuUsed <em>Cpu Used</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getMemoryUsed <em>Memory Used</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getCpuPercent <em>Cpu Percent</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getMemoryPercent <em>Memory Percent</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getDiskUsed <em>Disk Used</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getDiskPercent <em>Disk Percent</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getBandwidthUsed <em>Bandwidth Used</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getBandwidthPercent <em>Bandwidth Percent</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getMonitoringInterval <em>Monitoring Interval</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getCpuMaxValue <em>Cpu Max Value</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getMemoryMaxValue <em>Memory Max Value</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Container#getCoreMax <em>Core Max</em>}</li>
 * </ul>
 *
 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer()
 * @model
 * @generated
 */
public interface Container extends Compute {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Name()
	 * @model dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Containerid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Containerid</em>' attribute.
	 * @see #setContainerid(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Containerid()
	 * @model dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!containerid'"
	 * @generated
	 */
	String getContainerid();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getContainerid <em>Containerid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Containerid</em>' attribute.
	 * @see #getContainerid()
	 * @generated
	 */
	void setContainerid(String value);

	/**
	 * Returns the value of the '<em><b>Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Image</em>' attribute.
	 * @see #setImage(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Image()
	 * @model dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!image'"
	 * @generated
	 */
	String getImage();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getImage <em>Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Image</em>' attribute.
	 * @see #getImage()
	 * @generated
	 */
	void setImage(String value);

	/**
	 * Returns the value of the '<em><b>Build</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Build</em>' attribute.
	 * @see #setBuild(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Build()
	 * @model dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!build'"
	 * @generated
	 */
	String getBuild();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getBuild <em>Build</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Build</em>' attribute.
	 * @see #getBuild()
	 * @generated
	 */
	void setBuild(String value);

	/**
	 * Returns the value of the '<em><b>Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Command</em>' attribute.
	 * @see #setCommand(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Command()
	 * @model dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!command'"
	 * @generated
	 */
	String getCommand();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getCommand <em>Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Command</em>' attribute.
	 * @see #getCommand()
	 * @generated
	 */
	void setCommand(String value);

	/**
	 * Returns the value of the '<em><b>Ports</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Ports</em>' containment reference.
	 * @see #setPorts(ArrayOfString)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Ports()
	 * @model containment="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!ports'"
	 * @generated
	 */
	ArrayOfString getPorts();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getPorts <em>Ports</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ports</em>' containment reference.
	 * @see #getPorts()
	 * @generated
	 */
	void setPorts(ArrayOfString value);

	/**
	 * Returns the value of the '<em><b>Expose</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Expose</em>' containment reference.
	 * @see #setExpose(ArrayOfString)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Expose()
	 * @model containment="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!expose'"
	 * @generated
	 */
	ArrayOfString getExpose();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getExpose <em>Expose</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expose</em>' containment reference.
	 * @see #getExpose()
	 * @generated
	 */
	void setExpose(ArrayOfString value);

	/**
	 * Returns the value of the '<em><b>Volumes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Volumes</em>' containment reference.
	 * @see #setVolumes(ArrayOfString)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Volumes()
	 * @model containment="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!volumes'"
	 * @generated
	 */
	ArrayOfString getVolumes();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getVolumes <em>Volumes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Volumes</em>' containment reference.
	 * @see #getVolumes()
	 * @generated
	 */
	void setVolumes(ArrayOfString value);

	/**
	 * Returns the value of the '<em><b>Environment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Environment</em>' containment reference.
	 * @see #setEnvironment(ArrayOfString)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Environment()
	 * @model containment="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!environment'"
	 * @generated
	 */
	ArrayOfString getEnvironment();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getEnvironment <em>Environment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Environment</em>' containment reference.
	 * @see #getEnvironment()
	 * @generated
	 */
	void setEnvironment(ArrayOfString value);

	/**
	 * Returns the value of the '<em><b>Env File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Env File</em>' containment reference.
	 * @see #setEnvFile(ArrayOfString)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_EnvFile()
	 * @model containment="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!envFile'"
	 * @generated
	 */
	ArrayOfString getEnvFile();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getEnvFile <em>Env File</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Env File</em>' containment reference.
	 * @see #getEnvFile()
	 * @generated
	 */
	void setEnvFile(ArrayOfString value);

	/**
	 * Returns the value of the '<em><b>Net</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Net</em>' attribute.
	 * @see #setNet(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Net()
	 * @model dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!net'"
	 * @generated
	 */
	String getNet();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getNet <em>Net</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Net</em>' attribute.
	 * @see #getNet()
	 * @generated
	 */
	void setNet(String value);

	/**
	 * Returns the value of the '<em><b>Dns</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Dns</em>' containment reference.
	 * @see #setDns(ArrayOfString)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Dns()
	 * @model containment="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!dns'"
	 * @generated
	 */
	ArrayOfString getDns();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getDns <em>Dns</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dns</em>' containment reference.
	 * @see #getDns()
	 * @generated
	 */
	void setDns(ArrayOfString value);

	/**
	 * Returns the value of the '<em><b>Dns Search</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Dns Search</em>' containment reference.
	 * @see #setDnsSearch(ArrayOfString)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_DnsSearch()
	 * @model containment="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!dnsSearch'"
	 * @generated
	 */
	ArrayOfString getDnsSearch();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getDnsSearch <em>Dns Search</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dns Search</em>' containment reference.
	 * @see #getDnsSearch()
	 * @generated
	 */
	void setDnsSearch(ArrayOfString value);

	/**
	 * Returns the value of the '<em><b>Cap Add</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cap Add</em>' containment reference.
	 * @see #setCapAdd(ArrayOfString)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_CapAdd()
	 * @model containment="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!capAdd'"
	 * @generated
	 */
	ArrayOfString getCapAdd();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getCapAdd <em>Cap Add</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cap Add</em>' containment reference.
	 * @see #getCapAdd()
	 * @generated
	 */
	void setCapAdd(ArrayOfString value);

	/**
	 * Returns the value of the '<em><b>Cap Drop</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cap Drop</em>' containment reference.
	 * @see #setCapDrop(ArrayOfString)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_CapDrop()
	 * @model containment="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!capDrop'"
	 * @generated
	 */
	ArrayOfString getCapDrop();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getCapDrop <em>Cap Drop</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cap Drop</em>' containment reference.
	 * @see #getCapDrop()
	 * @generated
	 */
	void setCapDrop(ArrayOfString value);

	/**
	 * Returns the value of the '<em><b>Working Dir</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Working Dir</em>' attribute.
	 * @see #setWorkingDir(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_WorkingDir()
	 * @model dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!workingDir'"
	 * @generated
	 */
	String getWorkingDir();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getWorkingDir <em>Working Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Working Dir</em>' attribute.
	 * @see #getWorkingDir()
	 * @generated
	 */
	void setWorkingDir(String value);

	/**
	 * Returns the value of the '<em><b>Entrypoint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Entrypoint</em>' attribute.
	 * @see #setEntrypoint(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Entrypoint()
	 * @model dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!entrypoint'"
	 * @generated
	 */
	String getEntrypoint();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getEntrypoint <em>Entrypoint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entrypoint</em>' attribute.
	 * @see #getEntrypoint()
	 * @generated
	 */
	void setEntrypoint(String value);

	/**
	 * Returns the value of the '<em><b>User</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>User</em>' attribute.
	 * @see #setUser(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_User()
	 * @model dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!user'"
	 * @generated
	 */
	String getUser();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getUser <em>User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User</em>' attribute.
	 * @see #getUser()
	 * @generated
	 */
	void setUser(String value);

	/**
	 * Returns the value of the '<em><b>Domain Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Domain Name</em>' attribute.
	 * @see #setDomainName(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_DomainName()
	 * @model dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!domainName'"
	 * @generated
	 */
	String getDomainName();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getDomainName <em>Domain Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Name</em>' attribute.
	 * @see #getDomainName()
	 * @generated
	 */
	void setDomainName(String value);

	/**
	 * Returns the value of the '<em><b>Mem Limit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Mem Limit</em>' attribute.
	 * @see #setMemLimit(Integer)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_MemLimit()
	 * @model dataType="org.eclipse.cmf.occi.core.Integer"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!memLimit'"
	 * @generated
	 */
	Integer getMemLimit();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getMemLimit <em>Mem Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mem Limit</em>' attribute.
	 * @see #getMemLimit()
	 * @generated
	 */
	void setMemLimit(Integer value);

	/**
	 * Returns the value of the '<em><b>Memory Swap</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Memory Swap</em>' attribute.
	 * @see #setMemorySwap(Integer)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_MemorySwap()
	 * @model dataType="org.eclipse.cmf.occi.core.Integer"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!memorySwap'"
	 * @generated
	 */
	Integer getMemorySwap();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getMemorySwap <em>Memory Swap</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Memory Swap</em>' attribute.
	 * @see #getMemorySwap()
	 * @generated
	 */
	void setMemorySwap(Integer value);

	/**
	 * Returns the value of the '<em><b>Privileged</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Privileged</em>' attribute.
	 * @see #setPrivileged(boolean)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Privileged()
	 * @model default="false" dataType="org.eclipse.cmf.occi.core.Boolean"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!privileged'"
	 * @generated
	 */
	boolean isPrivileged();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#isPrivileged <em>Privileged</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Privileged</em>' attribute.
	 * @see #isPrivileged()
	 * @generated
	 */
	void setPrivileged(boolean value);

	/**
	 * Returns the value of the '<em><b>Restart</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Restart</em>' attribute.
	 * @see #setRestart(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Restart()
	 * @model dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!restart'"
	 * @generated
	 */
	String getRestart();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getRestart <em>Restart</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Restart</em>' attribute.
	 * @see #getRestart()
	 * @generated
	 */
	void setRestart(String value);

	/**
	 * Returns the value of the '<em><b>Stdin Open</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Stdin Open</em>' attribute.
	 * @see #setStdinOpen(boolean)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_StdinOpen()
	 * @model default="false" dataType="org.eclipse.cmf.occi.core.Boolean"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!stdinOpen'"
	 * @generated
	 */
	boolean isStdinOpen();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#isStdinOpen <em>Stdin Open</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stdin Open</em>' attribute.
	 * @see #isStdinOpen()
	 * @generated
	 */
	void setStdinOpen(boolean value);

	/**
	 * Returns the value of the '<em><b>Interactive</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Interactive</em>' attribute.
	 * @see #setInteractive(boolean)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Interactive()
	 * @model default="false" dataType="org.eclipse.cmf.occi.core.Boolean"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!interactive'"
	 * @generated
	 */
	boolean isInteractive();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#isInteractive <em>Interactive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interactive</em>' attribute.
	 * @see #isInteractive()
	 * @generated
	 */
	void setInteractive(boolean value);

	/**
	 * Returns the value of the '<em><b>Cpu Shares</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cpu Shares</em>' attribute.
	 * @see #setCpuShares(Integer)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_CpuShares()
	 * @model default="0" dataType="org.eclipse.cmf.occi.core.Integer"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!cpuShares'"
	 * @generated
	 */
	Integer getCpuShares();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getCpuShares <em>Cpu Shares</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cpu Shares</em>' attribute.
	 * @see #getCpuShares()
	 * @generated
	 */
	void setCpuShares(Integer value);

	/**
	 * Returns the value of the '<em><b>Pid</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Pid</em>' attribute.
	 * @see #setPid(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Pid()
	 * @model dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!pid'"
	 * @generated
	 */
	String getPid();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getPid <em>Pid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pid</em>' attribute.
	 * @see #getPid()
	 * @generated
	 */
	void setPid(String value);

	/**
	 * Returns the value of the '<em><b>Ipc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Ipc</em>' attribute.
	 * @see #setIpc(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Ipc()
	 * @model dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!ipc'"
	 * @generated
	 */
	String getIpc();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getIpc <em>Ipc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ipc</em>' attribute.
	 * @see #getIpc()
	 * @generated
	 */
	void setIpc(String value);

	/**
	 * Returns the value of the '<em><b>Add Host</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Add Host</em>' containment reference.
	 * @see #setAddHost(ArrayOfString)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_AddHost()
	 * @model containment="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!addHost'"
	 * @generated
	 */
	ArrayOfString getAddHost();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getAddHost <em>Add Host</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Add Host</em>' containment reference.
	 * @see #getAddHost()
	 * @generated
	 */
	void setAddHost(ArrayOfString value);

	/**
	 * Returns the value of the '<em><b>Mac Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Mac Address</em>' attribute.
	 * @see #setMacAddress(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_MacAddress()
	 * @model dataType="org.eclipse.cmf.occi.infrastructure.Mac"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!macAddress'"
	 * @generated
	 */
	String getMacAddress();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getMacAddress <em>Mac Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mac Address</em>' attribute.
	 * @see #getMacAddress()
	 * @generated
	 */
	void setMacAddress(String value);

	/**
	 * Returns the value of the '<em><b>Rm</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Rm</em>' attribute.
	 * @see #setRm(boolean)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Rm()
	 * @model dataType="org.eclipse.cmf.occi.core.Boolean"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!rm'"
	 * @generated
	 */
	boolean isRm();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#isRm <em>Rm</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rm</em>' attribute.
	 * @see #isRm()
	 * @generated
	 */
	void setRm(boolean value);

	/**
	 * Returns the value of the '<em><b>Security Opt</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Security Opt</em>' containment reference.
	 * @see #setSecurityOpt(ArrayOfString)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_SecurityOpt()
	 * @model containment="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!securityOpt'"
	 * @generated
	 */
	ArrayOfString getSecurityOpt();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getSecurityOpt <em>Security Opt</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Security Opt</em>' containment reference.
	 * @see #getSecurityOpt()
	 * @generated
	 */
	void setSecurityOpt(ArrayOfString value);

	/**
	 * Returns the value of the '<em><b>Device</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Device</em>' containment reference.
	 * @see #setDevice(ArrayOfString)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Device()
	 * @model containment="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!device'"
	 * @generated
	 */
	ArrayOfString getDevice();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getDevice <em>Device</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Device</em>' containment reference.
	 * @see #getDevice()
	 * @generated
	 */
	void setDevice(ArrayOfString value);

	/**
	 * Returns the value of the '<em><b>Lxc Conf</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Lxc Conf</em>' containment reference.
	 * @see #setLxcConf(ArrayOfString)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_LxcConf()
	 * @model containment="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!lxcConf'"
	 * @generated
	 */
	ArrayOfString getLxcConf();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getLxcConf <em>Lxc Conf</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lxc Conf</em>' containment reference.
	 * @see #getLxcConf()
	 * @generated
	 */
	void setLxcConf(ArrayOfString value);

	/**
	 * Returns the value of the '<em><b>Publish All</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Publish All</em>' attribute.
	 * @see #setPublishAll(boolean)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_PublishAll()
	 * @model default="false" dataType="org.eclipse.cmf.occi.core.Boolean"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!publishAll'"
	 * @generated
	 */
	boolean isPublishAll();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#isPublishAll <em>Publish All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Publish All</em>' attribute.
	 * @see #isPublishAll()
	 * @generated
	 */
	void setPublishAll(boolean value);

	/**
	 * Returns the value of the '<em><b>Read Only</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Read Only</em>' attribute.
	 * @see #setReadOnly(boolean)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_ReadOnly()
	 * @model default="false" dataType="org.eclipse.cmf.occi.core.Boolean"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!readOnly'"
	 * @generated
	 */
	boolean isReadOnly();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#isReadOnly <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Read Only</em>' attribute.
	 * @see #isReadOnly()
	 * @generated
	 */
	void setReadOnly(boolean value);

	/**
	 * Returns the value of the '<em><b>Monitored</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Monitored</em>' attribute.
	 * @see #setMonitored(boolean)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_Monitored()
	 * @model default="false" dataType="org.eclipse.cmf.occi.core.Boolean"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!monitored'"
	 * @generated
	 */
	boolean isMonitored();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#isMonitored <em>Monitored</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Monitored</em>' attribute.
	 * @see #isMonitored()
	 * @generated
	 */
	void setMonitored(boolean value);

	/**
	 * Returns the value of the '<em><b>Cpu Used</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cpu Used</em>' attribute.
	 * @see #setCpuUsed(Integer)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_CpuUsed()
	 * @model dataType="org.eclipse.cmf.occi.core.Integer"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!cpuUsed'"
	 * @generated
	 */
	Integer getCpuUsed();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getCpuUsed <em>Cpu Used</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cpu Used</em>' attribute.
	 * @see #getCpuUsed()
	 * @generated
	 */
	void setCpuUsed(Integer value);

	/**
	 * Returns the value of the '<em><b>Memory Used</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Memory Used</em>' attribute.
	 * @see #setMemoryUsed(Integer)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_MemoryUsed()
	 * @model dataType="org.eclipse.cmf.occi.core.Integer"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!memoryUsed'"
	 * @generated
	 */
	Integer getMemoryUsed();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getMemoryUsed <em>Memory Used</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Memory Used</em>' attribute.
	 * @see #getMemoryUsed()
	 * @generated
	 */
	void setMemoryUsed(Integer value);

	/**
	 * Returns the value of the '<em><b>Cpu Percent</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cpu Percent</em>' attribute.
	 * @see #setCpuPercent(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_CpuPercent()
	 * @model default="0" dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!cpuPercent'"
	 * @generated
	 */
	String getCpuPercent();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getCpuPercent <em>Cpu Percent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cpu Percent</em>' attribute.
	 * @see #getCpuPercent()
	 * @generated
	 */
	void setCpuPercent(String value);

	/**
	 * Returns the value of the '<em><b>Memory Percent</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Memory Percent</em>' attribute.
	 * @see #setMemoryPercent(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_MemoryPercent()
	 * @model default="0" dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!memoryPercent'"
	 * @generated
	 */
	String getMemoryPercent();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getMemoryPercent <em>Memory Percent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Memory Percent</em>' attribute.
	 * @see #getMemoryPercent()
	 * @generated
	 */
	void setMemoryPercent(String value);

	/**
	 * Returns the value of the '<em><b>Disk Used</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Disk Used</em>' attribute.
	 * @see #setDiskUsed(Integer)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_DiskUsed()
	 * @model dataType="org.eclipse.cmf.occi.core.Integer"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!diskUsed'"
	 * @generated
	 */
	Integer getDiskUsed();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getDiskUsed <em>Disk Used</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Disk Used</em>' attribute.
	 * @see #getDiskUsed()
	 * @generated
	 */
	void setDiskUsed(Integer value);

	/**
	 * Returns the value of the '<em><b>Disk Percent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Disk Percent</em>' attribute.
	 * @see #setDiskPercent(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_DiskPercent()
	 * @model dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!diskPercent'"
	 * @generated
	 */
	String getDiskPercent();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getDiskPercent <em>Disk Percent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Disk Percent</em>' attribute.
	 * @see #getDiskPercent()
	 * @generated
	 */
	void setDiskPercent(String value);

	/**
	 * Returns the value of the '<em><b>Bandwidth Used</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Bandwidth Used</em>' attribute.
	 * @see #setBandwidthUsed(Integer)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_BandwidthUsed()
	 * @model dataType="org.eclipse.cmf.occi.core.Integer"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!bandwidthUsed'"
	 * @generated
	 */
	Integer getBandwidthUsed();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getBandwidthUsed <em>Bandwidth Used</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bandwidth Used</em>' attribute.
	 * @see #getBandwidthUsed()
	 * @generated
	 */
	void setBandwidthUsed(Integer value);

	/**
	 * Returns the value of the '<em><b>Bandwidth Percent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Bandwidth Percent</em>' attribute.
	 * @see #setBandwidthPercent(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_BandwidthPercent()
	 * @model dataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!bandwidthPercent'"
	 * @generated
	 */
	String getBandwidthPercent();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getBandwidthPercent <em>Bandwidth Percent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bandwidth Percent</em>' attribute.
	 * @see #getBandwidthPercent()
	 * @generated
	 */
	void setBandwidthPercent(String value);

	/**
	 * Returns the value of the '<em><b>Monitoring Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Monitoring Interval</em>' attribute.
	 * @see #setMonitoringInterval(Integer)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_MonitoringInterval()
	 * @model dataType="org.eclipse.cmf.occi.core.Integer"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!monitoringInterval'"
	 * @generated
	 */
	Integer getMonitoringInterval();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getMonitoringInterval <em>Monitoring Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Monitoring Interval</em>' attribute.
	 * @see #getMonitoringInterval()
	 * @generated
	 */
	void setMonitoringInterval(Integer value);

	/**
	 * Returns the value of the '<em><b>Cpu Max Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cpu Max Value</em>' attribute.
	 * @see #setCpuMaxValue(Integer)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_CpuMaxValue()
	 * @model dataType="org.eclipse.cmf.occi.core.Integer"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!cpuMaxValue'"
	 * @generated
	 */
	Integer getCpuMaxValue();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getCpuMaxValue <em>Cpu Max Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cpu Max Value</em>' attribute.
	 * @see #getCpuMaxValue()
	 * @generated
	 */
	void setCpuMaxValue(Integer value);

	/**
	 * Returns the value of the '<em><b>Memory Max Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Memory Max Value</em>' attribute.
	 * @see #setMemoryMaxValue(Integer)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_MemoryMaxValue()
	 * @model dataType="org.eclipse.cmf.occi.core.Integer"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!memoryMaxValue'"
	 * @generated
	 */
	Integer getMemoryMaxValue();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getMemoryMaxValue <em>Memory Max Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Memory Max Value</em>' attribute.
	 * @see #getMemoryMaxValue()
	 * @generated
	 */
	void setMemoryMaxValue(Integer value);

	/**
	 * Returns the value of the '<em><b>Core Max</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Core Max</em>' attribute.
	 * @see #setCoreMax(Integer)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getContainer_CoreMax()
	 * @model default="1" dataType="org.eclipse.cmf.occi.core.Integer"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!coreMax'"
	 * @generated
	 */
	Integer getCoreMax();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Container#getCoreMax <em>Core Max</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Core Max</em>' attribute.
	 * @see #getCoreMax()
	 * @generated
	 */
	void setCoreMax(Integer value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!create()'"
	 * @generated
	 */
	void create();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!stop()'"
	 * @generated
	 */
	void stop();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!run()'"
	 * @generated
	 */
	void run();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!pause()'"
	 * @generated
	 */
	void pause();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!unpause()'"
	 * @generated
	 */
	void unpause();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * @param signal 
	 * <!-- end-model-doc -->
	 * @model signalDataType="org.eclipse.cmf.occi.core.String"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!kill(String)'"
	 * @generated
	 */
	void kill(String signal);

} // Container
