package com.dokebi.dalkom.common.magicnumber;

import lombok.Getter;

@Getter
public enum MileageHistoryState {
	ISSUED("0", "지급"),
	CHARGED("1", "충전"),
	USED("2", "사용"),
	CANCELLED("3", "취소"),
	RETURNED("4", "반품"),
	REFUNDED("5", "환불"),
	DENIED("6", "충전거부");

	private final String state;
	private final String name;

	MileageHistoryState(String state, String name) {
		this.state = state;
		this.name = name;
	}

	public static String getNameByState(String state) {
		for (MileageHistoryState mileageHistoryState : MileageHistoryState.values()) {
			if (mileageHistoryState.state.equals(state)) {
				return mileageHistoryState.name;
			}
		}
		return null; // 또는 기본값
	}
}
