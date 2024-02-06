package com.dokebi.dalkom.domain.option.controller;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dokebi.dalkom.domain.option.service.ProductOptionService;

@ExtendWith(MockitoExtension.class)
public class ProductOptionControllerTest {

	@InjectMocks
	private ProductOptionController productOptionController;

	@Mock
	private ProductOptionService productOptionService;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(productOptionController).build();
	}

	@Test
	@DisplayName("옵션 목록 조회 테스트")
	void readOptionListByCategoryTest() throws Exception {
		// Given

		// When, Then
		mockMvc.perform(get("/api/option")).andExpect(status().isOk());

		verify(productOptionService).readOptionList();
	}

	@Test
	@DisplayName("옵션 상세 조회 테스트")
	void readOptionDetailListByOptionCodeTest() throws Exception {
		// Given
		String optionCode = "OP1";

		// When, Then
		mockMvc.perform(get("/api/option/{optionCode}", optionCode)).andExpect(status().isOk());

		verify(productOptionService).readOptionDetailList(optionCode);
	}
}
