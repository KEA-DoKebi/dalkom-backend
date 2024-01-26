package com.dokebi.dalkom.domain.order.dto;

import static com.dokebi.dalkom.domain.order.factory.OrderReadResponseFactory.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderUserReadResponseTest {
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
		OrderUserReadResponse orderUserReadResponse = createOrderReadResponse();

		// Then
		assertThat(orderUserReadResponse.getOrdrSeq()).isEqualTo(expectedOrdrSeq);
		assertThat(orderUserReadResponse.getReceiverName()).isEqualTo(expectedReceiverName);
		assertThat(orderUserReadResponse.getReceiverAddress()).isEqualTo(expectedReceiverAddress);
		assertThat(orderUserReadResponse.getReceiverMobileNum()).isEqualTo(expectedReceiverMobileNum);
		assertThat(orderUserReadResponse.getReceiverMemo()).isEqualTo(expectedReceiverMemo);
		assertThat(orderUserReadResponse.getTotalPrice()).isEqualTo(expectedTotalPrice);
	}
}
