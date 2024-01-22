package com.dokebi.dalkom.domain.order.factory;

import java.util.Collections;

import com.dokebi.dalkom.domain.order.dto.OrderCreateRequest;

public class OrderCreateRequestFactory {
	public static OrderCreateRequest createOrderCreateRequest() {
		return new OrderCreateRequest(
			1L,
			"John Doe",
			"123 Main St",
			"555-1234",
			"Some memo",
			Collections.singletonList(1L),
			Collections.singletonList(2L),
			Collections.singletonList(3)
		);
	}
}
