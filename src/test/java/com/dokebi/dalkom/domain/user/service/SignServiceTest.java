package com.dokebi.dalkom.domain.user.service;

import static com.dokebi.dalkom.domain.user.factory.SignUpRequestFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
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
import com.dokebi.dalkom.domain.jira.exception.MissingJiraRequestHeaderException;
import com.dokebi.dalkom.domain.user.dto.LogInRequest;
import com.dokebi.dalkom.domain.user.dto.LogInUserResponse;
import com.dokebi.dalkom.domain.user.dto.SignUpRequest;
import com.dokebi.dalkom.domain.user.dto.SignUpResponse;
import com.dokebi.dalkom.domain.user.entity.Employee;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.exception.UserEmailAlreadyExistsException;
import com.dokebi.dalkom.domain.user.exception.UserNotFoundException;
import com.dokebi.dalkom.domain.user.repository.EmployeeRepository;
import com.dokebi.dalkom.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class SignServiceTest {

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

	@InjectMocks
	private SignService signService;

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
		when(employeeRepository.findByEmpId(anyString()))
			.thenReturn(Optional.of(new Employee("emp123", "existing@example.com", "Existing User", LocalDate.now())));
		// UserRepository의 existsByEmail 호출 결과를 모킹하여 이메일 중복 상황을 시뮬레이션
		when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);

		assertThrows(UserEmailAlreadyExistsException.class, () -> signService.signUp(request));

		// EmployeeRepository와 UserRepository에 대한 모킹 호출 검증
		verify(employeeRepository).findByEmpId("emp123");
		verify(userRepository).existsByEmail("existing@example.com");
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
}
