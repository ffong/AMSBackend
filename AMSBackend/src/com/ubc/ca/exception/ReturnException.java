package com.ubc.ca.exception;

public class ReturnException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ReturnException(String msg) {
		super(msg);
	}
	
	public ReturnException() {
		super("Could not process return.");
	}

}
