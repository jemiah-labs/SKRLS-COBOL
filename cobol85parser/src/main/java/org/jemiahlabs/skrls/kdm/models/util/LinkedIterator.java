package org.jemiahlabs.skrls.kdm.models.util;

import java.util.Iterator;
import java.util.List;

public class LinkedIterator<T> implements Iterator<T> {
	private int indexCurrent = 0;
	private int size;
	private List<T> nodes;
	
	public LinkedIterator(List<T> nodes) {
		this.nodes = nodes;
		size = nodes.size();
	}

	@Override
	public boolean hasNext() {
		return (indexCurrent + 1) < size;
	}
	
	public T CurrentValue() {
		if(indexCurrent == size) 
			return null;
		
		return nodes.get(indexCurrent++);
	}

	@Override
	public T next() {
		return nodes.get(indexCurrent + 1);
	}
}
