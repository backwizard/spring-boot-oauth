package com.example.controller;

import static com.example.security.SecurityConstants.HEADER_STRING;
import static com.example.security.SecurityConstants.TOKEN_PREFIX;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.HandlerException;
import com.example.model.Response;
import com.example.model.User;
import com.example.security.SecurityUtils;
import com.example.service.UserService;

@RestController
@RequestMapping(value = "/api/v1")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/user")
	public UserDetails getUser(@RequestParam("username") String username) throws HandlerException {
		return userService.loadUserByUsername(username);
	}

	@PostMapping(value = "/register")
	public ResponseEntity<Response> register(@Valid @RequestBody User user, HttpServletResponse res) throws HandlerException {
		userService.saveUser(user);
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + SecurityUtils.generateToken(user.getUsername()));
		return new ResponseEntity<Response>(new Response(HttpStatus.CREATED.value(), "Register success"), HttpStatus.CREATED);
	}
	
}
