package com.dokebi.dalkom.domain.product.controller;

import static com.dokebi.dalkom.domain.product.factory.ProductByCategoryDetailResponseFactory.*;
import static com.dokebi.dalkom.domain.product.factory.ProductByCategoryResponseFactory.*;
import static com.dokebi.dalkom.domain.product.factory.ProductCreateRequestFactory.*;
import static com.dokebi.dalkom.domain.product.factory.ReadProductResponseFactory.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dokebi.dalkom.domain.product.dto.ProductByCategoryDetailResponse;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse;
import com.dokebi.dalkom.domain.product.dto.ProductCreateRequest;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.dto.ReadProductResponse;
import com.dokebi.dalkom.domain.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductControllerTest {

	@Mock
	private ProductService productService;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach() {
		productService = Mockito.mock(ProductService.class);
		objectMapper = new ObjectMapper();

		//@InjectMocks 에러가 발생하여 수동주입
		ProductController productController = new ProductController(productService);

		mockMvc = MockMvcBuilders.standaloneSetup(productController)
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
	}

	@Test
	@DisplayName("상위 카테고리 별 상품 목록 조회 (PRODUCT-001)")
	public void readProductListByCategoryTest() throws Exception {
		// Given
		Long categorySeq = 1L;
		PageRequest pageable = PageRequest.of(0, 8);
		List<ProductByCategoryResponse> productByCategoryResponseList = createProductByCategoryResponseList();

		// 상품 목록 조회 결과를 Mock 객체로 생성
		given(productService.readProductListByCategory(categorySeq, pageable)).willReturn(
			new PageImpl<>(productByCategoryResponseList, pageable, productByCategoryResponseList.size()));

		// When & Then
		mockMvc.perform(get("/api/product/category/" + categorySeq)
				.param("page", "0").param("size", "8"))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("상품 상세 정보 조회 (PRODUCT-002)")
	public void readProductTest() throws Exception {
		// Given
		Long productSeq = 1L;
		// 상품 상세 정보 조회 결과를 Mock 객체로 생성
		given(productService.readProduct(productSeq)).willReturn(
			new ReadProductDetailResponse("테스트 이름", 50000));

		// When & Then
		mockMvc.perform(get("/api/product/" + productSeq))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("상품 정보 추가 (PRODUCT-003)")
	public void createProductTest() throws Exception {
		// Given
		ProductCreateRequest productCreateRequest = createProductCreateRequest();

		String jsonRequest = objectMapper.writeValueAsString(productCreateRequest);

		// When & Then
		mockMvc.perform(post("/api/product")
				.contentType(APPLICATION_JSON)
				.content(jsonRequest))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("상품 리스트 조회 - 관리자 화면 (PRODUCT-004)")
	public void readAdminPageProductListTest() throws Exception {
		// Given
		PageRequest pageable = PageRequest.of(0, 10);
		List<ReadProductResponse> readProductDetailResponseList = createReadProductResponseList();

		// 상품 목록 조회 결과를 Mock 객체로 생성
		given(productService.readAdminPageProductList(pageable)).willReturn(
			new PageImpl<>(readProductDetailResponseList, pageable, readProductDetailResponseList.size()));

		// When & Then
		mockMvc.perform(get("/api/product")
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("하위 카테고리 별 상품 목록 조회 (PRODUCT-005)")
	public void readProductListByCategoryDetailTest() throws Exception {
		// Given
		Long categorySeq = 1L;
		PageRequest pageable = PageRequest.of(0, 8);
		List<ProductByCategoryDetailResponse> productByCategoryDetailResponseList
			= createProductByCategoryDetailResponseList();
		// 상품 목록 조회 결과를 Mock 객체로 생성
		given(productService.readProductListByDetailCategory(categorySeq, pageable)).willReturn(
			new PageImpl<>(productByCategoryDetailResponseList, pageable, productByCategoryDetailResponseList.size()));

		// When & Then
		mockMvc.perform(get("/api/product/category/detail/" + categorySeq)
				.param("page", "0")
				.param("size", "8"))
			.andExpect(status().isOk());
	}
}
