package com.dokebi.dalkom.domain.order.factory;

import java.time.LocalDateTime;

import com.dokebi.dalkom.domain.order.dto.OrderUserReadResponse;

public class OrderReadResponseFactory {
	private Long ordrSeq;
	private String receiverName;
	private String receiverAddress;
	private String receiverMobileNum;
	private String receiverMemo;
	private Integer totalPrice;

	public static OrderUserReadResponse createOrderReadResponse() {
		return new OrderUserReadResponse(
			1L,
			"상품명",
			3L,
			100,
			"Shipped",
			LocalDateTime.now()

		);
	}
}
