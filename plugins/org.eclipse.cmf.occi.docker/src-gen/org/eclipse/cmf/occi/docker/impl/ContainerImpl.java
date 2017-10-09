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
package org.eclipse.cmf.occi.docker.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.cmf.occi.docker.ArrayOfString;
import org.eclipse.cmf.occi.docker.DockerPackage;

import org.eclipse.cmf.occi.infrastructure.impl.ComputeImpl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getContainerid <em>Containerid</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getImage <em>Image</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getBuild <em>Build</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getCommand <em>Command</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getExpose <em>Expose</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getVolumes <em>Volumes</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getEnvironment <em>Environment</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getEnvFile <em>Env File</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getNet <em>Net</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getDns <em>Dns</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getDnsSearch <em>Dns Search</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getCapAdd <em>Cap Add</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getCapDrop <em>Cap Drop</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getWorkingDir <em>Working Dir</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getEntrypoint <em>Entrypoint</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getUser <em>User</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getDomainName <em>Domain Name</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getMemLimit <em>Mem Limit</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getMemorySwap <em>Memory Swap</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#isPrivileged <em>Privileged</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getRestart <em>Restart</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#isStdinOpen <em>Stdin Open</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#isInteractive <em>Interactive</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getCpuShares <em>Cpu Shares</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getPid <em>Pid</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getIpc <em>Ipc</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getAddHost <em>Add Host</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getMacAddress <em>Mac Address</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#isRm <em>Rm</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getSecurityOpt <em>Security Opt</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getDevice <em>Device</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getLxcConf <em>Lxc Conf</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#isPublishAll <em>Publish All</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#isReadOnly <em>Read Only</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#isMonitored <em>Monitored</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getCpuUsed <em>Cpu Used</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getMemoryUsed <em>Memory Used</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getCpuPercent <em>Cpu Percent</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getMemoryPercent <em>Memory Percent</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getDiskUsed <em>Disk Used</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getDiskPercent <em>Disk Percent</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getBandwidthUsed <em>Bandwidth Used</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getBandwidthPercent <em>Bandwidth Percent</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getMonitoringInterval <em>Monitoring Interval</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getCpuMaxValue <em>Cpu Max Value</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getMemoryMaxValue <em>Memory Max Value</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.ContainerImpl#getCoreMax <em>Core Max</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContainerImpl extends ComputeImpl implements org.eclipse.cmf.occi.docker.Container {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getContainerid() <em>Containerid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainerid()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTAINERID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContainerid() <em>Containerid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainerid()
	 * @generated
	 * @ordered
	 */
	protected String containerid = CONTAINERID_EDEFAULT;

	/**
	 * The default value of the '{@link #getImage() <em>Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImage()
	 * @generated
	 * @ordered
	 */
	protected static final String IMAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImage() <em>Image</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImage()
	 * @generated
	 * @ordered
	 */
	protected String image = IMAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getBuild() <em>Build</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuild()
	 * @generated
	 * @ordered
	 */
	protected static final String BUILD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBuild() <em>Build</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuild()
	 * @generated
	 * @ordered
	 */
	protected String build = BUILD_EDEFAULT;

	/**
	 * The default value of the '{@link #getCommand() <em>Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommand()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMAND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCommand() <em>Command</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommand()
	 * @generated
	 * @ordered
	 */
	protected String command = COMMAND_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPorts() <em>Ports</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPorts()
	 * @generated
	 * @ordered
	 */
	protected ArrayOfString ports;

	/**
	 * The cached value of the '{@link #getExpose() <em>Expose</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpose()
	 * @generated
	 * @ordered
	 */
	protected ArrayOfString expose;

	/**
	 * The cached value of the '{@link #getVolumes() <em>Volumes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVolumes()
	 * @generated
	 * @ordered
	 */
	protected ArrayOfString volumes;

	/**
	 * The cached value of the '{@link #getEnvironment() <em>Environment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnvironment()
	 * @generated
	 * @ordered
	 */
	protected ArrayOfString environment;

	/**
	 * The cached value of the '{@link #getEnvFile() <em>Env File</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnvFile()
	 * @generated
	 * @ordered
	 */
	protected ArrayOfString envFile;

	/**
	 * The default value of the '{@link #getNet() <em>Net</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNet()
	 * @generated
	 * @ordered
	 */
	protected static final String NET_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNet() <em>Net</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNet()
	 * @generated
	 * @ordered
	 */
	protected String net = NET_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDns() <em>Dns</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDns()
	 * @generated
	 * @ordered
	 */
	protected ArrayOfString dns;

	/**
	 * The cached value of the '{@link #getDnsSearch() <em>Dns Search</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDnsSearch()
	 * @generated
	 * @ordered
	 */
	protected ArrayOfString dnsSearch;

	/**
	 * The cached value of the '{@link #getCapAdd() <em>Cap Add</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCapAdd()
	 * @generated
	 * @ordered
	 */
	protected ArrayOfString capAdd;

	/**
	 * The cached value of the '{@link #getCapDrop() <em>Cap Drop</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCapDrop()
	 * @generated
	 * @ordered
	 */
	protected ArrayOfString capDrop;

	/**
	 * The default value of the '{@link #getWorkingDir() <em>Working Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWorkingDir()
	 * @generated
	 * @ordered
	 */
	protected static final String WORKING_DIR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWorkingDir() <em>Working Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWorkingDir()
	 * @generated
	 * @ordered
	 */
	protected String workingDir = WORKING_DIR_EDEFAULT;

	/**
	 * The default value of the '{@link #getEntrypoint() <em>Entrypoint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntrypoint()
	 * @generated
	 * @ordered
	 */
	protected static final String ENTRYPOINT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEntrypoint() <em>Entrypoint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntrypoint()
	 * @generated
	 * @ordered
	 */
	protected String entrypoint = ENTRYPOINT_EDEFAULT;

	/**
	 * The default value of the '{@link #getUser() <em>User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUser()
	 * @generated
	 * @ordered
	 */
	protected static final String USER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUser() <em>User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUser()
	 * @generated
	 * @ordered
	 */
	protected String user = USER_EDEFAULT;

	/**
	 * The default value of the '{@link #getDomainName() <em>Domain Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainName()
	 * @generated
	 * @ordered
	 */
	protected static final String DOMAIN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDomainName() <em>Domain Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainName()
	 * @generated
	 * @ordered
	 */
	protected String domainName = DOMAIN_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getMemLimit() <em>Mem Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemLimit()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MEM_LIMIT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMemLimit() <em>Mem Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemLimit()
	 * @generated
	 * @ordered
	 */
	protected Integer memLimit = MEM_LIMIT_EDEFAULT;

	/**
	 * The default value of the '{@link #getMemorySwap() <em>Memory Swap</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemorySwap()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MEMORY_SWAP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMemorySwap() <em>Memory Swap</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemorySwap()
	 * @generated
	 * @ordered
	 */
	protected Integer memorySwap = MEMORY_SWAP_EDEFAULT;

	/**
	 * The default value of the '{@link #isPrivileged() <em>Privileged</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPrivileged()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PRIVILEGED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPrivileged() <em>Privileged</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPrivileged()
	 * @generated
	 * @ordered
	 */
	protected boolean privileged = PRIVILEGED_EDEFAULT;

	/**
	 * The default value of the '{@link #getRestart() <em>Restart</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRestart()
	 * @generated
	 * @ordered
	 */
	protected static final String RESTART_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRestart() <em>Restart</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRestart()
	 * @generated
	 * @ordered
	 */
	protected String restart = RESTART_EDEFAULT;

	/**
	 * The default value of the '{@link #isStdinOpen() <em>Stdin Open</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStdinOpen()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STDIN_OPEN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isStdinOpen() <em>Stdin Open</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStdinOpen()
	 * @generated
	 * @ordered
	 */
	protected boolean stdinOpen = STDIN_OPEN_EDEFAULT;

	/**
	 * The default value of the '{@link #isInteractive() <em>Interactive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInteractive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INTERACTIVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInteractive() <em>Interactive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInteractive()
	 * @generated
	 * @ordered
	 */
	protected boolean interactive = INTERACTIVE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCpuShares() <em>Cpu Shares</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCpuShares()
	 * @generated
	 * @ordered
	 */
	protected static final Integer CPU_SHARES_EDEFAULT = new Integer(0);

	/**
	 * The cached value of the '{@link #getCpuShares() <em>Cpu Shares</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCpuShares()
	 * @generated
	 * @ordered
	 */
	protected Integer cpuShares = CPU_SHARES_EDEFAULT;

	/**
	 * The default value of the '{@link #getPid() <em>Pid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPid()
	 * @generated
	 * @ordered
	 */
	protected static final String PID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPid() <em>Pid</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPid()
	 * @generated
	 * @ordered
	 */
	protected String pid = PID_EDEFAULT;

	/**
	 * The default value of the '{@link #getIpc() <em>Ipc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIpc()
	 * @generated
	 * @ordered
	 */
	protected static final String IPC_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIpc() <em>Ipc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIpc()
	 * @generated
	 * @ordered
	 */
	protected String ipc = IPC_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAddHost() <em>Add Host</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddHost()
	 * @generated
	 * @ordered
	 */
	protected ArrayOfString addHost;

	/**
	 * The default value of the '{@link #getMacAddress() <em>Mac Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMacAddress()
	 * @generated
	 * @ordered
	 */
	protected static final String MAC_ADDRESS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMacAddress() <em>Mac Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMacAddress()
	 * @generated
	 * @ordered
	 */
	protected String macAddress = MAC_ADDRESS_EDEFAULT;

	/**
	 * The default value of the '{@link #isRm() <em>Rm</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRm()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RM_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRm() <em>Rm</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRm()
	 * @generated
	 * @ordered
	 */
	protected boolean rm = RM_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSecurityOpt() <em>Security Opt</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecurityOpt()
	 * @generated
	 * @ordered
	 */
	protected ArrayOfString securityOpt;

	/**
	 * The cached value of the '{@link #getDevice() <em>Device</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDevice()
	 * @generated
	 * @ordered
	 */
	protected ArrayOfString device;

	/**
	 * The cached value of the '{@link #getLxcConf() <em>Lxc Conf</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLxcConf()
	 * @generated
	 * @ordered
	 */
	protected ArrayOfString lxcConf;

	/**
	 * The default value of the '{@link #isPublishAll() <em>Publish All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPublishAll()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PUBLISH_ALL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPublishAll() <em>Publish All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPublishAll()
	 * @generated
	 * @ordered
	 */
	protected boolean publishAll = PUBLISH_ALL_EDEFAULT;

	/**
	 * The default value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadOnly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean READ_ONLY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadOnly()
	 * @generated
	 * @ordered
	 */
	protected boolean readOnly = READ_ONLY_EDEFAULT;

	/**
	 * The default value of the '{@link #isMonitored() <em>Monitored</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMonitored()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MONITORED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMonitored() <em>Monitored</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMonitored()
	 * @generated
	 * @ordered
	 */
	protected boolean monitored = MONITORED_EDEFAULT;

	/**
	 * The default value of the '{@link #getCpuUsed() <em>Cpu Used</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCpuUsed()
	 * @generated
	 * @ordered
	 */
	protected static final Integer CPU_USED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCpuUsed() <em>Cpu Used</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCpuUsed()
	 * @generated
	 * @ordered
	 */
	protected Integer cpuUsed = CPU_USED_EDEFAULT;

	/**
	 * The default value of the '{@link #getMemoryUsed() <em>Memory Used</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemoryUsed()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MEMORY_USED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMemoryUsed() <em>Memory Used</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemoryUsed()
	 * @generated
	 * @ordered
	 */
	protected Integer memoryUsed = MEMORY_USED_EDEFAULT;

	/**
	 * The default value of the '{@link #getCpuPercent() <em>Cpu Percent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCpuPercent()
	 * @generated
	 * @ordered
	 */
	protected static final String CPU_PERCENT_EDEFAULT = "0";

	/**
	 * The cached value of the '{@link #getCpuPercent() <em>Cpu Percent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCpuPercent()
	 * @generated
	 * @ordered
	 */
	protected String cpuPercent = CPU_PERCENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getMemoryPercent() <em>Memory Percent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemoryPercent()
	 * @generated
	 * @ordered
	 */
	protected static final String MEMORY_PERCENT_EDEFAULT = "0";

	/**
	 * The cached value of the '{@link #getMemoryPercent() <em>Memory Percent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemoryPercent()
	 * @generated
	 * @ordered
	 */
	protected String memoryPercent = MEMORY_PERCENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getDiskUsed() <em>Disk Used</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiskUsed()
	 * @generated
	 * @ordered
	 */
	protected static final Integer DISK_USED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDiskUsed() <em>Disk Used</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiskUsed()
	 * @generated
	 * @ordered
	 */
	protected Integer diskUsed = DISK_USED_EDEFAULT;

	/**
	 * The default value of the '{@link #getDiskPercent() <em>Disk Percent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiskPercent()
	 * @generated
	 * @ordered
	 */
	protected static final String DISK_PERCENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDiskPercent() <em>Disk Percent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiskPercent()
	 * @generated
	 * @ordered
	 */
	protected String diskPercent = DISK_PERCENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getBandwidthUsed() <em>Bandwidth Used</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBandwidthUsed()
	 * @generated
	 * @ordered
	 */
	protected static final Integer BANDWIDTH_USED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBandwidthUsed() <em>Bandwidth Used</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBandwidthUsed()
	 * @generated
	 * @ordered
	 */
	protected Integer bandwidthUsed = BANDWIDTH_USED_EDEFAULT;

	/**
	 * The default value of the '{@link #getBandwidthPercent() <em>Bandwidth Percent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBandwidthPercent()
	 * @generated
	 * @ordered
	 */
	protected static final String BANDWIDTH_PERCENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBandwidthPercent() <em>Bandwidth Percent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBandwidthPercent()
	 * @generated
	 * @ordered
	 */
	protected String bandwidthPercent = BANDWIDTH_PERCENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getMonitoringInterval() <em>Monitoring Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMonitoringInterval()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MONITORING_INTERVAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMonitoringInterval() <em>Monitoring Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMonitoringInterval()
	 * @generated
	 * @ordered
	 */
	protected Integer monitoringInterval = MONITORING_INTERVAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getCpuMaxValue() <em>Cpu Max Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCpuMaxValue()
	 * @generated
	 * @ordered
	 */
	protected static final Integer CPU_MAX_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCpuMaxValue() <em>Cpu Max Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCpuMaxValue()
	 * @generated
	 * @ordered
	 */
	protected Integer cpuMaxValue = CPU_MAX_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getMemoryMaxValue() <em>Memory Max Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemoryMaxValue()
	 * @generated
	 * @ordered
	 */
	protected static final Integer MEMORY_MAX_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMemoryMaxValue() <em>Memory Max Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemoryMaxValue()
	 * @generated
	 * @ordered
	 */
	protected Integer memoryMaxValue = MEMORY_MAX_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCoreMax() <em>Core Max</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoreMax()
	 * @generated
	 * @ordered
	 */
	protected static final Integer CORE_MAX_EDEFAULT = new Integer(1);

	/**
	 * The cached value of the '{@link #getCoreMax() <em>Core Max</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoreMax()
	 * @generated
	 * @ordered
	 */
	protected Integer coreMax = CORE_MAX_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DockerPackage.Literals.CONTAINER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getContainerid() {
		return containerid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContainerid(String newContainerid) {
		String oldContainerid = containerid;
		containerid = newContainerid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__CONTAINERID, oldContainerid, containerid));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImage() {
		return image;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImage(String newImage) {
		String oldImage = image;
		image = newImage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__IMAGE, oldImage, image));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBuild() {
		return build;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBuild(String newBuild) {
		String oldBuild = build;
		build = newBuild;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__BUILD, oldBuild, build));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommand(String newCommand) {
		String oldCommand = command;
		command = newCommand;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__COMMAND, oldCommand, command));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayOfString getPorts() {
		return ports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPorts(ArrayOfString newPorts, NotificationChain msgs) {
		ArrayOfString oldPorts = ports;
		ports = newPorts;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__PORTS, oldPorts, newPorts);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPorts(ArrayOfString newPorts) {
		if (newPorts != ports) {
			NotificationChain msgs = null;
			if (ports != null)
				msgs = ((InternalEObject)ports).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__PORTS, null, msgs);
			if (newPorts != null)
				msgs = ((InternalEObject)newPorts).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__PORTS, null, msgs);
			msgs = basicSetPorts(newPorts, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__PORTS, newPorts, newPorts));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayOfString getExpose() {
		return expose;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpose(ArrayOfString newExpose, NotificationChain msgs) {
		ArrayOfString oldExpose = expose;
		expose = newExpose;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__EXPOSE, oldExpose, newExpose);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpose(ArrayOfString newExpose) {
		if (newExpose != expose) {
			NotificationChain msgs = null;
			if (expose != null)
				msgs = ((InternalEObject)expose).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__EXPOSE, null, msgs);
			if (newExpose != null)
				msgs = ((InternalEObject)newExpose).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__EXPOSE, null, msgs);
			msgs = basicSetExpose(newExpose, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__EXPOSE, newExpose, newExpose));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayOfString getVolumes() {
		return volumes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVolumes(ArrayOfString newVolumes, NotificationChain msgs) {
		ArrayOfString oldVolumes = volumes;
		volumes = newVolumes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__VOLUMES, oldVolumes, newVolumes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVolumes(ArrayOfString newVolumes) {
		if (newVolumes != volumes) {
			NotificationChain msgs = null;
			if (volumes != null)
				msgs = ((InternalEObject)volumes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__VOLUMES, null, msgs);
			if (newVolumes != null)
				msgs = ((InternalEObject)newVolumes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__VOLUMES, null, msgs);
			msgs = basicSetVolumes(newVolumes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__VOLUMES, newVolumes, newVolumes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayOfString getEnvironment() {
		return environment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnvironment(ArrayOfString newEnvironment, NotificationChain msgs) {
		ArrayOfString oldEnvironment = environment;
		environment = newEnvironment;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__ENVIRONMENT, oldEnvironment, newEnvironment);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnvironment(ArrayOfString newEnvironment) {
		if (newEnvironment != environment) {
			NotificationChain msgs = null;
			if (environment != null)
				msgs = ((InternalEObject)environment).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__ENVIRONMENT, null, msgs);
			if (newEnvironment != null)
				msgs = ((InternalEObject)newEnvironment).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__ENVIRONMENT, null, msgs);
			msgs = basicSetEnvironment(newEnvironment, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__ENVIRONMENT, newEnvironment, newEnvironment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayOfString getEnvFile() {
		return envFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnvFile(ArrayOfString newEnvFile, NotificationChain msgs) {
		ArrayOfString oldEnvFile = envFile;
		envFile = newEnvFile;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__ENV_FILE, oldEnvFile, newEnvFile);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnvFile(ArrayOfString newEnvFile) {
		if (newEnvFile != envFile) {
			NotificationChain msgs = null;
			if (envFile != null)
				msgs = ((InternalEObject)envFile).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__ENV_FILE, null, msgs);
			if (newEnvFile != null)
				msgs = ((InternalEObject)newEnvFile).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__ENV_FILE, null, msgs);
			msgs = basicSetEnvFile(newEnvFile, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__ENV_FILE, newEnvFile, newEnvFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNet() {
		return net;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNet(String newNet) {
		String oldNet = net;
		net = newNet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__NET, oldNet, net));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayOfString getDns() {
		return dns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDns(ArrayOfString newDns, NotificationChain msgs) {
		ArrayOfString oldDns = dns;
		dns = newDns;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__DNS, oldDns, newDns);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDns(ArrayOfString newDns) {
		if (newDns != dns) {
			NotificationChain msgs = null;
			if (dns != null)
				msgs = ((InternalEObject)dns).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__DNS, null, msgs);
			if (newDns != null)
				msgs = ((InternalEObject)newDns).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__DNS, null, msgs);
			msgs = basicSetDns(newDns, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__DNS, newDns, newDns));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayOfString getDnsSearch() {
		return dnsSearch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDnsSearch(ArrayOfString newDnsSearch, NotificationChain msgs) {
		ArrayOfString oldDnsSearch = dnsSearch;
		dnsSearch = newDnsSearch;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__DNS_SEARCH, oldDnsSearch, newDnsSearch);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDnsSearch(ArrayOfString newDnsSearch) {
		if (newDnsSearch != dnsSearch) {
			NotificationChain msgs = null;
			if (dnsSearch != null)
				msgs = ((InternalEObject)dnsSearch).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__DNS_SEARCH, null, msgs);
			if (newDnsSearch != null)
				msgs = ((InternalEObject)newDnsSearch).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__DNS_SEARCH, null, msgs);
			msgs = basicSetDnsSearch(newDnsSearch, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__DNS_SEARCH, newDnsSearch, newDnsSearch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayOfString getCapAdd() {
		return capAdd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCapAdd(ArrayOfString newCapAdd, NotificationChain msgs) {
		ArrayOfString oldCapAdd = capAdd;
		capAdd = newCapAdd;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__CAP_ADD, oldCapAdd, newCapAdd);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCapAdd(ArrayOfString newCapAdd) {
		if (newCapAdd != capAdd) {
			NotificationChain msgs = null;
			if (capAdd != null)
				msgs = ((InternalEObject)capAdd).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__CAP_ADD, null, msgs);
			if (newCapAdd != null)
				msgs = ((InternalEObject)newCapAdd).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__CAP_ADD, null, msgs);
			msgs = basicSetCapAdd(newCapAdd, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__CAP_ADD, newCapAdd, newCapAdd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayOfString getCapDrop() {
		return capDrop;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCapDrop(ArrayOfString newCapDrop, NotificationChain msgs) {
		ArrayOfString oldCapDrop = capDrop;
		capDrop = newCapDrop;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__CAP_DROP, oldCapDrop, newCapDrop);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCapDrop(ArrayOfString newCapDrop) {
		if (newCapDrop != capDrop) {
			NotificationChain msgs = null;
			if (capDrop != null)
				msgs = ((InternalEObject)capDrop).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__CAP_DROP, null, msgs);
			if (newCapDrop != null)
				msgs = ((InternalEObject)newCapDrop).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__CAP_DROP, null, msgs);
			msgs = basicSetCapDrop(newCapDrop, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__CAP_DROP, newCapDrop, newCapDrop));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWorkingDir() {
		return workingDir;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWorkingDir(String newWorkingDir) {
		String oldWorkingDir = workingDir;
		workingDir = newWorkingDir;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__WORKING_DIR, oldWorkingDir, workingDir));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEntrypoint() {
		return entrypoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntrypoint(String newEntrypoint) {
		String oldEntrypoint = entrypoint;
		entrypoint = newEntrypoint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__ENTRYPOINT, oldEntrypoint, entrypoint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUser() {
		return user;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUser(String newUser) {
		String oldUser = user;
		user = newUser;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__USER, oldUser, user));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDomainName() {
		return domainName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomainName(String newDomainName) {
		String oldDomainName = domainName;
		domainName = newDomainName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__DOMAIN_NAME, oldDomainName, domainName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getMemLimit() {
		return memLimit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemLimit(Integer newMemLimit) {
		Integer oldMemLimit = memLimit;
		memLimit = newMemLimit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__MEM_LIMIT, oldMemLimit, memLimit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getMemorySwap() {
		return memorySwap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemorySwap(Integer newMemorySwap) {
		Integer oldMemorySwap = memorySwap;
		memorySwap = newMemorySwap;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__MEMORY_SWAP, oldMemorySwap, memorySwap));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPrivileged() {
		return privileged;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrivileged(boolean newPrivileged) {
		boolean oldPrivileged = privileged;
		privileged = newPrivileged;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__PRIVILEGED, oldPrivileged, privileged));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRestart() {
		return restart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRestart(String newRestart) {
		String oldRestart = restart;
		restart = newRestart;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__RESTART, oldRestart, restart));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStdinOpen() {
		return stdinOpen;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStdinOpen(boolean newStdinOpen) {
		boolean oldStdinOpen = stdinOpen;
		stdinOpen = newStdinOpen;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__STDIN_OPEN, oldStdinOpen, stdinOpen));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInteractive() {
		return interactive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInteractive(boolean newInteractive) {
		boolean oldInteractive = interactive;
		interactive = newInteractive;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__INTERACTIVE, oldInteractive, interactive));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getCpuShares() {
		return cpuShares;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCpuShares(Integer newCpuShares) {
		Integer oldCpuShares = cpuShares;
		cpuShares = newCpuShares;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__CPU_SHARES, oldCpuShares, cpuShares));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPid(String newPid) {
		String oldPid = pid;
		pid = newPid;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__PID, oldPid, pid));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIpc() {
		return ipc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIpc(String newIpc) {
		String oldIpc = ipc;
		ipc = newIpc;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__IPC, oldIpc, ipc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayOfString getAddHost() {
		return addHost;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAddHost(ArrayOfString newAddHost, NotificationChain msgs) {
		ArrayOfString oldAddHost = addHost;
		addHost = newAddHost;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__ADD_HOST, oldAddHost, newAddHost);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddHost(ArrayOfString newAddHost) {
		if (newAddHost != addHost) {
			NotificationChain msgs = null;
			if (addHost != null)
				msgs = ((InternalEObject)addHost).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__ADD_HOST, null, msgs);
			if (newAddHost != null)
				msgs = ((InternalEObject)newAddHost).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__ADD_HOST, null, msgs);
			msgs = basicSetAddHost(newAddHost, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__ADD_HOST, newAddHost, newAddHost));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMacAddress() {
		return macAddress;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMacAddress(String newMacAddress) {
		String oldMacAddress = macAddress;
		macAddress = newMacAddress;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__MAC_ADDRESS, oldMacAddress, macAddress));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRm() {
		return rm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRm(boolean newRm) {
		boolean oldRm = rm;
		rm = newRm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__RM, oldRm, rm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayOfString getSecurityOpt() {
		return securityOpt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSecurityOpt(ArrayOfString newSecurityOpt, NotificationChain msgs) {
		ArrayOfString oldSecurityOpt = securityOpt;
		securityOpt = newSecurityOpt;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__SECURITY_OPT, oldSecurityOpt, newSecurityOpt);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSecurityOpt(ArrayOfString newSecurityOpt) {
		if (newSecurityOpt != securityOpt) {
			NotificationChain msgs = null;
			if (securityOpt != null)
				msgs = ((InternalEObject)securityOpt).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__SECURITY_OPT, null, msgs);
			if (newSecurityOpt != null)
				msgs = ((InternalEObject)newSecurityOpt).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__SECURITY_OPT, null, msgs);
			msgs = basicSetSecurityOpt(newSecurityOpt, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__SECURITY_OPT, newSecurityOpt, newSecurityOpt));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayOfString getDevice() {
		return device;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDevice(ArrayOfString newDevice, NotificationChain msgs) {
		ArrayOfString oldDevice = device;
		device = newDevice;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__DEVICE, oldDevice, newDevice);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDevice(ArrayOfString newDevice) {
		if (newDevice != device) {
			NotificationChain msgs = null;
			if (device != null)
				msgs = ((InternalEObject)device).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__DEVICE, null, msgs);
			if (newDevice != null)
				msgs = ((InternalEObject)newDevice).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__DEVICE, null, msgs);
			msgs = basicSetDevice(newDevice, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__DEVICE, newDevice, newDevice));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayOfString getLxcConf() {
		return lxcConf;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLxcConf(ArrayOfString newLxcConf, NotificationChain msgs) {
		ArrayOfString oldLxcConf = lxcConf;
		lxcConf = newLxcConf;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__LXC_CONF, oldLxcConf, newLxcConf);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLxcConf(ArrayOfString newLxcConf) {
		if (newLxcConf != lxcConf) {
			NotificationChain msgs = null;
			if (lxcConf != null)
				msgs = ((InternalEObject)lxcConf).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__LXC_CONF, null, msgs);
			if (newLxcConf != null)
				msgs = ((InternalEObject)newLxcConf).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DockerPackage.CONTAINER__LXC_CONF, null, msgs);
			msgs = basicSetLxcConf(newLxcConf, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__LXC_CONF, newLxcConf, newLxcConf));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPublishAll() {
		return publishAll;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPublishAll(boolean newPublishAll) {
		boolean oldPublishAll = publishAll;
		publishAll = newPublishAll;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__PUBLISH_ALL, oldPublishAll, publishAll));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReadOnly(boolean newReadOnly) {
		boolean oldReadOnly = readOnly;
		readOnly = newReadOnly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__READ_ONLY, oldReadOnly, readOnly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMonitored() {
		return monitored;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMonitored(boolean newMonitored) {
		boolean oldMonitored = monitored;
		monitored = newMonitored;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__MONITORED, oldMonitored, monitored));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getCpuUsed() {
		return cpuUsed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCpuUsed(Integer newCpuUsed) {
		Integer oldCpuUsed = cpuUsed;
		cpuUsed = newCpuUsed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__CPU_USED, oldCpuUsed, cpuUsed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getMemoryUsed() {
		return memoryUsed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemoryUsed(Integer newMemoryUsed) {
		Integer oldMemoryUsed = memoryUsed;
		memoryUsed = newMemoryUsed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__MEMORY_USED, oldMemoryUsed, memoryUsed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCpuPercent() {
		return cpuPercent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCpuPercent(String newCpuPercent) {
		String oldCpuPercent = cpuPercent;
		cpuPercent = newCpuPercent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__CPU_PERCENT, oldCpuPercent, cpuPercent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMemoryPercent() {
		return memoryPercent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemoryPercent(String newMemoryPercent) {
		String oldMemoryPercent = memoryPercent;
		memoryPercent = newMemoryPercent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__MEMORY_PERCENT, oldMemoryPercent, memoryPercent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getDiskUsed() {
		return diskUsed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDiskUsed(Integer newDiskUsed) {
		Integer oldDiskUsed = diskUsed;
		diskUsed = newDiskUsed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__DISK_USED, oldDiskUsed, diskUsed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDiskPercent() {
		return diskPercent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDiskPercent(String newDiskPercent) {
		String oldDiskPercent = diskPercent;
		diskPercent = newDiskPercent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__DISK_PERCENT, oldDiskPercent, diskPercent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getBandwidthUsed() {
		return bandwidthUsed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBandwidthUsed(Integer newBandwidthUsed) {
		Integer oldBandwidthUsed = bandwidthUsed;
		bandwidthUsed = newBandwidthUsed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__BANDWIDTH_USED, oldBandwidthUsed, bandwidthUsed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBandwidthPercent() {
		return bandwidthPercent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBandwidthPercent(String newBandwidthPercent) {
		String oldBandwidthPercent = bandwidthPercent;
		bandwidthPercent = newBandwidthPercent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__BANDWIDTH_PERCENT, oldBandwidthPercent, bandwidthPercent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getMonitoringInterval() {
		return monitoringInterval;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMonitoringInterval(Integer newMonitoringInterval) {
		Integer oldMonitoringInterval = monitoringInterval;
		monitoringInterval = newMonitoringInterval;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__MONITORING_INTERVAL, oldMonitoringInterval, monitoringInterval));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getCpuMaxValue() {
		return cpuMaxValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCpuMaxValue(Integer newCpuMaxValue) {
		Integer oldCpuMaxValue = cpuMaxValue;
		cpuMaxValue = newCpuMaxValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__CPU_MAX_VALUE, oldCpuMaxValue, cpuMaxValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getMemoryMaxValue() {
		return memoryMaxValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemoryMaxValue(Integer newMemoryMaxValue) {
		Integer oldMemoryMaxValue = memoryMaxValue;
		memoryMaxValue = newMemoryMaxValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__MEMORY_MAX_VALUE, oldMemoryMaxValue, memoryMaxValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Integer getCoreMax() {
		return coreMax;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCoreMax(Integer newCoreMax) {
		Integer oldCoreMax = coreMax;
		coreMax = newCoreMax;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.CONTAINER__CORE_MAX, oldCoreMax, coreMax));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void create() {
		throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!create()
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void stop() {
		throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!stop()
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void run() {
		throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!run()
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void pause() {
		throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!pause()
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unpause() {
		throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!unpause()
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void kill(final String signal) {
		throw new UnsupportedOperationException();  // FIXME Unimplemented http://occiware.org/occi/docker/ecore!Container!kill(String)
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DockerPackage.CONTAINER__PORTS:
				return basicSetPorts(null, msgs);
			case DockerPackage.CONTAINER__EXPOSE:
				return basicSetExpose(null, msgs);
			case DockerPackage.CONTAINER__VOLUMES:
				return basicSetVolumes(null, msgs);
			case DockerPackage.CONTAINER__ENVIRONMENT:
				return basicSetEnvironment(null, msgs);
			case DockerPackage.CONTAINER__ENV_FILE:
				return basicSetEnvFile(null, msgs);
			case DockerPackage.CONTAINER__DNS:
				return basicSetDns(null, msgs);
			case DockerPackage.CONTAINER__DNS_SEARCH:
				return basicSetDnsSearch(null, msgs);
			case DockerPackage.CONTAINER__CAP_ADD:
				return basicSetCapAdd(null, msgs);
			case DockerPackage.CONTAINER__CAP_DROP:
				return basicSetCapDrop(null, msgs);
			case DockerPackage.CONTAINER__ADD_HOST:
				return basicSetAddHost(null, msgs);
			case DockerPackage.CONTAINER__SECURITY_OPT:
				return basicSetSecurityOpt(null, msgs);
			case DockerPackage.CONTAINER__DEVICE:
				return basicSetDevice(null, msgs);
			case DockerPackage.CONTAINER__LXC_CONF:
				return basicSetLxcConf(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DockerPackage.CONTAINER__NAME:
				return getName();
			case DockerPackage.CONTAINER__CONTAINERID:
				return getContainerid();
			case DockerPackage.CONTAINER__IMAGE:
				return getImage();
			case DockerPackage.CONTAINER__BUILD:
				return getBuild();
			case DockerPackage.CONTAINER__COMMAND:
				return getCommand();
			case DockerPackage.CONTAINER__PORTS:
				return getPorts();
			case DockerPackage.CONTAINER__EXPOSE:
				return getExpose();
			case DockerPackage.CONTAINER__VOLUMES:
				return getVolumes();
			case DockerPackage.CONTAINER__ENVIRONMENT:
				return getEnvironment();
			case DockerPackage.CONTAINER__ENV_FILE:
				return getEnvFile();
			case DockerPackage.CONTAINER__NET:
				return getNet();
			case DockerPackage.CONTAINER__DNS:
				return getDns();
			case DockerPackage.CONTAINER__DNS_SEARCH:
				return getDnsSearch();
			case DockerPackage.CONTAINER__CAP_ADD:
				return getCapAdd();
			case DockerPackage.CONTAINER__CAP_DROP:
				return getCapDrop();
			case DockerPackage.CONTAINER__WORKING_DIR:
				return getWorkingDir();
			case DockerPackage.CONTAINER__ENTRYPOINT:
				return getEntrypoint();
			case DockerPackage.CONTAINER__USER:
				return getUser();
			case DockerPackage.CONTAINER__DOMAIN_NAME:
				return getDomainName();
			case DockerPackage.CONTAINER__MEM_LIMIT:
				return getMemLimit();
			case DockerPackage.CONTAINER__MEMORY_SWAP:
				return getMemorySwap();
			case DockerPackage.CONTAINER__PRIVILEGED:
				return isPrivileged();
			case DockerPackage.CONTAINER__RESTART:
				return getRestart();
			case DockerPackage.CONTAINER__STDIN_OPEN:
				return isStdinOpen();
			case DockerPackage.CONTAINER__INTERACTIVE:
				return isInteractive();
			case DockerPackage.CONTAINER__CPU_SHARES:
				return getCpuShares();
			case DockerPackage.CONTAINER__PID:
				return getPid();
			case DockerPackage.CONTAINER__IPC:
				return getIpc();
			case DockerPackage.CONTAINER__ADD_HOST:
				return getAddHost();
			case DockerPackage.CONTAINER__MAC_ADDRESS:
				return getMacAddress();
			case DockerPackage.CONTAINER__RM:
				return isRm();
			case DockerPackage.CONTAINER__SECURITY_OPT:
				return getSecurityOpt();
			case DockerPackage.CONTAINER__DEVICE:
				return getDevice();
			case DockerPackage.CONTAINER__LXC_CONF:
				return getLxcConf();
			case DockerPackage.CONTAINER__PUBLISH_ALL:
				return isPublishAll();
			case DockerPackage.CONTAINER__READ_ONLY:
				return isReadOnly();
			case DockerPackage.CONTAINER__MONITORED:
				return isMonitored();
			case DockerPackage.CONTAINER__CPU_USED:
				return getCpuUsed();
			case DockerPackage.CONTAINER__MEMORY_USED:
				return getMemoryUsed();
			case DockerPackage.CONTAINER__CPU_PERCENT:
				return getCpuPercent();
			case DockerPackage.CONTAINER__MEMORY_PERCENT:
				return getMemoryPercent();
			case DockerPackage.CONTAINER__DISK_USED:
				return getDiskUsed();
			case DockerPackage.CONTAINER__DISK_PERCENT:
				return getDiskPercent();
			case DockerPackage.CONTAINER__BANDWIDTH_USED:
				return getBandwidthUsed();
			case DockerPackage.CONTAINER__BANDWIDTH_PERCENT:
				return getBandwidthPercent();
			case DockerPackage.CONTAINER__MONITORING_INTERVAL:
				return getMonitoringInterval();
			case DockerPackage.CONTAINER__CPU_MAX_VALUE:
				return getCpuMaxValue();
			case DockerPackage.CONTAINER__MEMORY_MAX_VALUE:
				return getMemoryMaxValue();
			case DockerPackage.CONTAINER__CORE_MAX:
				return getCoreMax();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DockerPackage.CONTAINER__NAME:
				setName((String)newValue);
				return;
			case DockerPackage.CONTAINER__CONTAINERID:
				setContainerid((String)newValue);
				return;
			case DockerPackage.CONTAINER__IMAGE:
				setImage((String)newValue);
				return;
			case DockerPackage.CONTAINER__BUILD:
				setBuild((String)newValue);
				return;
			case DockerPackage.CONTAINER__COMMAND:
				setCommand((String)newValue);
				return;
			case DockerPackage.CONTAINER__PORTS:
				setPorts((ArrayOfString)newValue);
				return;
			case DockerPackage.CONTAINER__EXPOSE:
				setExpose((ArrayOfString)newValue);
				return;
			case DockerPackage.CONTAINER__VOLUMES:
				setVolumes((ArrayOfString)newValue);
				return;
			case DockerPackage.CONTAINER__ENVIRONMENT:
				setEnvironment((ArrayOfString)newValue);
				return;
			case DockerPackage.CONTAINER__ENV_FILE:
				setEnvFile((ArrayOfString)newValue);
				return;
			case DockerPackage.CONTAINER__NET:
				setNet((String)newValue);
				return;
			case DockerPackage.CONTAINER__DNS:
				setDns((ArrayOfString)newValue);
				return;
			case DockerPackage.CONTAINER__DNS_SEARCH:
				setDnsSearch((ArrayOfString)newValue);
				return;
			case DockerPackage.CONTAINER__CAP_ADD:
				setCapAdd((ArrayOfString)newValue);
				return;
			case DockerPackage.CONTAINER__CAP_DROP:
				setCapDrop((ArrayOfString)newValue);
				return;
			case DockerPackage.CONTAINER__WORKING_DIR:
				setWorkingDir((String)newValue);
				return;
			case DockerPackage.CONTAINER__ENTRYPOINT:
				setEntrypoint((String)newValue);
				return;
			case DockerPackage.CONTAINER__USER:
				setUser((String)newValue);
				return;
			case DockerPackage.CONTAINER__DOMAIN_NAME:
				setDomainName((String)newValue);
				return;
			case DockerPackage.CONTAINER__MEM_LIMIT:
				setMemLimit((Integer)newValue);
				return;
			case DockerPackage.CONTAINER__MEMORY_SWAP:
				setMemorySwap((Integer)newValue);
				return;
			case DockerPackage.CONTAINER__PRIVILEGED:
				setPrivileged((Boolean)newValue);
				return;
			case DockerPackage.CONTAINER__RESTART:
				setRestart((String)newValue);
				return;
			case DockerPackage.CONTAINER__STDIN_OPEN:
				setStdinOpen((Boolean)newValue);
				return;
			case DockerPackage.CONTAINER__INTERACTIVE:
				setInteractive((Boolean)newValue);
				return;
			case DockerPackage.CONTAINER__CPU_SHARES:
				setCpuShares((Integer)newValue);
				return;
			case DockerPackage.CONTAINER__PID:
				setPid((String)newValue);
				return;
			case DockerPackage.CONTAINER__IPC:
				setIpc((String)newValue);
				return;
			case DockerPackage.CONTAINER__ADD_HOST:
				setAddHost((ArrayOfString)newValue);
				return;
			case DockerPackage.CONTAINER__MAC_ADDRESS:
				setMacAddress((String)newValue);
				return;
			case DockerPackage.CONTAINER__RM:
				setRm((Boolean)newValue);
				return;
			case DockerPackage.CONTAINER__SECURITY_OPT:
				setSecurityOpt((ArrayOfString)newValue);
				return;
			case DockerPackage.CONTAINER__DEVICE:
				setDevice((ArrayOfString)newValue);
				return;
			case DockerPackage.CONTAINER__LXC_CONF:
				setLxcConf((ArrayOfString)newValue);
				return;
			case DockerPackage.CONTAINER__PUBLISH_ALL:
				setPublishAll((Boolean)newValue);
				return;
			case DockerPackage.CONTAINER__READ_ONLY:
				setReadOnly((Boolean)newValue);
				return;
			case DockerPackage.CONTAINER__MONITORED:
				setMonitored((Boolean)newValue);
				return;
			case DockerPackage.CONTAINER__CPU_USED:
				setCpuUsed((Integer)newValue);
				return;
			case DockerPackage.CONTAINER__MEMORY_USED:
				setMemoryUsed((Integer)newValue);
				return;
			case DockerPackage.CONTAINER__CPU_PERCENT:
				setCpuPercent((String)newValue);
				return;
			case DockerPackage.CONTAINER__MEMORY_PERCENT:
				setMemoryPercent((String)newValue);
				return;
			case DockerPackage.CONTAINER__DISK_USED:
				setDiskUsed((Integer)newValue);
				return;
			case DockerPackage.CONTAINER__DISK_PERCENT:
				setDiskPercent((String)newValue);
				return;
			case DockerPackage.CONTAINER__BANDWIDTH_USED:
				setBandwidthUsed((Integer)newValue);
				return;
			case DockerPackage.CONTAINER__BANDWIDTH_PERCENT:
				setBandwidthPercent((String)newValue);
				return;
			case DockerPackage.CONTAINER__MONITORING_INTERVAL:
				setMonitoringInterval((Integer)newValue);
				return;
			case DockerPackage.CONTAINER__CPU_MAX_VALUE:
				setCpuMaxValue((Integer)newValue);
				return;
			case DockerPackage.CONTAINER__MEMORY_MAX_VALUE:
				setMemoryMaxValue((Integer)newValue);
				return;
			case DockerPackage.CONTAINER__CORE_MAX:
				setCoreMax((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DockerPackage.CONTAINER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__CONTAINERID:
				setContainerid(CONTAINERID_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__IMAGE:
				setImage(IMAGE_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__BUILD:
				setBuild(BUILD_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__COMMAND:
				setCommand(COMMAND_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__PORTS:
				setPorts((ArrayOfString)null);
				return;
			case DockerPackage.CONTAINER__EXPOSE:
				setExpose((ArrayOfString)null);
				return;
			case DockerPackage.CONTAINER__VOLUMES:
				setVolumes((ArrayOfString)null);
				return;
			case DockerPackage.CONTAINER__ENVIRONMENT:
				setEnvironment((ArrayOfString)null);
				return;
			case DockerPackage.CONTAINER__ENV_FILE:
				setEnvFile((ArrayOfString)null);
				return;
			case DockerPackage.CONTAINER__NET:
				setNet(NET_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__DNS:
				setDns((ArrayOfString)null);
				return;
			case DockerPackage.CONTAINER__DNS_SEARCH:
				setDnsSearch((ArrayOfString)null);
				return;
			case DockerPackage.CONTAINER__CAP_ADD:
				setCapAdd((ArrayOfString)null);
				return;
			case DockerPackage.CONTAINER__CAP_DROP:
				setCapDrop((ArrayOfString)null);
				return;
			case DockerPackage.CONTAINER__WORKING_DIR:
				setWorkingDir(WORKING_DIR_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__ENTRYPOINT:
				setEntrypoint(ENTRYPOINT_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__USER:
				setUser(USER_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__DOMAIN_NAME:
				setDomainName(DOMAIN_NAME_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__MEM_LIMIT:
				setMemLimit(MEM_LIMIT_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__MEMORY_SWAP:
				setMemorySwap(MEMORY_SWAP_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__PRIVILEGED:
				setPrivileged(PRIVILEGED_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__RESTART:
				setRestart(RESTART_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__STDIN_OPEN:
				setStdinOpen(STDIN_OPEN_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__INTERACTIVE:
				setInteractive(INTERACTIVE_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__CPU_SHARES:
				setCpuShares(CPU_SHARES_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__PID:
				setPid(PID_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__IPC:
				setIpc(IPC_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__ADD_HOST:
				setAddHost((ArrayOfString)null);
				return;
			case DockerPackage.CONTAINER__MAC_ADDRESS:
				setMacAddress(MAC_ADDRESS_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__RM:
				setRm(RM_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__SECURITY_OPT:
				setSecurityOpt((ArrayOfString)null);
				return;
			case DockerPackage.CONTAINER__DEVICE:
				setDevice((ArrayOfString)null);
				return;
			case DockerPackage.CONTAINER__LXC_CONF:
				setLxcConf((ArrayOfString)null);
				return;
			case DockerPackage.CONTAINER__PUBLISH_ALL:
				setPublishAll(PUBLISH_ALL_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__READ_ONLY:
				setReadOnly(READ_ONLY_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__MONITORED:
				setMonitored(MONITORED_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__CPU_USED:
				setCpuUsed(CPU_USED_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__MEMORY_USED:
				setMemoryUsed(MEMORY_USED_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__CPU_PERCENT:
				setCpuPercent(CPU_PERCENT_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__MEMORY_PERCENT:
				setMemoryPercent(MEMORY_PERCENT_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__DISK_USED:
				setDiskUsed(DISK_USED_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__DISK_PERCENT:
				setDiskPercent(DISK_PERCENT_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__BANDWIDTH_USED:
				setBandwidthUsed(BANDWIDTH_USED_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__BANDWIDTH_PERCENT:
				setBandwidthPercent(BANDWIDTH_PERCENT_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__MONITORING_INTERVAL:
				setMonitoringInterval(MONITORING_INTERVAL_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__CPU_MAX_VALUE:
				setCpuMaxValue(CPU_MAX_VALUE_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__MEMORY_MAX_VALUE:
				setMemoryMaxValue(MEMORY_MAX_VALUE_EDEFAULT);
				return;
			case DockerPackage.CONTAINER__CORE_MAX:
				setCoreMax(CORE_MAX_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DockerPackage.CONTAINER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DockerPackage.CONTAINER__CONTAINERID:
				return CONTAINERID_EDEFAULT == null ? containerid != null : !CONTAINERID_EDEFAULT.equals(containerid);
			case DockerPackage.CONTAINER__IMAGE:
				return IMAGE_EDEFAULT == null ? image != null : !IMAGE_EDEFAULT.equals(image);
			case DockerPackage.CONTAINER__BUILD:
				return BUILD_EDEFAULT == null ? build != null : !BUILD_EDEFAULT.equals(build);
			case DockerPackage.CONTAINER__COMMAND:
				return COMMAND_EDEFAULT == null ? command != null : !COMMAND_EDEFAULT.equals(command);
			case DockerPackage.CONTAINER__PORTS:
				return ports != null;
			case DockerPackage.CONTAINER__EXPOSE:
				return expose != null;
			case DockerPackage.CONTAINER__VOLUMES:
				return volumes != null;
			case DockerPackage.CONTAINER__ENVIRONMENT:
				return environment != null;
			case DockerPackage.CONTAINER__ENV_FILE:
				return envFile != null;
			case DockerPackage.CONTAINER__NET:
				return NET_EDEFAULT == null ? net != null : !NET_EDEFAULT.equals(net);
			case DockerPackage.CONTAINER__DNS:
				return dns != null;
			case DockerPackage.CONTAINER__DNS_SEARCH:
				return dnsSearch != null;
			case DockerPackage.CONTAINER__CAP_ADD:
				return capAdd != null;
			case DockerPackage.CONTAINER__CAP_DROP:
				return capDrop != null;
			case DockerPackage.CONTAINER__WORKING_DIR:
				return WORKING_DIR_EDEFAULT == null ? workingDir != null : !WORKING_DIR_EDEFAULT.equals(workingDir);
			case DockerPackage.CONTAINER__ENTRYPOINT:
				return ENTRYPOINT_EDEFAULT == null ? entrypoint != null : !ENTRYPOINT_EDEFAULT.equals(entrypoint);
			case DockerPackage.CONTAINER__USER:
				return USER_EDEFAULT == null ? user != null : !USER_EDEFAULT.equals(user);
			case DockerPackage.CONTAINER__DOMAIN_NAME:
				return DOMAIN_NAME_EDEFAULT == null ? domainName != null : !DOMAIN_NAME_EDEFAULT.equals(domainName);
			case DockerPackage.CONTAINER__MEM_LIMIT:
				return MEM_LIMIT_EDEFAULT == null ? memLimit != null : !MEM_LIMIT_EDEFAULT.equals(memLimit);
			case DockerPackage.CONTAINER__MEMORY_SWAP:
				return MEMORY_SWAP_EDEFAULT == null ? memorySwap != null : !MEMORY_SWAP_EDEFAULT.equals(memorySwap);
			case DockerPackage.CONTAINER__PRIVILEGED:
				return privileged != PRIVILEGED_EDEFAULT;
			case DockerPackage.CONTAINER__RESTART:
				return RESTART_EDEFAULT == null ? restart != null : !RESTART_EDEFAULT.equals(restart);
			case DockerPackage.CONTAINER__STDIN_OPEN:
				return stdinOpen != STDIN_OPEN_EDEFAULT;
			case DockerPackage.CONTAINER__INTERACTIVE:
				return interactive != INTERACTIVE_EDEFAULT;
			case DockerPackage.CONTAINER__CPU_SHARES:
				return CPU_SHARES_EDEFAULT == null ? cpuShares != null : !CPU_SHARES_EDEFAULT.equals(cpuShares);
			case DockerPackage.CONTAINER__PID:
				return PID_EDEFAULT == null ? pid != null : !PID_EDEFAULT.equals(pid);
			case DockerPackage.CONTAINER__IPC:
				return IPC_EDEFAULT == null ? ipc != null : !IPC_EDEFAULT.equals(ipc);
			case DockerPackage.CONTAINER__ADD_HOST:
				return addHost != null;
			case DockerPackage.CONTAINER__MAC_ADDRESS:
				return MAC_ADDRESS_EDEFAULT == null ? macAddress != null : !MAC_ADDRESS_EDEFAULT.equals(macAddress);
			case DockerPackage.CONTAINER__RM:
				return rm != RM_EDEFAULT;
			case DockerPackage.CONTAINER__SECURITY_OPT:
				return securityOpt != null;
			case DockerPackage.CONTAINER__DEVICE:
				return device != null;
			case DockerPackage.CONTAINER__LXC_CONF:
				return lxcConf != null;
			case DockerPackage.CONTAINER__PUBLISH_ALL:
				return publishAll != PUBLISH_ALL_EDEFAULT;
			case DockerPackage.CONTAINER__READ_ONLY:
				return readOnly != READ_ONLY_EDEFAULT;
			case DockerPackage.CONTAINER__MONITORED:
				return monitored != MONITORED_EDEFAULT;
			case DockerPackage.CONTAINER__CPU_USED:
				return CPU_USED_EDEFAULT == null ? cpuUsed != null : !CPU_USED_EDEFAULT.equals(cpuUsed);
			case DockerPackage.CONTAINER__MEMORY_USED:
				return MEMORY_USED_EDEFAULT == null ? memoryUsed != null : !MEMORY_USED_EDEFAULT.equals(memoryUsed);
			case DockerPackage.CONTAINER__CPU_PERCENT:
				return CPU_PERCENT_EDEFAULT == null ? cpuPercent != null : !CPU_PERCENT_EDEFAULT.equals(cpuPercent);
			case DockerPackage.CONTAINER__MEMORY_PERCENT:
				return MEMORY_PERCENT_EDEFAULT == null ? memoryPercent != null : !MEMORY_PERCENT_EDEFAULT.equals(memoryPercent);
			case DockerPackage.CONTAINER__DISK_USED:
				return DISK_USED_EDEFAULT == null ? diskUsed != null : !DISK_USED_EDEFAULT.equals(diskUsed);
			case DockerPackage.CONTAINER__DISK_PERCENT:
				return DISK_PERCENT_EDEFAULT == null ? diskPercent != null : !DISK_PERCENT_EDEFAULT.equals(diskPercent);
			case DockerPackage.CONTAINER__BANDWIDTH_USED:
				return BANDWIDTH_USED_EDEFAULT == null ? bandwidthUsed != null : !BANDWIDTH_USED_EDEFAULT.equals(bandwidthUsed);
			case DockerPackage.CONTAINER__BANDWIDTH_PERCENT:
				return BANDWIDTH_PERCENT_EDEFAULT == null ? bandwidthPercent != null : !BANDWIDTH_PERCENT_EDEFAULT.equals(bandwidthPercent);
			case DockerPackage.CONTAINER__MONITORING_INTERVAL:
				return MONITORING_INTERVAL_EDEFAULT == null ? monitoringInterval != null : !MONITORING_INTERVAL_EDEFAULT.equals(monitoringInterval);
			case DockerPackage.CONTAINER__CPU_MAX_VALUE:
				return CPU_MAX_VALUE_EDEFAULT == null ? cpuMaxValue != null : !CPU_MAX_VALUE_EDEFAULT.equals(cpuMaxValue);
			case DockerPackage.CONTAINER__MEMORY_MAX_VALUE:
				return MEMORY_MAX_VALUE_EDEFAULT == null ? memoryMaxValue != null : !MEMORY_MAX_VALUE_EDEFAULT.equals(memoryMaxValue);
			case DockerPackage.CONTAINER__CORE_MAX:
				return CORE_MAX_EDEFAULT == null ? coreMax != null : !CORE_MAX_EDEFAULT.equals(coreMax);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case DockerPackage.CONTAINER___CREATE:
				create();
				return null;
			case DockerPackage.CONTAINER___STOP:
				stop();
				return null;
			case DockerPackage.CONTAINER___RUN:
				run();
				return null;
			case DockerPackage.CONTAINER___PAUSE:
				pause();
				return null;
			case DockerPackage.CONTAINER___UNPAUSE:
				unpause();
				return null;
			case DockerPackage.CONTAINER___KILL__STRING:
				kill((String)arguments.get(0));
				return null;
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", containerid: ");
		result.append(containerid);
		result.append(", image: ");
		result.append(image);
		result.append(", build: ");
		result.append(build);
		result.append(", command: ");
		result.append(command);
		result.append(", net: ");
		result.append(net);
		result.append(", workingDir: ");
		result.append(workingDir);
		result.append(", entrypoint: ");
		result.append(entrypoint);
		result.append(", user: ");
		result.append(user);
		result.append(", domainName: ");
		result.append(domainName);
		result.append(", memLimit: ");
		result.append(memLimit);
		result.append(", memorySwap: ");
		result.append(memorySwap);
		result.append(", privileged: ");
		result.append(privileged);
		result.append(", restart: ");
		result.append(restart);
		result.append(", stdinOpen: ");
		result.append(stdinOpen);
		result.append(", interactive: ");
		result.append(interactive);
		result.append(", cpuShares: ");
		result.append(cpuShares);
		result.append(", pid: ");
		result.append(pid);
		result.append(", ipc: ");
		result.append(ipc);
		result.append(", macAddress: ");
		result.append(macAddress);
		result.append(", rm: ");
		result.append(rm);
		result.append(", publishAll: ");
		result.append(publishAll);
		result.append(", readOnly: ");
		result.append(readOnly);
		result.append(", monitored: ");
		result.append(monitored);
		result.append(", cpuUsed: ");
		result.append(cpuUsed);
		result.append(", memoryUsed: ");
		result.append(memoryUsed);
		result.append(", cpuPercent: ");
		result.append(cpuPercent);
		result.append(", memoryPercent: ");
		result.append(memoryPercent);
		result.append(", diskUsed: ");
		result.append(diskUsed);
		result.append(", diskPercent: ");
		result.append(diskPercent);
		result.append(", bandwidthUsed: ");
		result.append(bandwidthUsed);
		result.append(", bandwidthPercent: ");
		result.append(bandwidthPercent);
		result.append(", monitoringInterval: ");
		result.append(monitoringInterval);
		result.append(", cpuMaxValue: ");
		result.append(cpuMaxValue);
		result.append(", memoryMaxValue: ");
		result.append(memoryMaxValue);
		result.append(", coreMax: ");
		result.append(coreMax);
		result.append(')');
		return result.toString();
	}

} //ContainerImpl
