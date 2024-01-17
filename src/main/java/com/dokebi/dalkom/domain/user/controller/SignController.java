package com.dokebi.dalkom.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.user.dto.LogInRequest;
import com.dokebi.dalkom.domain.user.dto.SignUpRequest;
import com.dokebi.dalkom.domain.user.dto.SignUpResponse;
import com.dokebi.dalkom.domain.user.service.SignService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SignController {

	private final SignService signService;

	@PostMapping("/api/user/sign-up")
	@ResponseStatus(HttpStatus.OK)
	public SignUpResponse signUp(@RequestBody SignUpRequest req) {
		return signService.signUp(req);
	}

	@PostMapping("/api/user/login")
	@ResponseStatus(HttpStatus.OK)
	public Response signIn(@RequestBody LogInRequest req) {
		return Response.success(signService.signIn(req));
	}
}
