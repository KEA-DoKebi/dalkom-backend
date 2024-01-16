package com.dokebi.dalkom.domain.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.admin.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController {
	private final AdminService adminService;

	@GetMapping("/api/admin")
	@ResponseStatus(HttpStatus.OK)
	public Response findAdmin() {
		return Response.success(adminService.readAll());
	}
}