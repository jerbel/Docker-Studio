package org.eclipse.cmf.occi.docker.connector.observer;

import java.util.LinkedList;

/**
 * This class implements Queue with size 2
 */
public class LimitedQueue<E> extends LinkedList<E> {

	private static final long serialVersionUID = -4826882850494874079L;
	private int limit;
	public LimitedQueue(int limit) {
		this.limit = limit;
	}
	
	@Override
	public boolean add(E o) {
		super.add(o);
		while (size() > limit) {
			super.remove();
		}
		return true;
	}
	
}
