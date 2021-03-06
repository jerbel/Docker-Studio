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
 * A representation of the model object '<em><b>Machinevirtualbox</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Machine on VirtualBox
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getBoot2dockerURL <em>Boot2docker URL</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getDiskSize <em>Disk Size</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getHostDNSResolver <em>Host DNS Resolver</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getImportBoot2DockerVM <em>Import Boot2 Docker VM</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getHostOnlyCIDR <em>Host Only CIDR</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getHostOnlyNICType <em>Host Only NIC Type</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getHostOnlyNICPromisc <em>Host Only NIC Promisc</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getNoShare <em>No Share</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getNoDNSProxy <em>No DNS Proxy</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getNoVTXCheck <em>No VTX Check</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getShareFolder <em>Share Folder</em>}</li>
 * </ul>
 *
 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevirtualbox()
 * @model
 * @generated
 */
public interface Machinevirtualbox extends Machine {
	/**
	 * Returns the value of the '<em><b>Boot2docker URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Boot2docker URL</em>' attribute.
	 * @see #setBoot2dockerURL(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevirtualbox_Boot2dockerURL()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getBoot2dockerURL();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getBoot2dockerURL <em>Boot2docker URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Boot2docker URL</em>' attribute.
	 * @see #getBoot2dockerURL()
	 * @generated
	 */
	void setBoot2dockerURL(String value);

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
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevirtualbox_DiskSize()
	 * @model default="20000" dataType="org.eclipse.cmf.occi.docker.Int"
	 * @generated
	 */
	Integer getDiskSize();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getDiskSize <em>Disk Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Disk Size</em>' attribute.
	 * @see #getDiskSize()
	 * @generated
	 */
	void setDiskSize(Integer value);

	/**
	 * Returns the value of the '<em><b>Host DNS Resolver</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Host DNS Resolver</em>' attribute.
	 * @see #setHostDNSResolver(Boolean)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevirtualbox_HostDNSResolver()
	 * @model default="false" dataType="org.eclipse.cmf.occi.docker.Bool"
	 * @generated
	 */
	Boolean getHostDNSResolver();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getHostDNSResolver <em>Host DNS Resolver</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Host DNS Resolver</em>' attribute.
	 * @see #getHostDNSResolver()
	 * @generated
	 */
	void setHostDNSResolver(Boolean value);

	/**
	 * Returns the value of the '<em><b>Import Boot2 Docker VM</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Import Boot2 Docker VM</em>' attribute.
	 * @see #setImportBoot2DockerVM(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevirtualbox_ImportBoot2DockerVM()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getImportBoot2DockerVM();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getImportBoot2DockerVM <em>Import Boot2 Docker VM</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Import Boot2 Docker VM</em>' attribute.
	 * @see #getImportBoot2DockerVM()
	 * @generated
	 */
	void setImportBoot2DockerVM(String value);

	/**
	 * Returns the value of the '<em><b>Host Only CIDR</b></em>' attribute.
	 * The default value is <code>"192.168.99.1/24"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Host Only CIDR</em>' attribute.
	 * @see #setHostOnlyCIDR(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevirtualbox_HostOnlyCIDR()
	 * @model default="192.168.99.1/24" dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getHostOnlyCIDR();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getHostOnlyCIDR <em>Host Only CIDR</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Host Only CIDR</em>' attribute.
	 * @see #getHostOnlyCIDR()
	 * @generated
	 */
	void setHostOnlyCIDR(String value);

	/**
	 * Returns the value of the '<em><b>Host Only NIC Type</b></em>' attribute.
	 * The default value is <code>"82540EM"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Host Only NIC Type</em>' attribute.
	 * @see #setHostOnlyNICType(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevirtualbox_HostOnlyNICType()
	 * @model default="82540EM" dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getHostOnlyNICType();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getHostOnlyNICType <em>Host Only NIC Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Host Only NIC Type</em>' attribute.
	 * @see #getHostOnlyNICType()
	 * @generated
	 */
	void setHostOnlyNICType(String value);

	/**
	 * Returns the value of the '<em><b>Host Only NIC Promisc</b></em>' attribute.
	 * The default value is <code>"deny"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Host Only NIC Promisc</em>' attribute.
	 * @see #setHostOnlyNICPromisc(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevirtualbox_HostOnlyNICPromisc()
	 * @model default="deny" dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getHostOnlyNICPromisc();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getHostOnlyNICPromisc <em>Host Only NIC Promisc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Host Only NIC Promisc</em>' attribute.
	 * @see #getHostOnlyNICPromisc()
	 * @generated
	 */
	void setHostOnlyNICPromisc(String value);

	/**
	 * Returns the value of the '<em><b>No Share</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>No Share</em>' attribute.
	 * @see #setNoShare(Boolean)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevirtualbox_NoShare()
	 * @model default="false" dataType="org.eclipse.cmf.occi.docker.Bool"
	 * @generated
	 */
	Boolean getNoShare();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getNoShare <em>No Share</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>No Share</em>' attribute.
	 * @see #getNoShare()
	 * @generated
	 */
	void setNoShare(Boolean value);

	/**
	 * Returns the value of the '<em><b>No DNS Proxy</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>No DNS Proxy</em>' attribute.
	 * @see #setNoDNSProxy(Boolean)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevirtualbox_NoDNSProxy()
	 * @model default="false" dataType="org.eclipse.cmf.occi.docker.Bool"
	 * @generated
	 */
	Boolean getNoDNSProxy();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getNoDNSProxy <em>No DNS Proxy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>No DNS Proxy</em>' attribute.
	 * @see #getNoDNSProxy()
	 * @generated
	 */
	void setNoDNSProxy(Boolean value);

	/**
	 * Returns the value of the '<em><b>No VTX Check</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>No VTX Check</em>' attribute.
	 * @see #setNoVTXCheck(Boolean)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevirtualbox_NoVTXCheck()
	 * @model default="false" dataType="org.eclipse.cmf.occi.docker.Bool"
	 * @generated
	 */
	Boolean getNoVTXCheck();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getNoVTXCheck <em>No VTX Check</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>No VTX Check</em>' attribute.
	 * @see #getNoVTXCheck()
	 * @generated
	 */
	void setNoVTXCheck(Boolean value);

	/**
	 * Returns the value of the '<em><b>Share Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Share Folder</em>' attribute.
	 * @see #setShareFolder(String)
	 * @see org.eclipse.cmf.occi.docker.DockerPackage#getMachinevirtualbox_ShareFolder()
	 * @model dataType="org.eclipse.cmf.occi.docker.String"
	 * @generated
	 */
	String getShareFolder();

	/**
	 * Sets the value of the '{@link org.eclipse.cmf.occi.docker.Machinevirtualbox#getShareFolder <em>Share Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Share Folder</em>' attribute.
	 * @see #getShareFolder()
	 * @generated
	 */
	void setShareFolder(String value);

} // Machinevirtualbox
