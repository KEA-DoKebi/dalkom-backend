package com.dokebi.dalkom.domain.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.user.dto.AuthResponse;
import com.dokebi.dalkom.domain.user.handler.JwtHandler;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
	private final JwtHandler jwtHandler;
	private final RedisService redisService;

	@Value("${jwt.max-age.access}")
	private long accessTokenMaxAgeSeconds;

	@Value("${jwt.max-age.refresh}")
	private long refreshTokenMaxAgeSeconds;

	@Value("${jwt.key.access}")
	private String accessKey;
	@Value("${jwt.key.refresh}")
	private String refreshKey;

	public String createAccessToken(String subject) {
		return jwtHandler.createToken(accessKey, subject, accessTokenMaxAgeSeconds);
	}

	public String createRefreshToken(String subject) {
		return jwtHandler.createToken(refreshKey, subject, refreshTokenMaxAgeSeconds);
	}

	public String readRefreshToken(String token) {
		return redisService.readValues(token);
	}

	public AuthResponse decryptAccessToken(String accessToken) {
		Jws<Claims> claims = validateToken(accessKey, accessToken);

		String subject = jwtHandler.extractToken(claims);

		String role = "User";
		int length = claims.getBody().getSubject().split(",").length;
		if (length > 1) {
			role = "Admin";
		}

		return new AuthResponse(subject, role);
	}

	public Jws<Claims> validateToken(String key, String token) {
		try {
			System.out.println("@@@@");
			System.out.println(key);
			System.out.println(token);
			Jws<Claims> claims = jwtHandler.parse(key, token);
			System.out.println(claims.toString());
			if (claims == null) {
				System.out.println("////////refreshToken//////////");
				//리프레시를 통해 다시 토큰 만들기
				//1. refreshToken 찾기
				String refreshToken = readRefreshToken(token);

				//2. refreshToken 해석해서 userSeq 찾기, refreshToken 유효검사
				String userSeq = jwtHandler.validate(refreshKey, refreshToken);
				// 리프레시도 만료인 경우
				if (userSeq == null) {
					return null;
				}

				//3. 새로운 accessToken 생성
				String accessToken = createAccessToken(userSeq);

				//4. 새로운 accessToken으로 요청
				claims = jwtHandler.parse(key, accessToken);
			}
			return claims;

		} catch (JwtException e) {
			return null;
		}
	}

}
