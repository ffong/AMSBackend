package com.ubc.ca.exception;

public class InvalidLoginException extends Exception{
	
	public InvalidLoginException() {
		// TODO Auto-generated constructor stub
		super("Invalid UserId or Password. Please provide valid credentials");
	}
	
	public InvalidLoginException(String msg ) {
		// TODO Auto-generated constructor stub
		super(msg);
	}
	

}
