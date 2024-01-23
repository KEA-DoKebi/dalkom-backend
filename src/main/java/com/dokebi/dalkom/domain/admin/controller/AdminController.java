package com.dokebi.dalkom.domain.admin.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.admin.dto.CreateAdminRequest;
import com.dokebi.dalkom.domain.admin.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController {
	private final AdminService adminService;

	// ADMIN-005 (관리자 생성)
	@PostMapping("/api/admin")
	@ResponseStatus(HttpStatus.OK)
	public Response createAdmin(@Valid @RequestBody CreateAdminRequest request) {
		return adminService.createAdmin(request);
	}

	// ADMIN-006 (관리자 목록 조회)
	@GetMapping("/api/admin")
	@ResponseStatus(HttpStatus.OK)
	public Response readAdminList() {
		return Response.success(adminService.readAdminList());
	}
}
