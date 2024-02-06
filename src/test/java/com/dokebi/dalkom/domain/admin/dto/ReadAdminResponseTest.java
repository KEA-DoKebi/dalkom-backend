package com.dokebi.dalkom.domain.admin.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dokebi.dalkom.domain.admin.entity.Admin;

class ReadAdminResponseTest {

	@Test
	@DisplayName("readAdminResponseTestGetter 테스트")
	void readAdminResponseTestGetter() {
		// Test data
		Long adminSeq = 1L;
		String adminId = "admin123";
		String role = "ROLE_ADMIN";
		String nickname = "admin";
		String name = "Admin User";
		String depart = "IT";

		// Create object using constructor
		ReadAdminResponse response = new ReadAdminResponse(adminSeq, adminId, role, nickname, name, depart);

		// Verify values using Getter methods
		assertEquals(adminSeq, response.getAdminSeq());
		assertEquals(adminId, response.getAdminId());
		assertEquals(role, response.getRole());
		assertEquals(nickname, response.getNickname());
		assertEquals(name, response.getName());
		assertEquals(depart, response.getDepart());
	}

	@Test
	@DisplayName("readAdminResponseToDto 테스트")
	void readAdminResponseToDto() {
		// Test data
		Admin admin = new Admin("administrator", "12345a!", "nickname",
			"name", "IT");

		// Convert to DTO using static method
		ReadAdminResponse response = ReadAdminResponse.toDto(admin);

		// Verify values
		assertEquals(admin.getAdminSeq(), response.getAdminSeq());
		assertEquals(admin.getAdminId(), response.getAdminId());
		assertEquals(admin.getRole(), response.getRole());
		assertEquals(admin.getNickname(), response.getNickname());
		assertEquals(admin.getName(), response.getName());
		assertEquals(admin.getDepart(), response.getDepart());
	}
}

