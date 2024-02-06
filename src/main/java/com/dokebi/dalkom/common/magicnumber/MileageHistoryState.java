package com.dokebi.dalkom.common.magicnumber;

import lombok.Getter;

@Getter
public enum MileageHistoryState {
	ISSUED("0", "지급"),
	CHARGED("1", "충전"),
	DENIED("2", "충전거부"),
	USED("3", "사용"),
	CANCELLED("4", "취소"),
	RETURNED("5", "반품"),
	REFUNDED("6", "환불");

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
