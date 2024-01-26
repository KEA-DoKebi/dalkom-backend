package com.dokebi.dalkom.domain.order.factory;

import java.util.List;

import com.dokebi.dalkom.domain.order.dto.OrderCreateRequest;

public class OrderCreateRequestFactory {
	public static OrderCreateRequest createOrderCreateRequest() {
		return new OrderCreateRequest(
			1L,
			"John Doe",
			"123 Main St",
			"555-1234",
			"Some memo",
			List.of(1L), // productSeqList
			List.of(2L), // prdtOptionSeqList
			List.of(3)  // amountList
		);
	}

	public static OrderCreateRequest createOrderCreateRequest(
		Long userSeq, String receiverName, String receiverAddress,
		String receiverMobileNum, String receiverMemo,
		List<Long> productSeqList, List<Long> prdtOptionSeqList, List<Integer> amountList
	) {
		return new OrderCreateRequest(
			userSeq,
			receiverName,
			receiverAddress,
			receiverMobileNum,
			receiverMemo,
			productSeqList,
			prdtOptionSeqList,
			amountList
		);
	}

}
