package com.dokebi.dalkom.domain.order.factory;

import com.dokebi.dalkom.domain.order.dto.AuthorizeOrderRequest;

public class AuthorizeOrderRequestFactory {

	public static AuthorizeOrderRequest createAuthorizeOrderRequest() {
		return new AuthorizeOrderRequest(
			"123456a!"
		);
	}
}
