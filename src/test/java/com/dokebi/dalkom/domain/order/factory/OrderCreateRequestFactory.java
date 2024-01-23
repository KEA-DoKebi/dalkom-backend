package com.dokebi.dalkom.domain.order.factory;

import java.util.Collections;
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
			Collections.singletonList(1L), // productSeqList
			Collections.singletonList(2L), // prdtOptionSeqList
			Collections.singletonList(3)  // amountList
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
