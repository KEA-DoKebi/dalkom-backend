package com.dokebi.dalkom.domain.product.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ProductByCategoryResponseTest {

	@Test
	public void testProductByCategoryResponse() {
		// Given: 상품 정보 초기화
		Long expectedProductSeq = 1L;
		String expectedName = "상품 이름";
		Integer expectedPrice = 10000;
		String expectedState = "판매중";
		String expectedImageUrl = "image/url";
		String expectedCompany = "회사명";
		Integer expectedStock = 50;

		// When: ProductByCategoryResponse 객체 생성 및 초기화
		ProductByCategoryResponse dto = new ProductByCategoryResponse(
			expectedProductSeq, expectedName, expectedPrice,
			expectedState, expectedImageUrl, expectedCompany, expectedStock);

		// Then: 객체 필드 값 검증
		assertEquals(expectedProductSeq, dto.getProductSeq());
		assertEquals(expectedName, dto.getName());
		assertEquals(expectedPrice, dto.getPrice());
		assertEquals(expectedState, dto.getState());
		assertEquals(expectedImageUrl, dto.getImageUrl());
		assertEquals(expectedCompany, dto.getCompany());
		assertEquals(expectedStock, dto.getStock());
	}
}
