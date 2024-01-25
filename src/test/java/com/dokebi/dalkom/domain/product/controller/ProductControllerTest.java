package com.dokebi.dalkom.domain.product.controller;

import static org.mockito.BDDMockito.*;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.dokebi.dalkom.domain.product.dto.ProductCreateRequest;
import com.dokebi.dalkom.domain.product.factory.ProductCreateRequestFactory;
import com.dokebi.dalkom.domain.product.service.ProductService;
import com.dokebi.dalkom.domain.user.config.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
	@InjectMocks
	private ProductController productController;
	@Mock
	private ProductService productService;
	private MockMvc mockMvc;

	@BeforeEach
	void beforeEach() {
		this.mockMvc = MockMvcBuilders
			.standaloneSetup(productController)
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
	@DisplayName("상위 카테고리 별 상품 목록 조회 (PRODUCT-001)")
	public void readProductListByCategoryTest() throws Exception {
		// Given
		Long categorySeq = 1L;
		PageRequest pageable = PageRequest.of(0, 8);

		// When & Then
		mockMvc.perform(get("/api/product/category/{categorySeq}", categorySeq)
				.param("page", "0")
				.param("size", "8"))
			.andExpect(status().isOk());

		verify(productService).readProductListByCategory(categorySeq, pageable);
	}

	@Test
	@DisplayName("상품 상세 정보 조회 (PRODUCT-002)")
	public void readProductTest() throws Exception {
		// Given
		Long productSeq = 1L;

		// When & Then
		mockMvc.perform(get("/api/product/{productSeq}", productSeq))
			.andExpect(status().isOk());

		verify(productService).readProduct(productSeq);
	}

	@Test
	@DisplayName("상품 정보 추가 (PRODUCT-003)")
	public void createProductTest() throws Exception {
		// Given
		ProductCreateRequest request
			= ProductCreateRequestFactory.createProductCreateRequest();

		// When & Then
		mockMvc.perform(post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
			.andExpect(status().isOk());

		verify(productService).createProduct(request);
	}

	@Test
	@DisplayName("상품 리스트 조회 - 관리자 화면 (PRODUCT-004)")
	public void readAdminPageProductListTest() throws Exception {
		// Given
		PageRequest pageable = PageRequest.of(0, 10);

		// When & Then
		mockMvc.perform(get("/api/product")
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk());

		verify(productService).readAdminPageProductList(pageable);
	}

	@Test
	@DisplayName("하위 카테고리 별 상품 목록 조회 (PRODUCT-005)")
	public void readProductListByDetailCategoryTest() throws Exception {
		// Given
		Long categorySeq = 1L;
		PageRequest pageable = PageRequest.of(0, 8);

		// When & Then
		mockMvc.perform(get("/api/product/category/detail/{categorySeq}", categorySeq)
				.param("page", "0")
				.param("size", "8"))
			.andExpect(status().isOk());

		verify(productService).readProductListByDetailCategory(categorySeq, pageable);
	}

	@Test
	@DisplayName("PRODUCT-006 (전체 카테고리 별 상품 목록 조회 - 메인 화면)")
	public void readProductListByCategoryAllTest() throws Exception {
		// Given
		PageRequest pageable = PageRequest.of(0, 8);

		// When, Then
		mockMvc.perform(get("/api/product/category/main"))
			.andExpect(status().isOk());

		verify(productService).readProductListByCategoryAll(pageable);
	}
}
