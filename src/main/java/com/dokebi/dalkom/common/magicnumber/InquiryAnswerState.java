package com.dokebi.dalkom.common.magicnumber;

import lombok.Getter;

@Getter
public enum InquiryAnswerState {

	YES("Y", "답변완료"),
	NO("N", "대기중");

	private final String state;
	private final String name;

	InquiryAnswerState(String state, String name) {
		this.state = state;
		this.name = name;
	}

	public static String getNameByState(String state) {
		for (InquiryAnswerState inquiryAnswerState : InquiryAnswerState.values()) {
			if (inquiryAnswerState.state.equals(state)) {
				return inquiryAnswerState.name;
			}
		}
		return null; // 또는 기본값
	}

}
