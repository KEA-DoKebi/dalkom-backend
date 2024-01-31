package com.dokebi.dalkom.common.magicnumber;

import lombok.Getter;

@Getter
public enum MileageApplyState {

	YES("Y", "승인"),
	NO("N", "미승인"),
	WAITING("W", "대기중");

	private final String state;
	private final String name;

	MileageApplyState(String state, String name) {
		this.state = state;
		this.name = name;
	}

	public static String getNameByState(String state) {
		for (MileageApplyState applyState : MileageApplyState.values()) {
			if (applyState.state.equals(state)) {
				return applyState.name;
			}
		}
		return null; // 또는 기본값
	}

}
