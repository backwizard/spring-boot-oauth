package com.example.exception;

import lombok.Data;

@Data
public class ErrorResponse {

	private int errorCode;
	private String message;
}