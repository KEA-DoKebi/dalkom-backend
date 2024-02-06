package com.dokebi.dalkom.domain.order.dto;

import static com.dokebi.dalkom.domain.order.factory.OrderReadResponseFactory.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderUserReadResponseTest {
	@Test
	void createOrderDto() {
		// Given
		Long expectedOrdrSeq = 1L;
		Integer expectedTotalPrice = 100;

		// When
		OrderUserReadResponse orderUserReadResponse = createOrderReadResponse();

		// Then
		assertThat(orderUserReadResponse.getOrdrSeq()).isEqualTo(expectedOrdrSeq);
		assertThat(orderUserReadResponse.getTotalPrice()).isEqualTo(expectedTotalPrice);
	}
}
