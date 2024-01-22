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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dokebi.dalkom.domain.admin.dto.CreateAdminRequest;
import com.dokebi.dalkom.domain.admin.factory.CreateAdminRequestFactory;
import com.dokebi.dalkom.domain.admin.service.AdminService;
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
		mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
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
		mockMvc.perform(get("/api/admin"))
			.andExpect(status().isOk());

		verify(adminService).readAdminList();
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
