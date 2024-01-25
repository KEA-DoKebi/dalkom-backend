package com.dokebi.dalkom.domain.inquiry.factory;

import com.dokebi.dalkom.domain.order.dto.OrderStateUpdateRequest;

public class OrderStateUpdateRequestFactory {
	public static OrderStateUpdateRequest createOrderStateUpdateRequestFactory(String orderState) {
		return new OrderStateUpdateRequest(orderState);
	}
}
