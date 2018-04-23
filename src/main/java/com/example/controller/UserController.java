package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	public UserController() {
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping(value = "/user")
	public String getUser() {
		return "Hello World";
	}
}
