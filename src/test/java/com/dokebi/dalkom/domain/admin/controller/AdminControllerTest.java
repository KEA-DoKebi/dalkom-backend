package com.dokebi.dalkom.domain.admin.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.dokebi.dalkom.domain.admin.dto.CreateAdminRequest;
import com.dokebi.dalkom.domain.admin.factory.CreateAdminRequestFactory;
import com.dokebi.dalkom.domain.admin.service.AdminService;
import com.dokebi.dalkom.domain.user.config.LoginUser;
import com.dokebi.dalkom.domain.user.dto.SignUpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {
	@InjectMocks
	private AdminController adminController;
	@Mock
	private AdminService adminService;
	private MockMvc mockMvc;

	@BeforeEach
	void beforeEach() {
		this.mockMvc = MockMvcBuilders
			.standaloneSetup(adminController)
			.setCustomArgumentResolvers(
				new PageableHandlerMethodArgumentResolver(),
				new HandlerMethodArgumentResolver() {
					@Override
					public boolean supportsParameter(MethodParameter parameter) {
						return parameter.getParameterType().equals(Long.class) &&
							parameter.hasParameterAnnotation(LoginUser.class);
					}

					@Override
					public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
						NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
						return 1L; // 여기서는 userSeq를 1L로 가정하고 직접 제공
					}
				}
			)
			.build();
	}

	private String asJsonString(Object object) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule()); // Java 8 날짜/시간 지원 모듈 등록
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("ADMIN-001 (사용자 비활성화)")
	void updateUserTest() throws Exception {
		// Given
		Long userSeq = 1L;

		// When, Then
		mockMvc.perform(put("/api/admin/user/{userSeq}", userSeq))
			.andExpect(status().isOk());

		verify(adminService).updateUser(userSeq);
	}

	// ADMIN-003은 SIGN CONTROLLER에 있음
	// @Test
	// @DisplayName("ADMIN-003 (관리자 로그인) 테스트")
	// void signInAdminTest() throws Exception {
	// 	// Given
	// 	LogInAdminRequest request = new LogInAdminRequest("email@email.com", "123456a!");
	//
	// 	// When, Then
	// 	mockMvc.perform(post("/api/admin/login")
	// 			.contentType(MediaType.APPLICATION_JSON)
	// 			.content(asJsonString(request)))
	// 		.andExpect(status().isOk());
	//
	// 	verify(signService).signInAdmin(request);
	// }

	@Test
	@DisplayName("ADMIN-006 (관리자 생성)")
	void createAdminTest() throws Exception {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory.createCreateAdminRequest();

		// When, Then
		mockMvc.perform(post("/api/admin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
			.andExpect(status().isOk());

		verify(adminService).createAdmin(request);
	}

	@Test
	@DisplayName("ADMIN-007 (관리자 목록 조회)")
	void readAdminListTest() throws Exception {
		// Given
		int page = 0; // 페이지 번호
		int size = 10; // 페이지 크기
		Pageable pageable = PageRequest.of(0, 10);

		// When, Then
		mockMvc.perform(get("/api/admin")
				.param("page", String.valueOf(page))
				.param("size", String.valueOf(size)))
			.andExpect(status().isOk());

		verify(adminService).readAdminList(pageable);
	}

	@Test
	@DisplayName("ADMIN-008 (관리자 유저 생성)")
	void createUserTest() throws Exception {
		// Given
		SignUpRequest request = new SignUpRequest(
			"empId",
			"email@email.com",
			"123456a!",
			"류창민",
			"RCM",
			"대한민국 어딘가",
			LocalDate.of(2024, 2, 5),
			120000
		);

		// When, Then
		mockMvc.perform(post("/api/admin/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
			.andExpect(status().isOk());

		verify(adminService).createUser(request);
	}

	@Test
	@DisplayName("ADMIN-009 (관리자 대쉬보드)")
	void readDashboardTest() throws Exception {
		// When, Then
		mockMvc.perform(get("/api/admin/dashboard"))
			.andExpect(status().isOk());

		verify(adminService).readDashboard();
	}

	@Test
	@DisplayName("ADMIN-010 (관리자 목록 조회 검색)")
	void readAdminListSearchTest() throws Exception {
		// Given
		String name = "John";
		String adminId = "admin123";
		String depart = "IT";
		String nickname = "johnny";
		int page = 0;
		int size = 10;
		Pageable pageable = PageRequest.of(0, 10);

		// When, Then
		mockMvc.perform(get("/api/admin/search")
				.param("name", name)
				.param("adminId", adminId)
				.param("depart", depart)
				.param("nickname", nickname)
				.param("page", String.valueOf(page))
				.param("size", String.valueOf(size)))
			.andExpect(status().isOk());

		verify(adminService).readAdminListSearch(name, adminId, depart, nickname, pageable);
	}
}
