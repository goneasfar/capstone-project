package com.macneil.capstoneproject;
/*
 * THIS HAS NOT BEEN IMPLEMENTED YET
 */


public class Racket {
	private static int lastXCor;
	private static int lastYCor;
	/**
	 * @param lastXCor
	 * @param lastYCor
	 */
	public Racket(int lastXCor, int lastYCor) {
		super();
		Racket.lastXCor = lastXCor;
		Racket.lastYCor = lastYCor;
	}
	public int getLastXCor() {
		return lastXCor;
	}
	public void setLastXCor(int lastXCor) {
		Racket.lastXCor = lastXCor;
	}
	public int getLastYCor() {
		return lastYCor;
	}
	public void setLastYCor(int lastYCor) {
		Racket.lastYCor = lastYCor;
	}
	
	
}
