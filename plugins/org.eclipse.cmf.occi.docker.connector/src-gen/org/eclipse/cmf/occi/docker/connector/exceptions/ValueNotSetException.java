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

public class ValueNotSetException extends Exception {

	private static final long serialVersionUID = 5637847215708641285L;

	private String attributeName = null;

	public ValueNotSetException() {
		super();
	}

	public ValueNotSetException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValueNotSetException(String message) {
		super(message);
	}

	public ValueNotSetException(Throwable cause) {
		super(cause);
	}

	public ValueNotSetException(String attributeName, String message) {
		super(message);
		this.attributeName = attributeName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

}
