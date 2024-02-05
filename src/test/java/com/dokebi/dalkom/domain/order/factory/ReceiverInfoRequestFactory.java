package com.dokebi.dalkom.domain.order.factory;

import com.dokebi.dalkom.domain.order.dto.ReceiverInfoRequest;

public class ReceiverInfoRequestFactory {
	public static ReceiverInfoRequest createReceiverInfoRequest() {
		return new ReceiverInfoRequest(
			"John Doe", // receiverName
			"123 Main St",          // receiverAddress
			"010-1234-5678",        // receiverMobileNum
			"Special instructions"  // receiverMemo
		);
	}
}
