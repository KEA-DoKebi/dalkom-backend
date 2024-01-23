package com.dokebi.dalkom.domain.admin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.admin.dto.AdminDto;
import com.dokebi.dalkom.domain.admin.dto.CreateAdminRequest;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.factory.CreateAdminRequestFactory;
import com.dokebi.dalkom.domain.admin.repository.AdminRepository;
import com.dokebi.dalkom.domain.user.exception.UserNicknameAlreadyExistsException;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
	@InjectMocks
	private AdminService adminService;
	@Mock
	private AdminRepository adminRepository;
	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	void readAdminListTest() {
		// Given
		Admin admin1 = new Admin("adminId", "password",
			"nickname", "name", "depart");
		Admin admin2 = new Admin("adminId", "password",
			"nickname", "name", "depart");
		Admin admin3 = new Admin("adminId", "password",
			"nickname", "name", "depart");

		List<Admin> adminList = Arrays.asList(admin1, admin2, admin3);

		List<AdminDto> adminDtoList = new ArrayList<>();
		for(Admin admin : adminList) {
			AdminDto adminDto = AdminDto.toDto(admin);
			adminDtoList.add(adminDto);
		}

		when(adminRepository.findAll()).thenReturn(adminList);

		// When
		List<AdminDto> result = adminService.readAdminList();

		// Then
		for(int i = 0; i < result.size(); i++) {
			AdminDto adminDto1 = result.get(i);
			AdminDto adminDto2 = adminDtoList.get(i);

			assertEquals(adminDto1, adminDto2);
		}
	}

	@Test
	void createAdminTest_Success() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory.createCreateAdminRequest();

		when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
		Admin admin = CreateAdminRequest.toEntity(request);

		// When
		adminRepository.save(admin);

		Response response = adminService.createAdmin(request);

		// Then
		assertTrue(response.isSuccess());
	}

	@Test
	void createAdminTest_Failure() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory.createCreateAdminRequest();

		when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

		when(adminRepository.save(any(Admin.class))).thenThrow(UserNicknameAlreadyExistsException.class);

		// When
		Response response = adminService.createAdmin(request);

		// Then
		assertFalse(response.isSuccess());
	}

	// 해당 메소드는 private로 선언되어 있기에 간접적으로 테스트를 진행
	@Test
	void validateNicknameTest_Success() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory.createCreateAdminRequest();

		when(adminRepository.existsByNickname(request.getNickname())).thenReturn(false);

		// When
		Response response = adminService.createAdmin(request);

		// Then
		assertTrue(response.isSuccess());
	}

	@Test
	void validateNicknameTest_Failure() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory.createCreateAdminRequest();

		when(adminRepository.existsByNickname(request.getNickname())).thenReturn(true);

		// When
		Response response = adminService.createAdmin(request);

		// Then
		assertFalse(response.isSuccess());
	}

	@Test
	void readAdminByAdminSeqTest() {
		// Given
		Long adminSeq = 1L;
		Admin admin = new Admin(
			"adminId",
			"password",
			"nickname",
			"name",
			"depart"
		);

		when(adminRepository.findByAdminSeq(adminSeq)).thenReturn(Optional.of(admin));

		// When
		Admin result = adminService.readAdminByAdminSeq(adminSeq);

		// Then
		assertNotNull(result);
		assertEquals(result, admin);
	}

	@Test
	void readAdminByAdminIdTest() {
		// Given
		String adminId = "adminId";

		Admin admin = new Admin(
			"adminId",
			"password",
			"nickname",
			"name",
			"depart"
		);

		when(adminRepository.findByAdminId(adminId)).thenReturn(Optional.of(admin));

		// When
		Admin result = adminService.readAdminByAdminId(adminId);

		// Then
		assertNotNull(result);
		assertEquals(result, admin);
	}
}
