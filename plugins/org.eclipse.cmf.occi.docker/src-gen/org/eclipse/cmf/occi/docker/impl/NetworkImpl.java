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

import org.eclipse.cmf.occi.docker.DockerPackage;
import org.eclipse.cmf.occi.docker.Network;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Network</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.NetworkImpl#getNetworkId <em>Network Id</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.NetworkImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.NetworkImpl#getAuxAddress <em>Aux Address</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.NetworkImpl#getDriver <em>Driver</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.NetworkImpl#getGateway <em>Gateway</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.NetworkImpl#getInternal <em>Internal</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.NetworkImpl#getIpRange <em>Ip Range</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.NetworkImpl#getIpamDriver <em>Ipam Driver</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.NetworkImpl#getIpamOpt <em>Ipam Opt</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.NetworkImpl#getIpv6 <em>Ipv6</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.NetworkImpl#getOpt <em>Opt</em>}</li>
 *   <li>{@link org.eclipse.cmf.occi.docker.impl.NetworkImpl#getSubnet <em>Subnet</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NetworkImpl extends org.eclipse.cmf.occi.infrastructure.impl.NetworkImpl implements Network {
	/**
	 * The default value of the '{@link #getNetworkId() <em>Network Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNetworkId()
	 * @generated
	 * @ordered
	 */
	protected static final String NETWORK_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNetworkId() <em>Network Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNetworkId()
	 * @generated
	 * @ordered
	 */
	protected String networkId = NETWORK_ID_EDEFAULT;

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
	 * The default value of the '{@link #getAuxAddress() <em>Aux Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuxAddress()
	 * @generated
	 * @ordered
	 */
	protected static final String AUX_ADDRESS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuxAddress() <em>Aux Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuxAddress()
	 * @generated
	 * @ordered
	 */
	protected String auxAddress = AUX_ADDRESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getDriver() <em>Driver</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDriver()
	 * @generated
	 * @ordered
	 */
	protected static final String DRIVER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDriver() <em>Driver</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDriver()
	 * @generated
	 * @ordered
	 */
	protected String driver = DRIVER_EDEFAULT;

	/**
	 * The default value of the '{@link #getGateway() <em>Gateway</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGateway()
	 * @generated
	 * @ordered
	 */
	protected static final String GATEWAY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGateway() <em>Gateway</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGateway()
	 * @generated
	 * @ordered
	 */
	protected String gateway = GATEWAY_EDEFAULT;

	/**
	 * The default value of the '{@link #getInternal() <em>Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInternal()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean INTERNAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInternal() <em>Internal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInternal()
	 * @generated
	 * @ordered
	 */
	protected Boolean internal = INTERNAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getIpRange() <em>Ip Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIpRange()
	 * @generated
	 * @ordered
	 */
	protected static final String IP_RANGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIpRange() <em>Ip Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIpRange()
	 * @generated
	 * @ordered
	 */
	protected String ipRange = IP_RANGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getIpamDriver() <em>Ipam Driver</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIpamDriver()
	 * @generated
	 * @ordered
	 */
	protected static final String IPAM_DRIVER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIpamDriver() <em>Ipam Driver</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIpamDriver()
	 * @generated
	 * @ordered
	 */
	protected String ipamDriver = IPAM_DRIVER_EDEFAULT;

	/**
	 * The default value of the '{@link #getIpamOpt() <em>Ipam Opt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIpamOpt()
	 * @generated
	 * @ordered
	 */
	protected static final String IPAM_OPT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIpamOpt() <em>Ipam Opt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIpamOpt()
	 * @generated
	 * @ordered
	 */
	protected String ipamOpt = IPAM_OPT_EDEFAULT;

	/**
	 * The default value of the '{@link #getIpv6() <em>Ipv6</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIpv6()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean IPV6_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIpv6() <em>Ipv6</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIpv6()
	 * @generated
	 * @ordered
	 */
	protected Boolean ipv6 = IPV6_EDEFAULT;

	/**
	 * The default value of the '{@link #getOpt() <em>Opt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOpt()
	 * @generated
	 * @ordered
	 */
	protected static final String OPT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOpt() <em>Opt</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOpt()
	 * @generated
	 * @ordered
	 */
	protected String opt = OPT_EDEFAULT;

	/**
	 * The default value of the '{@link #getSubnet() <em>Subnet</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubnet()
	 * @generated
	 * @ordered
	 */
	protected static final String SUBNET_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSubnet() <em>Subnet</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubnet()
	 * @generated
	 * @ordered
	 */
	protected String subnet = SUBNET_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NetworkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DockerPackage.Literals.NETWORK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getNetworkId() {
		return networkId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNetworkId(String newNetworkId) {
		String oldNetworkId = networkId;
		networkId = newNetworkId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.NETWORK__NETWORK_ID, oldNetworkId, networkId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.NETWORK__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAuxAddress() {
		return auxAddress;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAuxAddress(String newAuxAddress) {
		String oldAuxAddress = auxAddress;
		auxAddress = newAuxAddress;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.NETWORK__AUX_ADDRESS, oldAuxAddress, auxAddress));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDriver() {
		return driver;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDriver(String newDriver) {
		String oldDriver = driver;
		driver = newDriver;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.NETWORK__DRIVER, oldDriver, driver));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getGateway() {
		return gateway;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGateway(String newGateway) {
		String oldGateway = gateway;
		gateway = newGateway;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.NETWORK__GATEWAY, oldGateway, gateway));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getInternal() {
		return internal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInternal(Boolean newInternal) {
		Boolean oldInternal = internal;
		internal = newInternal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.NETWORK__INTERNAL, oldInternal, internal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getIpRange() {
		return ipRange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIpRange(String newIpRange) {
		String oldIpRange = ipRange;
		ipRange = newIpRange;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.NETWORK__IP_RANGE, oldIpRange, ipRange));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getIpamDriver() {
		return ipamDriver;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIpamDriver(String newIpamDriver) {
		String oldIpamDriver = ipamDriver;
		ipamDriver = newIpamDriver;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.NETWORK__IPAM_DRIVER, oldIpamDriver, ipamDriver));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getIpamOpt() {
		return ipamOpt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIpamOpt(String newIpamOpt) {
		String oldIpamOpt = ipamOpt;
		ipamOpt = newIpamOpt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.NETWORK__IPAM_OPT, oldIpamOpt, ipamOpt));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getIpv6() {
		return ipv6;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIpv6(Boolean newIpv6) {
		Boolean oldIpv6 = ipv6;
		ipv6 = newIpv6;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.NETWORK__IPV6, oldIpv6, ipv6));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getOpt() {
		return opt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOpt(String newOpt) {
		String oldOpt = opt;
		opt = newOpt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.NETWORK__OPT, oldOpt, opt));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSubnet() {
		return subnet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSubnet(String newSubnet) {
		String oldSubnet = subnet;
		subnet = newSubnet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DockerPackage.NETWORK__SUBNET, oldSubnet, subnet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DockerPackage.NETWORK__NETWORK_ID:
				return getNetworkId();
			case DockerPackage.NETWORK__NAME:
				return getName();
			case DockerPackage.NETWORK__AUX_ADDRESS:
				return getAuxAddress();
			case DockerPackage.NETWORK__DRIVER:
				return getDriver();
			case DockerPackage.NETWORK__GATEWAY:
				return getGateway();
			case DockerPackage.NETWORK__INTERNAL:
				return getInternal();
			case DockerPackage.NETWORK__IP_RANGE:
				return getIpRange();
			case DockerPackage.NETWORK__IPAM_DRIVER:
				return getIpamDriver();
			case DockerPackage.NETWORK__IPAM_OPT:
				return getIpamOpt();
			case DockerPackage.NETWORK__IPV6:
				return getIpv6();
			case DockerPackage.NETWORK__OPT:
				return getOpt();
			case DockerPackage.NETWORK__SUBNET:
				return getSubnet();
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
			case DockerPackage.NETWORK__NETWORK_ID:
				setNetworkId((String)newValue);
				return;
			case DockerPackage.NETWORK__NAME:
				setName((String)newValue);
				return;
			case DockerPackage.NETWORK__AUX_ADDRESS:
				setAuxAddress((String)newValue);
				return;
			case DockerPackage.NETWORK__DRIVER:
				setDriver((String)newValue);
				return;
			case DockerPackage.NETWORK__GATEWAY:
				setGateway((String)newValue);
				return;
			case DockerPackage.NETWORK__INTERNAL:
				setInternal((Boolean)newValue);
				return;
			case DockerPackage.NETWORK__IP_RANGE:
				setIpRange((String)newValue);
				return;
			case DockerPackage.NETWORK__IPAM_DRIVER:
				setIpamDriver((String)newValue);
				return;
			case DockerPackage.NETWORK__IPAM_OPT:
				setIpamOpt((String)newValue);
				return;
			case DockerPackage.NETWORK__IPV6:
				setIpv6((Boolean)newValue);
				return;
			case DockerPackage.NETWORK__OPT:
				setOpt((String)newValue);
				return;
			case DockerPackage.NETWORK__SUBNET:
				setSubnet((String)newValue);
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
			case DockerPackage.NETWORK__NETWORK_ID:
				setNetworkId(NETWORK_ID_EDEFAULT);
				return;
			case DockerPackage.NETWORK__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DockerPackage.NETWORK__AUX_ADDRESS:
				setAuxAddress(AUX_ADDRESS_EDEFAULT);
				return;
			case DockerPackage.NETWORK__DRIVER:
				setDriver(DRIVER_EDEFAULT);
				return;
			case DockerPackage.NETWORK__GATEWAY:
				setGateway(GATEWAY_EDEFAULT);
				return;
			case DockerPackage.NETWORK__INTERNAL:
				setInternal(INTERNAL_EDEFAULT);
				return;
			case DockerPackage.NETWORK__IP_RANGE:
				setIpRange(IP_RANGE_EDEFAULT);
				return;
			case DockerPackage.NETWORK__IPAM_DRIVER:
				setIpamDriver(IPAM_DRIVER_EDEFAULT);
				return;
			case DockerPackage.NETWORK__IPAM_OPT:
				setIpamOpt(IPAM_OPT_EDEFAULT);
				return;
			case DockerPackage.NETWORK__IPV6:
				setIpv6(IPV6_EDEFAULT);
				return;
			case DockerPackage.NETWORK__OPT:
				setOpt(OPT_EDEFAULT);
				return;
			case DockerPackage.NETWORK__SUBNET:
				setSubnet(SUBNET_EDEFAULT);
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
			case DockerPackage.NETWORK__NETWORK_ID:
				return NETWORK_ID_EDEFAULT == null ? networkId != null : !NETWORK_ID_EDEFAULT.equals(networkId);
			case DockerPackage.NETWORK__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DockerPackage.NETWORK__AUX_ADDRESS:
				return AUX_ADDRESS_EDEFAULT == null ? auxAddress != null : !AUX_ADDRESS_EDEFAULT.equals(auxAddress);
			case DockerPackage.NETWORK__DRIVER:
				return DRIVER_EDEFAULT == null ? driver != null : !DRIVER_EDEFAULT.equals(driver);
			case DockerPackage.NETWORK__GATEWAY:
				return GATEWAY_EDEFAULT == null ? gateway != null : !GATEWAY_EDEFAULT.equals(gateway);
			case DockerPackage.NETWORK__INTERNAL:
				return INTERNAL_EDEFAULT == null ? internal != null : !INTERNAL_EDEFAULT.equals(internal);
			case DockerPackage.NETWORK__IP_RANGE:
				return IP_RANGE_EDEFAULT == null ? ipRange != null : !IP_RANGE_EDEFAULT.equals(ipRange);
			case DockerPackage.NETWORK__IPAM_DRIVER:
				return IPAM_DRIVER_EDEFAULT == null ? ipamDriver != null : !IPAM_DRIVER_EDEFAULT.equals(ipamDriver);
			case DockerPackage.NETWORK__IPAM_OPT:
				return IPAM_OPT_EDEFAULT == null ? ipamOpt != null : !IPAM_OPT_EDEFAULT.equals(ipamOpt);
			case DockerPackage.NETWORK__IPV6:
				return IPV6_EDEFAULT == null ? ipv6 != null : !IPV6_EDEFAULT.equals(ipv6);
			case DockerPackage.NETWORK__OPT:
				return OPT_EDEFAULT == null ? opt != null : !OPT_EDEFAULT.equals(opt);
			case DockerPackage.NETWORK__SUBNET:
				return SUBNET_EDEFAULT == null ? subnet != null : !SUBNET_EDEFAULT.equals(subnet);
		}
		return super.eIsSet(featureID);
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
		result.append(" (networkId: ");
		result.append(networkId);
		result.append(", name: ");
		result.append(name);
		result.append(", auxAddress: ");
		result.append(auxAddress);
		result.append(", driver: ");
		result.append(driver);
		result.append(", gateway: ");
		result.append(gateway);
		result.append(", internal: ");
		result.append(internal);
		result.append(", ipRange: ");
		result.append(ipRange);
		result.append(", ipamDriver: ");
		result.append(ipamDriver);
		result.append(", ipamOpt: ");
		result.append(ipamOpt);
		result.append(", ipv6: ");
		result.append(ipv6);
		result.append(", opt: ");
		result.append(opt);
		result.append(", subnet: ");
		result.append(subnet);
		result.append(')');
		return result.toString();
	}

} //NetworkImpl
