package com.dokebi.dalkom.domain.product.controller;

import static com.dokebi.dalkom.domain.product.factory.ProductByCategoryResponseFactory.*;
import static com.dokebi.dalkom.domain.product.factory.ProductCreateRequestFactory.*;
import static com.dokebi.dalkom.domain.product.factory.ReadProductResponseFactory.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
	public void setup() {
		productService = Mockito.mock(ProductService.class);
		objectMapper = new ObjectMapper();

		//@InjectMocks 에러가 발생하여 수동주입
		ProductController productController = new ProductController(productService);

		mockMvc = MockMvcBuilders.standaloneSetup(productController)
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
			.build();
	}

	// PRODUCTS-001 (카테고리 별 상품 목록 조회) 테스트
	@Test
	public void readProductListByCategoryTest() throws Exception {
		// Given
		Long categorySeq = 1L;
		PageRequest pageable = PageRequest.of(0, 8);
		List<ProductByCategoryResponse> productByCategoryResponseList = createProductByCategoryResponseList();

		// 상품 목록 조회 결과를 Mock 객체로 생성
		given(productService.readProductListByCategory(categorySeq, pageable)).willReturn(
			new PageImpl<>(productByCategoryResponseList, pageable, productByCategoryResponseList.size()));

		// When & Then
		mockMvc.perform(get("/api/products/category/" + categorySeq).param("page", "0").param("size", "8"))
			.andExpect(status().isOk());
	}

	// PRODUCTS-002 (상품 상세 정보 조회) 테스트
	@Test
	public void readProductTest() throws Exception {
		// Given
		Long productSeq = 1L;
		// 상품 상세 정보 조회 결과를 Mock 객체로 생성
		given(productService.readProduct(productSeq)).willReturn(new ReadProductDetailResponse("테스트 이름", 50000));

		// When & Then
		mockMvc.perform(get("/api/product/" + productSeq))
			.andExpect(status().isOk());
	}

	// PRODUCTS-003 (상품 정보 추가) 테스트
	@Test
	public void createProductTest() throws Exception {
		// Given
		ProductCreateRequest productCreateRequest = createProductCreateRequest();

		String jsonRequest = objectMapper.writeValueAsString(productCreateRequest);

		// When & Then
		mockMvc.perform(post("/api/product").contentType(APPLICATION_JSON).content(jsonRequest))
			.andExpect(status().isOk());
	}

	// PRODUCT-004 (상품 리스트 조회) 테스트
	@Test
	public void readProductListTest() throws Exception {
		// Given
		PageRequest pageable = PageRequest.of(0, 10);
		List<ReadProductResponse> readProductDetailResponseList = createReadProductResponseList();

		// 상품 목록 조회 결과를 Mock 객체로 생성
		given(productService.readProductList(pageable)).willReturn(
			new PageImpl<>(readProductDetailResponseList, pageable, readProductDetailResponseList.size()));

		// When & Then
		mockMvc.perform(get("/api/product").param("page", "0").param("size", "10"))
			.andExpect(status().isOk());
	}

	// PRODUCTS-005 (서브 카테고리 별 상품 목록 조회) 테스트
	@Test
	public void readProductListByCategoryDetailTest() throws Exception {
		// Given
		Long categorySeq = 1L;
		PageRequest pageable = PageRequest.of(0, 8);
		List<ProductByCategoryResponse> productByCategoryResponseList = createProductByCategoryResponseList();
		// 상품 목록 조회 결과를 Mock 객체로 생성
		given(productService.readProductListByCategoryDetail(categorySeq, pageable)).willReturn(
			new PageImpl<>(productByCategoryResponseList, pageable, productByCategoryResponseList.size()));

		// When & Then
		mockMvc.perform(get("/api/products/category/detail/" + categorySeq).param("page", "0").param("size", "8"))
			.andExpect(status().isOk());
	}
}
