package com.dokebi.dalkom.domain.user.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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

	// USER-003 (사용자 정보 수정)
	@PutMapping("/api/user")
	@ResponseStatus(HttpStatus.OK)
	public Response updateUser(@LoginUser Long userSeq, @Valid @RequestBody UserUpdateRequest req) {
		return userService.updateUser(userSeq, req);
	}

	@GetMapping("/api/user")
	@ResponseStatus(HttpStatus.OK)
	public Response readUserList(Pageable pageable) {
		return Response.success(userService.readUserList(pageable));
	}
}
