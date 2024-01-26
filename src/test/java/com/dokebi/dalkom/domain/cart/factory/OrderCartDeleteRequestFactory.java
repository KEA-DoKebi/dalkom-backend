package com.dokebi.dalkom.domain.cart.factory;

import java.util.Arrays;
import java.util.List;

import com.dokebi.dalkom.domain.cart.dto.OrderCartDeleteRequest;

public class OrderCartDeleteRequestFactory {
	public static OrderCartDeleteRequest createOrderCartDeleteRequest() {
		List<Long> orderCartSeqList = Arrays.asList(1L, 2L, 3L);
		return new OrderCartDeleteRequest(orderCartSeqList);
	}

	public static OrderCartDeleteRequest createOrderCartDeleteRequest(List<Long> list) {
		List<Long> orderCartSeqList = list;
		return new OrderCartDeleteRequest(orderCartSeqList);
	}
}
