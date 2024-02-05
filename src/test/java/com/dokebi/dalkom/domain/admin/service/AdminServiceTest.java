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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dokebi.dalkom.domain.admin.dto.CreateAdminRequest;
import com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse;
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
		Pageable pageable = PageRequest.of(0, 3);
		Admin admin1 = new Admin("adminId", "password",
			"nickname", "name", "depart");
		Admin admin2 = new Admin("adminId", "password",
			"nickname", "name", "depart");
		Admin admin3 = new Admin("adminId", "password",
			"nickname", "name", "depart");

		List<Admin> adminList = Arrays.asList(admin1, admin2, admin3);

		List<ReadAdminResponse> readAdminResponseList = new ArrayList<>();
		for (Admin admin : adminList) {
			ReadAdminResponse readAdminResponse = ReadAdminResponse.toDto(admin);
			readAdminResponseList.add(readAdminResponse);
		}

		when(adminRepository.findAll()).thenReturn(adminList);

		// When
		Page<ReadAdminResponse> result = adminService.readAdminList(pageable);

		// Then
		for (int i = 0; i < readAdminResponseList.size(); i++) {
			ReadAdminResponse readAdminResponse1 = result.getContent().get(i);
			ReadAdminResponse readAdminResponse2 = readAdminResponseList.get(i);

			assertEquals(readAdminResponse1, readAdminResponse2);
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

		//Response response = adminService.createAdmin(request);

		// Then
		//assertTrue(response.isSuccess());
	}

	@Test
	void createAdminTest_Failure() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory.createCreateAdminRequest();

		when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

		when(adminRepository.save(any(Admin.class))).thenThrow(UserNicknameAlreadyExistsException.class);

		// When
		//Response response = adminService.createAdmin(request);

		// Then
		//assertFalse(response.isSuccess());
	}

	// 해당 메소드는 private로 선언되어 있기에 간접적으로 테스트를 진행
	@Test
	void validateNicknameTest_Success() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory.createCreateAdminRequest();

		when(adminRepository.existsByNickname(request.getNickname())).thenReturn(false);

		// When
		//Response response = adminService.createAdmin(request);

		// Then
		//assertTrue(response.isSuccess());
	}

	@Test
	void validateNicknameTest_Failure() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory.createCreateAdminRequest();

		when(adminRepository.existsByNickname(request.getNickname())).thenReturn(true);

		// When
		//Response response = adminService.createAdmin(request);

		// Then
		//assertFalse(response.isSuccess());
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
