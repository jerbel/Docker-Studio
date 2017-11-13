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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Graph<T> {

	private static Logger LOGGER = LoggerFactory.getLogger(Graph.class);

	/*
	 * Organize the deployment order of the Nodes
	 */
	private List<GraphNode<T>> deploymentOrder = new ArrayList<>();

	/*
	 * These are basically the nodes of the graph
	 */
	private Map<T, GraphNode<T>> nodes = new HashMap<>();

	/*
	 * The callback interface used to notify of the fact that a node just got the
	 * evaluation
	 */
	private NodeValueListener<T> listener;

	/*
	 * It holds a list of the already evaluated nodes
	 */
	private List<GraphNode<T>> evaluatedNodes = new ArrayList<GraphNode<T>>();

	/*
	 * The main constructor that has one parameter representing the callback
	 * mechanism used by this class to notify when a node gets the evaluation.
	 * 
	 * @param listener The callback interface implemented by the user classes
	 */
	public NodeValueListener<T> Graph(NodeValueListener<T> listener) {
		return this.listener = listener;
	}

	/*
	 * Allows adding of new dependicies to the graph. "evalFirstValue" needs to be
	 * evaluated before "evalAfterValue"
	 * 
	 * @param evalFirstValue The parameter that needs to be evaluated first
	 * 
	 * @param evalAfterValue The parameter that needs to be evaluated after
	 */
	public void addDependency(T evalFirstValue, T evalAfterValue) {
		GraphNode<T> firstNode = null;
		GraphNode<T> afterNode = null;
		if (nodes.containsKey(evalFirstValue)) {
			firstNode = nodes.get(evalFirstValue);
		} else {
			firstNode = createNode(evalFirstValue);
			nodes.put(evalFirstValue, firstNode);
		}
		if (nodes.containsKey(evalAfterValue)) {
			afterNode = nodes.get(evalAfterValue);
		} else {
			afterNode = createNode(evalAfterValue);
			nodes.put(evalAfterValue, afterNode);
		}
		firstNode.addGoingOutNode(afterNode);
		afterNode.addComingInNode(firstNode);
	}

	/*
	 * Creates a graph node of the <T> generic type
	 * 
	 * @param value The value that is hosted by the node
	 * 
	 * @return a generic GraphNode object
	 */
	private GraphNode<T> createNode(T value) {
		GraphNode<T> node = new GraphNode<T>();
		node.value = value;
		return node;
	}

	/*
	 * Get the node that has not a parent
	 * 
	 */
	public List<GraphNode<T>> getOrphanNodes() {
		List<GraphNode<T>> orphanNodes = null;
		Set<T> keys = nodes.keySet();
		for (T key : keys) {
			GraphNode<T> node = nodes.get(key);
			if (node.getComingInNodes() == null && node.getComingInNodes() == null) {
				if (orphanNodes == null) {
					orphanNodes = new ArrayList<>();
				}
				orphanNodes.add(node);
			}
		}
		return orphanNodes;
	}

	/**
	 * 
	 * @return
	 */
	public List<GraphNode<T>> getLeafNodes() {
		List<GraphNode<T>> leafNodes = new ArrayList<>();
		Set<T> keys = nodes.keySet();
		for (T key : keys) {
			GraphNode<T> node = nodes.get(key);
			if (node.getGoingOutNodes() == null) {
				leafNodes.add(node);
			}
		}
		return leafNodes;
	}

	public List<GraphNode<T>> deploymentOrder() {
		List<GraphNode<T>> currentNodes = getLeafNodes();
		List<GraphNode<T>> orphnanNodes = getOrphanNodes();
		for (GraphNode<T> m : orphnanNodes) {
			LOGGER.info("Orphans: " + m.value);
		}
		List<GraphNode<T>> newleafNodes = new ArrayList<>();
		while (!currentNodes.isEmpty()) {
			for (GraphNode<T> g : currentNodes) {
				if (g.getComingInNodes() != null) {
					List<GraphNode<T>> realLeafs = g.getComingInNodes();
					realLeafs.removeAll(orphnanNodes);
					newleafNodes.addAll(new ArrayList<>(realLeafs));
				}
			}
			// remove linked graphs first
			List<GraphNode<T>> linkedGraphs = getLinkedGraphs(newleafNodes);
			// Add linked graphs after
			newleafNodes.addAll(new ArrayList<GraphNode<T>>(linkedGraphs));
			for (GraphNode<T> n : currentNodes) {
				if (!this.deploymentOrder.contains(n)) {
					this.deploymentOrder.add(n);
				}
			}
			currentNodes = new ArrayList<GraphNode<T>>(newleafNodes);
			newleafNodes = new ArrayList<GraphNode<T>>();
		}
		for (GraphNode<T> n : orphnanNodes) {
			if (!this.deploymentOrder.contains(n)) {
				this.deploymentOrder.add(n);
			}
		}
		return this.deploymentOrder;
	}

	/**
	 * 
	 * @param graphs
	 * @return
	 */
	public synchronized List<GraphNode<T>> getLinkedGraphs(List<GraphNode<T>> graphs) {
		List<GraphNode<T>> linkedGraphs = new ArrayList<>();
		for (GraphNode<T> firstVal : graphs) {
			for (GraphNode<T> g : graphs) {
				if (g.getComingInNodes().contains(firstVal)) {
					linkedGraphs.add(firstVal);
				}
			}
		}
		graphs.removeAll(linkedGraphs);
		return linkedGraphs;
	}

	public boolean isAlreadyEvaluated(GraphNode<T> node) {
		return evaluatedNodes.contains(node);
	}

	public boolean areAlreadyEvaluated(List<GraphNode<T>> nodes) {
		return evaluatedNodes.containsAll(nodes);
	}

}
