package com.dokebi.dalkom.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping("/api/users/sign-up")
	@ResponseStatus(HttpStatus.CREATED)
	public SignUpResponse signUp(@RequestBody SignUpRequest req) {
		return signService.signUp(req);
	}
}
