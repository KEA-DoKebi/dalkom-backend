package com.dokebi.dalkom.domain.user.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	public Response updateUser(@LoginUser Long userSeq, @Valid @RequestBody UserUpdateRequest request) {
		userService.updateUserByUserSeq(userSeq, request);

		return Response.success();
	}

	// USER-004 (사용자 정보 조회)
	@GetMapping("/api/user")
	@ResponseStatus(HttpStatus.OK)
	public Response readUserList(Pageable pageable) {
		return Response.success(userService.readUserList(pageable));
	}

	// USER-006 (사용자 정보 조회 검색)
	@GetMapping("/api/user/search")
	@ResponseStatus(HttpStatus.OK)
	public Response readUserListSearch(
		@RequestParam(required = false) String email,
		@RequestParam(required = false) String nickname,
		Pageable pageable) {
		return Response.success(userService.readUserListSearch(email, nickname, pageable));
	}

	// USER-007 (사용자 정보 조회(자신))
	@GetMapping("/api/user/self")
	@ResponseStatus(HttpStatus.OK)
	public Response readUserSelfDetail(@LoginUser Long userSeq) {
		return Response.success(userService.readUserSelfDetail(userSeq));
	}
}
