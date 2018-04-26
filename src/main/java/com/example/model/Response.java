package com.example.model;

import lombok.Data;

@Data
public class Response {
	private int status;
	private String message;
	
	public Response(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
}
