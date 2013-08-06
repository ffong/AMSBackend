package com.ubc.ca.exception;

public class TooManyItemsFoundException extends Exception{
public TooManyItemsFoundException() {
	super(" Cannot Add item to cart . More than one Item found for item Description. Use Search ");
}

}
