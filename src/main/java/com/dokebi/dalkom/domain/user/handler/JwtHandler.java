package com.dokebi.dalkom.domain.user.handler;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtHandler {
	private String type = "Bearer ";

	public String createToken(String encodedKey, String subject, long maxAgeSeconds) {
		Date now = new Date();
		return type + Jwts.builder()
			.setSubject(subject)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + maxAgeSeconds * 1000L))
			.signWith(SignatureAlgorithm.HS256, encodedKey)
			.compact();
	}

	public String extractSubjects(String encodedKey, String token) {
		return parse(encodedKey, token).getBody().getSubject();
	}

	public boolean validate(String encodedKey, String token) {
		try {
			parse(encodedKey, token);
			return true;

		} catch (JwtException e) {
			return false;
		}
	}

	private Jws<Claims> parse(String key, String token) {
		return Jwts.parser()
			.setSigningKey(key)
			.parseClaimsJws(untype(token));
	}

	private String untype(String token) {
		return token.substring(type.length());
	}
}
