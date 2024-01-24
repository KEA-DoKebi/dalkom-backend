package com.dokebi.dalkom.domain.product.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ReadProductResponseTest {

	@Test
	public void testReadProductResponseConstructor() {
		// Given
		Long productSeq = 1L;
		String name = "Test Product";
		Integer price = 10000;
		String state = "Available";
		String imageUrl = "http://testimageurl.com";
		String company = "Test Company";
		String optionDetail = "Option Detail";
		Integer amount = 100;

		// When
		ReadProductResponse response = new ReadProductResponse(productSeq, name, price, state, imageUrl, company,
			optionDetail, amount);

		// Then
		assertNotNull(response);
		assertEquals(productSeq, response.getProductSeq());
		assertEquals(name, response.getName());
		assertEquals(price, response.getPrice());
		assertEquals(state, response.getState());
		assertEquals(imageUrl, response.getImageUrl());
		assertEquals(company, response.getCompany());
		assertEquals(optionDetail, response.getOptionDetail());
		assertEquals(amount, response.getAmount());
	}

	@Test
	public void testReadProductResponseSimplifiedConstructor() {
		// Given
		String name = "Simplified Test Product";
		Integer price = 5000;

		// When
		ReadProductResponse response = new ReadProductResponse(name, price);

		// Then
		assertNotNull(response);
		assertEquals(name, response.getName());
		assertEquals(price, response.getPrice());
	}
}
