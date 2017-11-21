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
package org.eclipse.cmf.occi.docker.connector.helpers;

import java.util.Map;

import org.eclipse.cmf.occi.core.Configuration;
import org.eclipse.cmf.occi.core.Entity;
import org.eclipse.cmf.occi.core.Link;
import org.eclipse.cmf.occi.core.Resource;
import org.eclipse.cmf.occi.core.util.OcciHelper;
import org.eclipse.cmf.occi.docker.Container;
import org.eclipse.cmf.occi.docker.Machine;
import org.eclipse.cmf.occi.docker.connector.exceptions.DockerException;
import org.eclipse.cmf.occi.docker.connector.utils.DockerUtil;
import org.eclipse.cmf.occi.docker.connector.utils.ModelHandler;
import org.eclipse.cmf.occi.infrastructure.Compute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is for import and synchronize current model (used by occiRetrieve() as well for containers and machine, use for DockerServices with sirius.)
 * @author Christophe Gourdin
 *
 */
public class DockerModelHelper {
	
	private Configuration configuration;
	
	private static Logger LOGGER = LoggerFactory.getLogger(DockerModelHelper.class);
	
	/**
	 * Entity is used to retrieve current configuration object only.
	 * @param entity
	 */
	public DockerModelHelper(Entity entity) {
		this.configuration = OcciHelper.getConfiguration(entity);
	}
	
	/**
	 * 
	 * @param configuration
	 */
	public DockerModelHelper(Configuration configuration) {
		this.configuration = configuration;
	}
	
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	/**
	 * Import the overall machine and container in current configuration model object.
	 * @throws DockerException
	 */
	public void importModel() throws DockerException {
		Map<String, String> hosts = DockerUtil.getHosts();
		ModelHandler modelHandler = new ModelHandler();
		
		LOGGER.info("Importing model...");
		LOGGER.info(hosts.toString());
		for (Map.Entry<String, String> entry : hosts.entrySet()) {
			boolean machineExistInModeler = containMachine(entry.getKey());
			if (!machineExistInModeler) {
				Machine machine = modelHandler.getModel(entry.getKey(), entry.getValue(), machineExistInModeler);
				this.configuration.getResources().add(machine);
				if (machine.getLinks() != null) {
					for (Link link : machine.getLinks()) {
						if (link.getTarget() instanceof Container) {
							this.configuration.getResources().add(link.getTarget());
						}
					}
				}
			} else {
				LOGGER.info("Machine : " + entry.getKey() + " already exist in model");
			}
		}
	}
	
	
	
	
	/**
	 * 
	 * @param machineName
	 * @return
	 * @throws DockerException
	 */
	public boolean containMachine(String machineName) throws DockerException {
		if (this.configuration == null) {
			throw new DockerException("No configuration object defined.");
		}
		for (Resource r : this.configuration.getResources()) {
			if (r instanceof Machine) {
				String name = ((Machine)r).getName();
				
				if (name != null && name.equals(machineName)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	
}
