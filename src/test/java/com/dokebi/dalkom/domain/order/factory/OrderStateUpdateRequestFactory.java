package com.dokebi.dalkom.domain.order.factory;

import com.dokebi.dalkom.domain.order.dto.OrderStateUpdateRequest;

public class OrderStateUpdateRequestFactory {

	public static OrderStateUpdateRequest createOrderStateUpdateRequest() {
		return new OrderStateUpdateRequest(
			"11"
		);
	}

	public static OrderStateUpdateRequest createOrderStateUpdateRequest(String orderState) {
		return new OrderStateUpdateRequest(
			orderState
		);
	}

}
