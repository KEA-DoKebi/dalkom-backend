package com.dokebi.dalkom.domain.user.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SignController {
	// private final SignService signService;
	//
	// @PostMapping("/api/auth/sign-up")
	// @ResponseStatus(HttpStatus.CREATED)
	// public Response signUp(@Valid @RequestBody SignUpRequest req) {
	// 	signService.signUp(req);
	// 	return success();
	// }
}
