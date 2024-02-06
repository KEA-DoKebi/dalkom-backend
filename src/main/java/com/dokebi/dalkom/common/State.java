package com.dokebi.dalkom.common;

import lombok.Generated;

@Generated
public enum State {
	ORDER_STATE1("11", "상품준비"),
	ORDER_STATE2("12", "배송시작"),
	ORDER_STATE3("13", "주문상태"),
	ORDER_STATE4("14", "주문상태"),
	ORDER_STATE5("15", "주문상태"),
	ORDER_STATE6("21", "주문상태");

	private final String code;
	private final String msg;

	State(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
