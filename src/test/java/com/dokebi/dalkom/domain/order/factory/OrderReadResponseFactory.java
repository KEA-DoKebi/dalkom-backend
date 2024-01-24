package com.dokebi.dalkom.domain.order.factory;

import com.dokebi.dalkom.domain.order.dto.OrderReadResponse;

public class OrderReadResponseFactory {
	private Long ordrSeq;
	private String receiverName;
	private String receiverAddress;
	private String receiverMobileNum;
	private String receiverMemo;
	private Integer totalPrice;

	public static OrderReadResponse createOrderReadResponse() {
		return new OrderReadResponse(
			1L,
			"receiverName",
			"receiverAddress",
			"receiverMobileNum",
			"receiverMemo",
			100000
		);
	}
}
