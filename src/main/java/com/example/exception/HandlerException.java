package com.example.exception;

public class HandlerException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String errorMessage;

	@Override
	public String getMessage() {
		return errorMessage;
	}
	
	public HandlerException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	
	public HandlerException() {
		super();
	}

}
