package com.dokebi.dalkom.domain.user.handler;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

	public String validate(String encodedKey, String token) {
		try {
			Jws<Claims> claims = parse(encodedKey, token);
			if (claims == null)
				return null;
			return extractToken(claims);

		} catch (JwtException e) {
			return null;
		}
	}

	public String extractToken(Jws<Claims> claims) {
		return claims.getBody().getSubject();
	}

	public Jws<Claims> parse(String key, String token) {
		try {
			System.out.println("===토큰 유효====");
			return Jwts.parser().setSigningKey(key).parseClaimsJws(untype(token));
		} catch (ExpiredJwtException e) {
			System.out.println("===토큰 만료===");
			return null;

		}

	}

	private String untype(String token) {
		return token.substring(type.length());
	}

}
