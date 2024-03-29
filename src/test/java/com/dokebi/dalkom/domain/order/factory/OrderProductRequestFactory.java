package com.dokebi.dalkom.domain.order.factory;

import com.dokebi.dalkom.domain.order.dto.OrderProductRequest;

public class OrderProductRequestFactory {
	public static OrderProductRequest createOrderProductRequest() {
		return new OrderProductRequest(
			1L,        // productSeq
			2L,        // productOptionSeq
			3L,
			3         // productAmount

		);
	}

	public static OrderProductRequest createOrderProductRequest(
		Long productSeq, Long productOptionSeq, Long orderCartSeq, Integer productAmount) {
		return new OrderProductRequest(
			productSeq, productOptionSeq, orderCartSeq, productAmount
		);
	}

}
