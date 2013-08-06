package com.ubc.ca.exception;

public class NoStockException extends Exception {
	public NoStockException() {
		super("Existing Stock less than Requested quantity");
	}

}
