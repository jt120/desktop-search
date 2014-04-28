package com.jt.desktop.util;

public class ClockUtil {
	
	private static long cost = 0L;
	
	public static void start() {
		cost = System.currentTimeMillis();
	}
	
	public static void end() {
		long end = System.currentTimeMillis();
		cost = end - cost;
		System.out.println("cost: " + cost/1000 + " seconds");
	}
	
}
