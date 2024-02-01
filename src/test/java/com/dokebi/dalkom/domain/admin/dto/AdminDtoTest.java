package com.dokebi.dalkom.domain.admin.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.dokebi.dalkom.domain.admin.entity.Admin;

public class AdminDtoTest {
	@Test
	void AdminDtoToDtoTest() {
		// Given
		Admin admin = new Admin(
			"adminId",
			"password",
			"nickname",
			"name",
			"depart"
		);

		// When
		ReadAdminResponse readAdminResponse = ReadAdminResponse.toDto(admin);

		// Then
		assertNotNull(readAdminResponse);
		assertEquals(admin.getAdminSeq(), readAdminResponse.getAdminSeq());
		assertEquals(admin.getAdminId(), readAdminResponse.getAdminId());
		assertEquals(admin.getRole(), readAdminResponse.getRole());
		assertEquals(admin.getNickname(), readAdminResponse.getNickname());
		assertEquals(admin.getName(), readAdminResponse.getName());
		assertEquals(admin.getDepart(), readAdminResponse.getDepart());
	}
}
