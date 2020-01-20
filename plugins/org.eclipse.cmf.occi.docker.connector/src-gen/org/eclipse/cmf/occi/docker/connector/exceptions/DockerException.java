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
package org.eclipse.cmf.occi.docker.connector.exceptions;

/**
 * Generic exception thrown when using docker client (or configuration failed).
 * 
 * @author Christophe Gourdin
 *
 */
public class DockerException extends Exception {

	private static final long serialVersionUID = -4327274087174792001L;

	public DockerException() {
		super();
	}

	public DockerException(String message, Throwable cause) {
		super(message, cause);
	}

	public DockerException(String message) {
		super(message);
	}

	public DockerException(Throwable cause) {
		super(cause);
	}

}
