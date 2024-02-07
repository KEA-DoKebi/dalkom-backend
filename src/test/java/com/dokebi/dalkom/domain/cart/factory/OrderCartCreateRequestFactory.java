package com.dokebi.dalkom.domain.cart.factory;

import com.dokebi.dalkom.domain.cart.dto.OrderCartCreateRequest;

public class OrderCartCreateRequestFactory {
	// OrderCartCreateRequest는 ProductSeq, PrdtOptionSeq, Amount로 이루어져 있다.
	public static OrderCartCreateRequest createOrderCartCreateRequest() {
		return new OrderCartCreateRequest(
			1L,
			1L,
			10
		);
	}

	public static OrderCartCreateRequest createOrderCartCreateRequest(
		Long productSeq, Long prdtOptionSeq, Integer amount) {
		return new OrderCartCreateRequest(productSeq, prdtOptionSeq, amount);
	}
}
