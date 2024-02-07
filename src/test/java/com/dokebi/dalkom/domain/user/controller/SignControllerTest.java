package com.dokebi.dalkom.domain.user.controller;

import static com.dokebi.dalkom.domain.user.factory.SignUpRequestFactory.*;
import static com.dokebi.dalkom.domain.user.factory.SignUpResponseFactory.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.user.dto.LogInAdminRequest;
import com.dokebi.dalkom.domain.user.dto.LogInAdminResponse;
import com.dokebi.dalkom.domain.user.dto.LogInRequest;
import com.dokebi.dalkom.domain.user.dto.LogInUserResponse;
import com.dokebi.dalkom.domain.user.dto.SignUpRequest;
import com.dokebi.dalkom.domain.user.dto.SignUpResponse;
import com.dokebi.dalkom.domain.user.service.SignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
class SignControllerTest {

	@InjectMocks
	private SignController signController;

	@Mock
	private SignService signService;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		mockMvc = MockMvcBuilders.standaloneSetup(signController).build();
	}

	@Test
	@DisplayName("사용자 회원가입 처리")
	void signUp() throws Exception {
		// Given
		SignUpRequest request = createSignUpRequest();
		SignUpResponse response = createSignUpResponse();
		when(signService.signUp(any(SignUpRequest.class))).thenReturn(response);

		// When & Then
		mockMvc.perform(post("/api/user/sign-up")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk());

		verify(signService).signUp(any(SignUpRequest.class));
	}

	@Test
	@DisplayName("사용자 로그인 처리")
	void signIn() throws Exception {
		// Given
		LogInRequest request = new LogInRequest("username", "password");
		LogInUserResponse response = new LogInUserResponse("accessToken123", 1000);
		when(signService.signInUser(any(LogInRequest.class))).thenReturn(response);

		// When & Then
		mockMvc.perform(post("/api/user/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk());

		verify(signService).signInUser(any(LogInRequest.class));
	}

	@Test
	@DisplayName("관리자 로그인 처리")
	void signInAdmin() throws Exception {
		// Given
		LogInAdminRequest request = new LogInAdminRequest("adminUsername", "password");
		LogInAdminResponse response = new LogInAdminResponse("accessToken123", "ADMIN_ROLE", "Administrator");
		when(signService.signInAdmin(any(LogInAdminRequest.class))).thenReturn(response);

		// When & Then
		mockMvc.perform(post("/api/admin/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk());

		verify(signService).signInAdmin(any(LogInAdminRequest.class));
	}

	@Test
	@DisplayName("사용자 로그아웃 처리")
	void signOut() throws Exception {
		// Given
		when(signService.signOut(any(HttpServletRequest.class))).thenReturn(Response.success());

		// When & Then
		mockMvc.perform(post("/api/user/logout"))
			.andExpect(status().isOk());

		verify(signService).signOut(any(HttpServletRequest.class));
	}

	@Test
	@DisplayName("관리자 로그아웃 처리")
	void signOutAdmin() throws Exception {
		// Given
		when(signService.signOut(any(HttpServletRequest.class)))
			.thenReturn(Response.success());

		// When & Then
		mockMvc.perform(post("/api/admin/logout"))
			.andExpect(status().isOk());

		verify(signService).signOut(any(HttpServletRequest.class));
	}
}
