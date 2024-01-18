package com.dokebi.dalkom.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.user.config.LoginUser;
import com.dokebi.dalkom.domain.user.dto.UserUpdateRequest;
import com.dokebi.dalkom.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;

	@PutMapping("/api/user")
	@ResponseStatus(HttpStatus.OK)
	public Response updateUser(@LoginUser Long userSeq, @RequestBody UserUpdateRequest req) {
		return userService.updateUser(userSeq, req);
	}
}
