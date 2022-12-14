package com.fmontalvoo.springboot.jwt.app.auth.service;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;

public interface JwtService {

	public String create(Authentication authentication) throws IOException;

	public String resolve(String token);

	public Claims getClaims(String token);

	public boolean validate(String token);

	public String getUsername(String token);

	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException;
}
