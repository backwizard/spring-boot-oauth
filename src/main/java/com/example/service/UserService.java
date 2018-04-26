package com.example.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.model.User;

public interface UserService extends UserDetailsService {

	public User saveUser(User user) throws RuntimeException;

}
