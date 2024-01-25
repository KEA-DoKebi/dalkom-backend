package com.dokebi.dalkom.domain.admin.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
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

@RestController
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adminService;

	// ADMIN-005 (관리자 생성)
	@PostMapping("/api/admin")
	@ResponseStatus(HttpStatus.OK)
	public Response createAdmin(@Valid @RequestBody CreateAdminRequest request) {
		adminService.createAdmin(request);
		return Response.success();
	}

	// ADMIN-006 (관리자 목록 조회)
	@GetMapping("/api/admin")
	@ResponseStatus(HttpStatus.OK)
	public Response readAdminList(Pageable pageable) {
		return Response.success(adminService.readAdminList(pageable));
	}
}
