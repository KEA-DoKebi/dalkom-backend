package com.dokebi.dalkom.domain.order.factory;

import com.dokebi.dalkom.domain.order.dto.OrderDetailSimpleResponse;

public class OrderDetailSimpleResponseFactory {
	public static OrderDetailSimpleResponse createOrderDetailSimpleResponse() {
		return new OrderDetailSimpleResponse("TestProduct", "test-image.jpg", 123L, "TestDetail");
	}
}
