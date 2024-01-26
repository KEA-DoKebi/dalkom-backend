package com.dokebi.dalkom.domain.order.factory;

import java.time.LocalDateTime;

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
			100,
			"Shipped",
			LocalDateTime.now()

		);
	}
}
