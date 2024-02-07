package com.dokebi.dalkom.domain.category.controller;

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

import com.dokebi.dalkom.domain.category.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
	@InjectMocks
	private CategoryController categoryController;
	@Mock
	private CategoryService categoryService;
	private MockMvc mockMvc;

	@BeforeEach
	void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}

	private String asJsonString(Object object){
		try{
			return new ObjectMapper().writeValueAsString(object);
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("카테고리 목록 조회 - depth 0의 카테고리 리스트")
	void readCategoriesTest() throws Exception {
		mockMvc.perform(get("/api/category"))
			.andExpect(status().isOk());

		verify(categoryService).readCategoryList();
	}

	@Test
	@DisplayName("특정 카테고리의 서브 카테고리 목록 조회")
	void readSubCategoriesTest() throws Exception {
		Long categorySeq = 1L;

		mockMvc.perform(get("/api/category/{categorySeq}",categorySeq))
			.andExpect(status().isOk());

		verify(categoryService).readSubCategoryList(categorySeq);
	}
}
