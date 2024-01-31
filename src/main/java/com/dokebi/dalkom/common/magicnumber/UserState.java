package com.dokebi.dalkom.common.magicnumber;

import lombok.Getter;

@Getter
public enum UserState {

	ACTIVE("Y", "활성 사용자"),
	INACTIVE("N", "비활성 사용자");

	private final String state;
	private final String name;

	UserState(String state, String message) {
		this.state = state;
		this.name = message;
	}

}
