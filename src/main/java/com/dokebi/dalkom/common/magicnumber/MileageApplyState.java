package com.dokebi.dalkom.common.magicnumber;

import lombok.Getter;

@Getter
public enum MileageApplyState {

	YES("Y", "승인"),
	NO("N", "미승인"),
	WAITING("W", "대기중");

	private final String state;
	private final String name;

	MileageApplyState(String state, String message) {
		this.state = state;
		this.name = message;
	}

}
