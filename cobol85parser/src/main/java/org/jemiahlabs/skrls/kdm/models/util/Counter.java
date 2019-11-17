package org.jemiahlabs.skrls.kdm.models.util;

public class Counter {
	private static final Counter globalCounter = new Counter(0);
	private int count;
	
	public static Counter getGlobalCounter() {
		return globalCounter;
	}
	
	public static Counter getLocalCounter() {
		return getLocalCounter(0);
	}
	
	public static Counter getLocalCounter(int initialCount) {
		return new Counter(initialCount);
	}
	
	private Counter(int initialCount) {
		count = initialCount;
	}
	
	public void reset(int initialCount) {
		count = initialCount;
	}
	
	public void reset() {
		reset(0);
	}
	
	public int increment() {
		return count++;
	}
}
