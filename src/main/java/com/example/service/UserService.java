package com.example.service;

import java.util.Optional;

import com.example.model.User;

public interface UserService {

	public Optional<User> findById(Long id);
	public User saveUser(User user);

}
