package com.dokebi.dalkom.common.magicnumber;

import lombok.Getter;

@Getter
public enum InquiryAnswerState {

	YES("Y", "답변완료"),
	NO("N", "대기중");

	private final String state;
	private final String name;

	InquiryAnswerState(String state, String message) {
		this.state = state;
		this.name = message;
	}
}
