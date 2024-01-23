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
		AdminDto adminDto = AdminDto.toDto(admin);

		// Then
		assertNotNull(adminDto);
		assertEquals(admin.getAdminSeq(), adminDto.getAdminSeq());
		assertEquals(admin.getAdminId(), adminDto.getAdminId());
		assertEquals(admin.getRole(), adminDto.getRole());
		assertEquals(admin.getNickname(), adminDto.getNickname());
		assertEquals(admin.getName(), adminDto.getName());
		assertEquals(admin.getDepart(), adminDto.getDepart());
	}
}
