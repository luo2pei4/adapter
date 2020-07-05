package com.lp.adapter.core;

public class ThreadGroupInformation {

	private static ThreadGroup threadGroup = new ThreadGroup("businessThreadGroup");

	public static ThreadGroup getThreadGroup() {

		return threadGroup;
	}

	public static int getCurrentActiveThreadCount() {
		
		return threadGroup.activeCount();
	}
	
	public static String getSingleThreadName() {
		
		if (threadGroup.activeCount() == 1) {
			
			Thread[] arrThread = new Thread[1];
			threadGroup.enumerate(arrThread);
			
			return arrThread[0].getName();
		}

		return null;
	}
}
