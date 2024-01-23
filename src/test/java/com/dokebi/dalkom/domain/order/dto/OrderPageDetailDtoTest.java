package com.dokebi.dalkom.domain.order.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class OrderPageDetailDtoTest {

	@Test
	public void createOrderPageDetailDto() {
		// Given
		Long expectedProductSeq = 1L;
		Long expectedProductOptionSeq = 2L;
		Integer expectedProductAmount = 3;
		String expectedProductName = "상품명";
		Integer expectedProductPrice = 10000;
		Integer expectedTotalPrice = 30000; // productAmount * productPrice

		// When
		OrderPageDetailDto orderPageDetailDto = new OrderPageDetailDto(expectedProductSeq, expectedProductOptionSeq,
			expectedProductAmount, expectedProductName, expectedProductPrice, expectedTotalPrice);

		// Then
		assertThat(orderPageDetailDto.getProductSeq()).isEqualTo(expectedProductSeq);
		assertThat(orderPageDetailDto.getProductOptionSeq()).isEqualTo(expectedProductOptionSeq);
		assertThat(orderPageDetailDto.getProductAmount()).isEqualTo(expectedProductAmount);
		assertThat(orderPageDetailDto.getProductName()).isEqualTo(expectedProductName);
		assertThat(orderPageDetailDto.getProductPrice()).isEqualTo(expectedProductPrice);
		assertThat(orderPageDetailDto.getTotalPrice()).isEqualTo(expectedTotalPrice);
	}
}
