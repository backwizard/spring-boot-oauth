package com.example.security;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class SecurityUtils {
	
	public static String generateToken(String id, String username) {
		return Jwts.builder().setId(id).setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + 60000))
				.signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs".getBytes()).compact();
	}

}
