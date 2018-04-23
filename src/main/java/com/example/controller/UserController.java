package com.example.controller;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/api/v1/user")
	public User getUser() {
		return userService.findById(1L).get();
	}

	@PostMapping(value = "/register")
	public void register(@RequestBody User user, HttpServletResponse res) {
		userService.saveUser(user);
		res.addHeader("Authorization", "Bearer " + generateToken(String.valueOf(user.getId()), user.getUsername()));
	}

	public static String generateToken(String id, String username) {
		return Jwts.builder().setId(id).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + 60000))
				.signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs".getBytes()).compact();
	}
}
