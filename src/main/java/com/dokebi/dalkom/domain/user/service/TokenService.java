package com.dokebi.dalkom.domain.user.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.user.handler.JwtHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
	private final JwtHandler jwtHandler;

	@Value("${jwt.max-age.access}")
	private long accessTokenMaxAgeSeconds;

	@Value("${jwt.max-age.refresh}")
	private long refreshTokenMaxAgeSeconds;

	@Value("${jwt.key.access}")
	private String accessKey;

	@Value("${jwt.key.refresh}")
	private String refreshKey;

	public String createAccessToken(String subject) {
		accessKey = Base64.getEncoder().encodeToString(accessKey.getBytes());
		return jwtHandler.createToken(accessKey, subject, accessTokenMaxAgeSeconds);
	}

	public String createRefreshToken(String subject) {
		refreshKey = Base64.getEncoder().encodeToString(refreshKey.getBytes());
		return jwtHandler.createToken(refreshKey, subject, refreshTokenMaxAgeSeconds);
	}
}
