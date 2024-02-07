package com.dokebi.dalkom.domain.user.service;

import static com.dokebi.dalkom.domain.admin.factory.AdminFactory.*;
import static com.dokebi.dalkom.domain.user.factory.SignUpRequestFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.service.AdminService;
import com.dokebi.dalkom.domain.jira.exception.MissingJiraRequestHeaderException;
import com.dokebi.dalkom.domain.user.dto.LogInAdminRequest;
import com.dokebi.dalkom.domain.user.dto.LogInAdminResponse;
import com.dokebi.dalkom.domain.user.dto.LogInRequest;
import com.dokebi.dalkom.domain.user.dto.LogInUserResponse;
import com.dokebi.dalkom.domain.user.dto.SignUpRequest;
import com.dokebi.dalkom.domain.user.dto.SignUpResponse;
import com.dokebi.dalkom.domain.user.entity.Employee;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.exception.LoginFailureException;
import com.dokebi.dalkom.domain.user.exception.UserEmailAlreadyExistsException;
import com.dokebi.dalkom.domain.user.exception.UserNotFoundException;
import com.dokebi.dalkom.domain.user.repository.EmployeeRepository;
import com.dokebi.dalkom.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class SignServiceTest {
	@Mock
	AdminService adminService;
	@InjectMocks
	private SignService signService;
	@Mock
	private TokenService tokenService;

	@Mock
	private RedisService redisService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private HttpServletRequest request;

	@Test
	@DisplayName("사용자 로그인 성공")
	void signInUser_Success() {
		LogInRequest request = new LogInRequest("user@example.com", "password");
		User mockUser = mock(User.class);
		when(mockUser.getMileage()).thenReturn(1000);
		when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(mockUser));
		when(passwordEncoder.matches("password", mockUser.getPassword())).thenReturn(true);
		when(tokenService.createAccessToken(anyString())).thenReturn("accessToken");
		when(tokenService.createRefreshToken(anyString())).thenReturn("refreshToken");

		LogInUserResponse response = signService.signInUser(request);

		assertNotNull(response);
		assertEquals("accessToken", response.getAccessToken());
		assertEquals(1000, response.getMileage());
		verify(redisService).createValues("accessToken", "refreshToken");
	}

	@Test
	@DisplayName("사용자 로그인 실패 - 사용자를 찾을 수 없음")
	void signInUser_UserNotFound_Failure() {
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> {
			signService.signInUser(new LogInRequest("user@example.com", "password"));
		});
	}

	@Test
	@DisplayName("관리자 로그인 성공")
	void signInAdmin_Success() {
		// Given
		String adminId = "adminId";
		String password = "password";
		Admin admin = createMockAdmin();
		LogInAdminRequest logInAdminRequest = new LogInAdminRequest(adminId, password);

		when(adminService.readAdminByAdminId(anyString())).thenReturn(admin);
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
		when(tokenService.createAccessToken(anyString())).thenReturn("accessToken");
		when(tokenService.createRefreshToken(anyString())).thenReturn("refreshToken");

		// When
		LogInAdminResponse response = signService.signInAdmin(logInAdminRequest);

		// Then
		assertNotNull(response);
		assertEquals("accessToken", response.getAccessToken());
		verify(redisService).createValues("accessToken", "refreshToken");
	}

	@Test
	@DisplayName("관리자 로그인 실패 - 관리자를 찾을 수 없음")
	void signInAdmin_AdminNotFound_Failure() {
		// Given
		String adminId = "nonExistingAdmin";
		LogInAdminRequest logInAdminRequest = new LogInAdminRequest(adminId, "password");

		when(adminService.readAdminByAdminId(anyString())).thenThrow(new LoginFailureException());

		// When & Then
		assertThrows(LoginFailureException.class, () -> signService.signInAdmin(logInAdminRequest));
	}

	@Test
	@DisplayName("관리자 로그인 실패 - 비밀번호 불일치")
	void signInAdmin_WrongPassword_Failure() {
		// Given
		String adminId = "admin01";
		String wrongPassword = "wrongPassword";
		Admin admin = createMockAdmin();
		LogInAdminRequest logInAdminRequest = new LogInAdminRequest(adminId, wrongPassword);

		when(adminService.readAdminByAdminId(anyString())).thenReturn(admin);
		when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

		// When & Then
		assertThrows(LoginFailureException.class, () -> signService.signInAdmin(logInAdminRequest));
	}

	@Test
	@DisplayName("로그아웃 성공")
	void signOut_Success() {
		when(request.getHeader("AccessToken")).thenReturn("validToken");
		doNothing().when(redisService).deleteValues("validToken");

		Response response = signService.signOut(request);

		assertNotNull(response);
		assertTrue(response.isSuccess());
	}

	@Test
	@DisplayName("로그아웃 실패 - 토큰 누락")
	void signOut_MissingToken_Failure() {
		when(request.getHeader("AccessToken")).thenReturn(null);

		assertThrows(MissingJiraRequestHeaderException.class, () -> {
			signService.signOut(request);
		});
	}

	@Test
	@DisplayName("회원가입 성공")
	void signUp_Success() {
		SignUpRequest signUpRequest = createSignUpRequest();

		when(employeeRepository.findByEmpId(anyString())).thenReturn(
			Optional.of(new Employee("DKT123456", "user@example.com", "User Name", LocalDate.now())));
		when(userRepository.existsByEmail(anyString())).thenReturn(false);
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

		SignUpResponse response = signService.signUp(signUpRequest);

		assertNotNull(response);
		assertEquals("회원가입 성공", response.getMessage());
	}

	@Test
	@DisplayName("회원가입 실패 - 이메일 중복")
	void signUp_EmailAlreadyExists_Failure() {
		SignUpRequest request = new SignUpRequest();
		request.setEmpId("emp123");
		request.setEmail("existing@example.com");
		request.setName("Existing User");
		request.setJoinedAt(LocalDate.now());

		// EmployeeRepository의 findByEmpId 호출 결과를 모킹하여 EmployeeNotFoundException을 방지
		when(employeeRepository.findByEmpId(anyString())).thenReturn(
			Optional.of(new Employee("emp123", "existing@example.com", "Existing User", LocalDate.now())));
		// UserRepository의 existsByEmail 호출 결과를 모킹하여 이메일 중복 상황을 시뮬레이션
		when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

		assertThrows(UserEmailAlreadyExistsException.class, () -> signService.signUp(request));

		// EmployeeRepository와 UserRepository에 대한 모킹 호출 검증
		verify(employeeRepository).findByEmpId("emp123");
		verify(userRepository).existsByEmail("existing@example.com");
	}

	@Test
	@DisplayName("마일리지 지급 - 최대 한도")
	void calculateMaximumMileageTest() {
		// Given
		LocalDate joinedAt = LocalDate.of(2019, 6, 14);

		// When
		Integer mileage = signService.calculateMileage(joinedAt);

		// Then
		// 예상 마일리지 계산: (최대 지급 한도 1,200,000)
		int expectedMileage = 1_200_000;
		assertEquals(expectedMileage, mileage);
	}

	@Test
	@DisplayName("마일리지 지급 - 15월 이전")
	void calculateMileage_Before15th() {
		// Given
		int currentYear = LocalDate.now().getYear();
		int mileagePerMonth = 100_000; //10만
		int mileagePerYear = mileagePerMonth * 12;
		LocalDate startOfYear = LocalDate.of(currentYear, 1, 1);
		LocalDate joinedAt = LocalDate.of(2024, 1, 10);

		// When
		Integer mileage = signService.calculateMileage(joinedAt);

		// Then
		long monthsWorked = ChronoUnit.MONTHS.between(joinedAt.withDayOfMonth(1), LocalDate.now());
		int expectedMileage = (int)monthsWorked * mileagePerMonth;
		assertEquals(expectedMileage, mileage);
	}

	@Test
	@DisplayName("마일리지 지급 - 15월 이후")
	void calculateMileageTest_SameYear() {
		// Given
		int currentYear = LocalDate.now().getYear();
		int mileagePerMonth = 100_000; //10만
		int mileagePerYear = mileagePerMonth * 12;
		LocalDate startOfYear = LocalDate.of(currentYear, 1, 1);
		LocalDate joinedAt = LocalDate.of(currentYear - 1, 2, 16);

		// When
		Integer mileage = signService.calculateMileage(joinedAt);

		// Then
		// 예상 마일리지 계산: (1월 입사, 2월부터 시작)
		long monthsWorked = ChronoUnit.MONTHS.between(joinedAt.withDayOfMonth(1).plusMonths(1), startOfYear);
		int expectedMileage = Math.min((int)monthsWorked * mileagePerMonth + mileagePerYear, mileagePerYear);
		assertEquals(expectedMileage, mileage);
	}
}
