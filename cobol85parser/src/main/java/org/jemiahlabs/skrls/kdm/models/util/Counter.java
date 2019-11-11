package org.jemiahlabs.skrls.kdm.models.util;

public class Counter {
	private static final Counter counterGlobal = new Counter(0);
	private int count;
	
	public static Counter getCounterGlobal() {
		return counterGlobal;
	}
	
	public static Counter getLocalCounter() {
		return getLocalCounter(0);
	}
	
	public static Counter getLocalCounter(int initialCount) {
		return new Counter(initialCount);
	}
	
	public Counter(int initialCount) {
		count = initialCount;
	}
	
	public int increment() {
		return count++;
	}
}
