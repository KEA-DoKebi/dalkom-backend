package com.dokebi.dalkom.domain.admin.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
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
import com.fasterxml.jackson.databind.ObjectMapper;

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
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("관리자 목록 조회 테스트")
	void readAdminListTest() throws Exception {
		// Given
		int page = 0; // 페이지 번호
		int size = 10; // 페이지 크기

		mockMvc.perform(get("/api/admin")
				.param("page", String.valueOf(page))
				.param("size", String.valueOf(size)))
			.andExpect(status().isOk());

		verify(adminService).readAdminList(any(Pageable.class));
	}

	@Test
	@DisplayName("관리자 생성 테스트")
	void createAdminTest() throws Exception {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory.createCreateAdminRequest();

		mockMvc.perform(post("/api/admin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
			.andExpect(status().isOk());

		verify(adminService).createAdmin(request);
	}
}
