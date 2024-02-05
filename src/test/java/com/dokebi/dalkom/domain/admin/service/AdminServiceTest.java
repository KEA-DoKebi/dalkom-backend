package com.dokebi.dalkom.domain.admin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dokebi.dalkom.domain.admin.dto.AdminDashboardResponse;
import com.dokebi.dalkom.domain.admin.dto.CreateAdminRequest;
import com.dokebi.dalkom.domain.admin.dto.MonthlyCategoryListDto;
import com.dokebi.dalkom.domain.admin.dto.MonthlyPriceListDto;
import com.dokebi.dalkom.domain.admin.dto.MonthlyProductListDto;
import com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.factory.CreateAdminRequestFactory;
import com.dokebi.dalkom.domain.admin.repository.AdminRepository;
import com.dokebi.dalkom.domain.user.dto.SignUpRequest;
import com.dokebi.dalkom.domain.user.entity.Employee;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.factory.UserFactory;
import com.dokebi.dalkom.domain.user.repository.EmployeeRepository;
import com.dokebi.dalkom.domain.user.repository.UserRepository;
import com.dokebi.dalkom.domain.user.service.SignService;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
	@InjectMocks
	private AdminService adminService;
	@Mock
	private SignService signService;
	@Mock
	private AdminRepository adminRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private EmployeeRepository employeeRepository;
	@Mock
	private PasswordEncoder passwordEncoder;

	// ADMIN-001 (유저 비활성화)
	@Test
	void updateUserTest() {
		// Given
		Long userSeq = 1L;
		User user = UserFactory.createMockUser();
		user.setState("Y");

		when(userRepository.findById(userSeq)).thenReturn(Optional.of(user));

		// When
		adminService.updateUser(userSeq);

		// Then
		assertEquals(user.getState(), "N");
	}

	// ADMIN-006 (관리자 생성)
	@Test
	void createAdminTest() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory.createCreateAdminRequest();

		when(adminRepository.existsByNickname(request.getNickname())).thenReturn(false);
		when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

		// When
		adminService.createAdmin(request);

		// Then
		verify(adminRepository).save(any());
	}

	// ADMIN-007 (관리자 목록 조회)
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

		Page<ReadAdminResponse> page = new PageImpl<>(readAdminResponseList, pageable, readAdminResponseList.size());

		when(adminRepository.findAllAdminList(pageable)).thenReturn(page);

		// When
		Page<ReadAdminResponse> result = adminService.readAdminList(pageable);

		// Then
		for (int i = 0; i < readAdminResponseList.size(); i++) {
			ReadAdminResponse readAdminResponse1 = result.getContent().get(i);
			ReadAdminResponse readAdminResponse2 = readAdminResponseList.get(i);

			assertEquals(readAdminResponse1, readAdminResponse2);
		}
	}

	// ADMIN-008 (관리자 유저 생성)
	@Test
	void createUserTest() {
		// Given
		SignUpRequest request = new SignUpRequest(
			"DKT201735826", "temp@gmail.com", "123456a!", "홍길동",
			"HGD", "성남시 가천대", LocalDate.of(2024, 2, 5), 120000
		);
		Employee employee = new Employee(
			"DKT201735826", "temp@gmail.com", "홍길동", LocalDate.of(2024, 2, 5)
		);
		when(employeeRepository.findByEmpId(request.getEmpId())).thenReturn(Optional.of(employee));
		when(signService.calculateMileage(any(LocalDate.class))).thenReturn(100); // 예시 값

		when(userRepository.existsByEmail(anyString())).thenReturn(false);
		when(userRepository.existsByNickname(anyString())).thenReturn(false);

		// When
		adminService.createUser(request);

		// Then - 정확한 값을 넣으면 toEntity로 인해 다른 객체가 생겨버리는 바람에 오류가 발생
		verify(userRepository).save(any());
	}

	// ADMIN-009 (관리자 대시보드)
	@Test
	void testReadDashboard() {
		// Given
		when(adminRepository.findTotalPrice()).thenReturn(1000);
		when(adminRepository.findTotalMonthlyPrice()).thenReturn(500);
		when(adminRepository.findTotalDailyPrice()).thenReturn(100);

		when(adminRepository.findMonthlyPriceList()).thenReturn(
			Arrays.asList(new MonthlyPriceListDto(), new MonthlyPriceListDto()));
		when(adminRepository.findMonthlyCategoryList()).thenReturn(
			Arrays.asList(new MonthlyCategoryListDto(), new MonthlyCategoryListDto()));

		List<MonthlyProductListDto> monthlyProductListContent = Arrays.asList(new MonthlyProductListDto(),
			new MonthlyProductListDto());
		Page<MonthlyProductListDto> monthlyProductListPage = new PageImpl<>(monthlyProductListContent);
		Pageable topFive = PageRequest.of(0, 5);
		when(adminRepository.findMonthlyProductList(topFive)).thenReturn(monthlyProductListPage);

		// When
		AdminDashboardResponse actualResponse = adminService.readDashboard();

		// Then
		assertAll(
			() -> assertEquals(1000, actualResponse.getTotalMileage()),
			() -> assertEquals(500, actualResponse.getTotalMonthlyMileage()),
			() -> assertEquals(100, actualResponse.getTotalDailyMileage()),
			() -> assertEquals(2, actualResponse.getMonthlyPriceList().size()),
			() -> assertEquals(2, actualResponse.getMonthlyCategoryList().size()),
			() -> assertEquals(2, actualResponse.getMonthlyProductList().getContent().size())
		);
	}

	// ADMIN-010 (관리자 목록 조회 검색) - 이름
	@Test
	void readAdminListSearchNameTest() {
		// Given
		String name = "John";
		Pageable pageable = Pageable.unpaged();
		when(adminRepository.findAdminListByName(name, pageable)).thenReturn(Page.empty());

		// When
		Page<ReadAdminResponse> result =
			adminService.readAdminListSearch(name, null, null, null, pageable);

		// Then
		assertNotNull(result);
		verify(adminRepository).findAdminListByName(name, pageable);
	}

	// ADMIN-010 (관리자 목록 조회 검색) - adminId
	@Test
	void readAdminListSearchAdminIdTest() {
		// Given
		String adminId = "admin123";
		Pageable pageable = Pageable.unpaged();
		when(adminRepository.findAdminListByAdminId(adminId, pageable)).thenReturn(Page.empty());

		// When
		Page<ReadAdminResponse> result =
			adminService.readAdminListSearch(null, adminId, null, null, pageable);

		// Then
		assertNotNull(result);
		verify(adminRepository).findAdminListByAdminId(adminId, pageable);
	}

	// ADMIN-010 (관리자 목록 조회 검색) - depart
	@Test
	void readAdminListSearchDepartTest() {
		// Given
		String depart = "HR";
		Pageable pageable = Pageable.unpaged();
		when(adminRepository.findAdminListByDepart(depart, pageable)).thenReturn(Page.empty());

		// When
		Page<ReadAdminResponse> result =
			adminService.readAdminListSearch(null, null, depart, null, pageable);

		// Then
		assertNotNull(result);
		verify(adminRepository).findAdminListByDepart(depart, pageable);
	}

	// ADMIN-010 (관리자 목록 조회 검색) - 닉네임
	@Test
	void readAdminListSearchNicknameTest() {
		// Given
		String nickname = "johndoe";
		Pageable pageable = Pageable.unpaged();
		when(adminRepository.findAdminListByNickname(nickname, pageable)).thenReturn(Page.empty());

		// When
		Page<ReadAdminResponse> result =
			adminService.readAdminListSearch(null, null, null, nickname, pageable);

		// Then
		assertNotNull(result);
		verify(adminRepository).findAdminListByNickname(nickname, pageable);
	}

	// ADMIN-010 (관리자 목록 조회 검색)
	@Test
	void readAdminListSearchTest() {
		// Given
		Pageable pageable = Pageable.unpaged();
		when(adminRepository.findAllAdminList(pageable)).thenReturn(Page.empty());

		// When
		Page<ReadAdminResponse> result =
			adminService.readAdminListSearch(null, null, null, null, pageable);

		// Then
		assertNotNull(result);
		verify(adminRepository).findAllAdminList(pageable);
	}
}
