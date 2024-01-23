package com.dokebi.dalkom.domain.order.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderReadResponseTest {

	@Test
	void createOrderDto() {
		// Given
		Long expectedOrdrSeq = 1L;
		String expectedReceiverName = "홍길동";
		String expectedReceiverAddress = "서울시 강남구";
		String expectedReceiverMobileNum = "010-1234-5678";
		String expectedReceiverMemo = "부재 시 문 앞에 놓아주세요";
		Integer expectedTotalPrice = 10000;

		// When
		OrderReadResponse orderReadResponse = new OrderReadResponse(expectedOrdrSeq, expectedReceiverName, expectedReceiverAddress,
			expectedReceiverMobileNum, expectedReceiverMemo, expectedTotalPrice);

		// Then
		assertThat(orderReadResponse.getOrdrSeq()).isEqualTo(expectedOrdrSeq);
		assertThat(orderReadResponse.getReceiverName()).isEqualTo(expectedReceiverName);
		assertThat(orderReadResponse.getReceiverAddress()).isEqualTo(expectedReceiverAddress);
		assertThat(orderReadResponse.getReceiverMobileNum()).isEqualTo(expectedReceiverMobileNum);
		assertThat(orderReadResponse.getReceiverMemo()).isEqualTo(expectedReceiverMemo);
		assertThat(orderReadResponse.getTotalPrice()).isEqualTo(expectedTotalPrice);
	}
}
