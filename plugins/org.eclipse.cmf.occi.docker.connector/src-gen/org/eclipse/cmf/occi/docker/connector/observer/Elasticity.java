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
package org.eclipse.cmf.occi.docker.connector.observer;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.eclipse.cmf.occi.core.Resource;
import org.eclipse.cmf.occi.docker.connector.ContainerConnector;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.docker.connector.helpers.CpuManager;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Elasticity {
	// Initialize logger for Elasticity.
	private static Logger LOGGER = LoggerFactory.getLogger(Elasticity.class);
	private Boolean update = false;

	public Elasticity() {
	}

	/**
	 * 
	 * @param cpuManager
	 * @param host
	 * @param privateKey
	 * @param container
	 */
	public void action(CpuManager cpuManager, String host, String privateKey, ContainerConnector container)
			throws DockerException {
		NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH); // TODO DYNAMIC number format
		try {
			float cpu_used = nf.parse(container.getCpuPercent()).floatValue();

			if (cpu_used > 90.0F && !update) {
				// Action
				cpuManager.setCPUValue(host, privateKey, container, "6");
				update = true;
				modifyResourceSet(container, 6); // Update the attribute
				System.out.println("Elasticity action: 2 Cores was added!");
			}
			if (cpu_used < 90.0F && update) {
				// Action
				cpuManager.setCPUValue(host, privateKey, container, "1");
				update = false;
				modifyResourceSet(container, 1); // Update the attribute
				System.out.println("Elasticity action: x Cores was removed!");
			}
		} catch (ParseException ex) {
			LOGGER.error("Parse error : " + ex.getMessage());
			throw new DockerException(ex.getMessage(), ex);
		}
	}

	/**
	 * 
	 * @param resource
	 * @param cores
	 */
	public synchronized void modifyResourceSet(Resource resource, Integer cores) {
		// Creating an editing domain
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(resource.eResource().getResourceSet());
		Command cmd = new RecordingCommand(domain) {
			@Override
			protected void doExecute() {
				// these modifications require a write transaction in this editing domain
				((ContainerConnector) resource).setOcciComputeCores(cores);
			}
		};

		try {
			((TransactionalCommandStack) domain.getCommandStack()).execute(cmd, null); // default options
		} catch (RollbackException rbe) {
			LOGGER.error(rbe.getStatus().toString());
			rbe.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
