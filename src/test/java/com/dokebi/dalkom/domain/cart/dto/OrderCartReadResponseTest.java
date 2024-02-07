package com.dokebi.dalkom.domain.cart.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderCartReadResponseTest {

	@Test
	@DisplayName("orderCartReadResponse 테스트")
	public void orderCartReadResponseTest() {
		// Given
		Long orderCartSeq = 1L;
		Long productSeq = 2L;
		Long prdtOptionSeq = 3L;
		String productName = "Test Product";
		String prdtOptionDetail = "Test Option";
		String imageUrl = "test-image.jpg";
		Integer price = 1000;
		Integer amount = 2;
		Integer stock = 80;

		// When
		OrderCartReadResponse orderCartReadResponse = new OrderCartReadResponse(
			orderCartSeq, productSeq, prdtOptionSeq, productName, prdtOptionDetail, imageUrl, price, amount, stock);

		// Then
		assertNotNull(orderCartReadResponse);
		assertEquals(orderCartSeq, orderCartReadResponse.getOrderCartSeq());
		assertEquals(productSeq, orderCartReadResponse.getProductSeq());
		assertEquals(prdtOptionSeq, orderCartReadResponse.getPrdtOptionSeq());
		assertEquals(productName, orderCartReadResponse.getProductName());
		assertEquals(prdtOptionDetail, orderCartReadResponse.getPrdtOptionDetail());
		assertEquals(imageUrl, orderCartReadResponse.getImageUrl());
		assertEquals(price, orderCartReadResponse.getPrice());
		assertEquals(amount, orderCartReadResponse.getAmount());
	}
}
