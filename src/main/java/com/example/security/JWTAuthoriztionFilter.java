package com.example.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class JWTAuthoriztionFilter extends BasicAuthenticationFilter {

	public JWTAuthoriztionFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		System.out.println("UsernamePasswordAuthenticationToken");
		System.out.println("request=====" + request);
		String token = request.getHeader("Authorization");
		if (token != null) {
			// parse the token.
			String user = Jwts.parser().setSigningKey("SecretKeyToGenJWTs".getBytes())
					.parseClaimsJws(token.replace("Bearer ", "")).getBody().getSubject();
			String id = Jwts.parser().setSigningKey("SecretKeyToGenJWTs".getBytes())
					.parseClaimsJws(token.replace("Bearer ", "")).getBody().getId();

			System.out.println("user :" + user);
			System.out.println("id :" + id);
			if (user != null && id != null) {
				return new UsernamePasswordAuthenticationToken(user, id, new ArrayList<>());
			}
			return null;
		}
		return null;
	}

}
