package com.dokebi.dalkom.domain.user.config.token;

import com.dokebi.dalkom.domain.user.handler.JwtHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenHelper {

	private final JwtHandler jwtHandler;
	private final String key;
	private final long maxAgeSeconds;
}
