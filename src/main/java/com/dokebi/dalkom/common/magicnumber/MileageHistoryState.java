package com.dokebi.dalkom.common.magicnumber;

import lombok.Getter;

@Getter
public enum MileageHistoryState {
	ISSUED("0", "지급"),
	CHARGED("1", "충전"),
	USED("2", "사용"),
	CANCELLED("3", "취소"),
	RETURNED("4", "반품"),
	REFUNDED("5", "환불");

	private final String state;
	private final String name;

	MileageHistoryState(String state, String message) {
		this.state = state;
		this.name = message;
	}

}
