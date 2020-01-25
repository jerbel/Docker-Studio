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
package org.eclipse.cmf.occi.docker.connector.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GraphNode<T> {

	public T value;
	private List<GraphNode<T>> comingInNodes;
	private List<GraphNode<T>> goingOutNodes;

	private static Logger LOGGER = LoggerFactory.getLogger(Graph.class);

	/*
	 * Adds an incoming node to the current node
	 * 
	 * @param node The incoming node
	 */
	public void addComingInNode(GraphNode<T> node) {
		if (comingInNodes == null) {
			comingInNodes = new ArrayList<>();
		}
		comingInNodes.add(node);
	}

	/*
	 * Adds an outgoing node from the current node
	 * 
	 * @param node The outgoing node
	 */
	public void addGoingOutNode(GraphNode<T> node) {
		if (goingOutNodes == null) {
			goingOutNodes = new ArrayList<>();
		}
		goingOutNodes.add(node);
	}

	/**
	 * Provides all the coming in nodes
	 * 
	 * @return The coming in nodes
	 */
	public List<GraphNode<T>> getComingInNodes() {
		return comingInNodes;
	}

	/*
	 * Provides all the going out nodes
	 * 
	 * @return The going out nodes
	 */
	public List<GraphNode<T>> getGoingOutNodes() {
		return goingOutNodes;
	}

	/**
	 * 
	 * @return
	 */
	public int getGoingOutNodesSize() {
		if (goingOutNodes == null) {
			goingOutNodes = new ArrayList<GraphNode<T>>();
		}
		return goingOutNodes.size();
	}

	/**
	 * 
	 * @return
	 */
	public int getComingInNodesSize() {
		if (comingInNodes == null) {
			comingInNodes = new ArrayList<GraphNode<T>>();
		}
		return comingInNodes.size();
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	public int getTreeSize(GraphNode<T> node) {
		int count = 1;
		if (node.getGoingOutNodesSize() > 0) {
			count++; // Get number of familly
			for (GraphNode<T> n : node.getGoingOutNodes()) {
				if (n.getGoingOutNodesSize() > 0) {
					count++; // Get number of familly
					getTreeSize(n);
				}
			}
		}
		return count;
	}

}
