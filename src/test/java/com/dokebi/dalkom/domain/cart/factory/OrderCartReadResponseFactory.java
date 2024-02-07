package com.dokebi.dalkom.domain.cart.factory;

import com.dokebi.dalkom.domain.cart.dto.OrderCartReadResponse;

public class OrderCartReadResponseFactory {
	public static OrderCartReadResponse createOrderCartReadResponse() {
		return new OrderCartReadResponse(
			1L,
			1L,
			1L,
			"productName",
			"prdtOptionName",
			"imageUrl",
			1000,
			2,
			80
		);
	}

	public static OrderCartReadResponse createOrderCartReadResponse(
		Long orderCartSeq, Long productSeq, Long prdtOptionSeq,
		String productName, String prdtOptionName, String imageUrl,
		Integer price, Integer amount, Integer stock
	) {
		return new OrderCartReadResponse(orderCartSeq, productSeq, prdtOptionSeq,
			productName, prdtOptionName, imageUrl, price, amount, stock);
	}
}
