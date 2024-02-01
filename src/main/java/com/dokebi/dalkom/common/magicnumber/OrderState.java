package com.dokebi.dalkom.common.magicnumber;

import lombok.Getter;

@Getter
public enum OrderState {

	CONFIRMED("11", "주문확인"),
	PREPARING("12", "배송준비"),
	SHIPPED("13", "배송시작"),
	DELIVERED("14", "배송완료"),
	FINALIZED("15", "구매확정"),
	CANCELED("21", "주문취소"),
	REFUND_CONFIRMED("31", "반품/환불접수"),
	RETURNED("32", "반송완료"),
	REFUNDED("33", "반품/환불완료");

	private final String state;
	private final String name;

	OrderState(String state, String name) {
		this.state = state;
		this.name = name;
	}

	public static String getNameByState(String state) {
		for (OrderState orderState : OrderState.values()) {
			if (orderState.state.equals(state)) {
				return orderState.name;
			}
		}
		return null; // 또는 기본값
	}

	// "11" : 주문확인, "12" : 배송준비, "13" : 배송시작, "14" : 배송완료, "15" : 구매확정,
	// "21" : 주문취소,
	// "31" : 반품접수, "32" : 반송시작, "33" : 반송완료, "34" : 반품완료,
	// "41" : 환불

}
