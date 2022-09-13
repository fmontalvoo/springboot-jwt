package com.fmontalvoo.springboot.jwt.app.auth.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmontalvoo.springboot.jwt.app.auth.SimpleGrantedAuthorityMixin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService {

//	private final Logger logger = LoggerFactory.getLogger(getClass());

//	public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	public static final SecretKey SECRET_KEY = new SecretKeySpec("puSKhESaSid@D9^j~KS74ySSi4H`~d3F".getBytes(),
			SignatureAlgorithm.HS256.getJcaName());
	public static final long ACCESS_TOKEN_EXPIRE = 86400000L;

	@Override
	public String create(Authentication authentication) throws IOException {
		User user = (User) authentication.getPrincipal();

		Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();

		Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

		String token = Jwts.builder().setSubject(user.getUsername()).setClaims(claims).signWith(SECRET_KEY)
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE))
				.compact();

		return token;
	}

	@Override
	public String resolve(String token) {
		if (token != null && !token.isBlank() && token.startsWith("Bearer "))
			return token.replace("Bearer ", "");
		return "";
	}

	@Override
	public Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(resolve(token)).getBody();
	}

	@Override
	public boolean validate(String token) {
		try {
			this.getClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public String getUsername(String token) {
		return this.getClaims(token).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
		Object roles = this.getClaims(token).get("authorities");
		Collection<? extends GrantedAuthority> authorities = Arrays
				.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
						.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
		return authorities;
	}

}
