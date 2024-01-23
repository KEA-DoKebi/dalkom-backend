package com.dokebi.dalkom.domain.order.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class OrderDtoTest {

	@Test
	public void createOrderDto() {
		// Given
		Long expectedOrdrSeq = 1L;
		String expectedReceiverName = "홍길동";
		String expectedReceiverAddress = "서울시 강남구";
		String expectedReceiverMobileNum = "010-1234-5678";
		String expectedReceiverMemo = "부재 시 문 앞에 놓아주세요";
		Integer expectedTotalPrice = 10000;

		// When
		OrderDto orderDto = new OrderDto(expectedOrdrSeq, expectedReceiverName, expectedReceiverAddress,
			expectedReceiverMobileNum, expectedReceiverMemo, expectedTotalPrice);

		// Then
		assertThat(orderDto.getOrdrSeq()).isEqualTo(expectedOrdrSeq);
		assertThat(orderDto.getReceiverName()).isEqualTo(expectedReceiverName);
		assertThat(orderDto.getReceiverAddress()).isEqualTo(expectedReceiverAddress);
		assertThat(orderDto.getReceiverMobileNum()).isEqualTo(expectedReceiverMobileNum);
		assertThat(orderDto.getReceiverMemo()).isEqualTo(expectedReceiverMemo);
		assertThat(orderDto.getTotalPrice()).isEqualTo(expectedTotalPrice);
	}
}
