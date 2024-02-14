package com.dokebi.dalkom.common.magicnumber;

import lombok.Getter;

@Getter
public enum UserState {

	ACTIVE("Y", "활성 사용자"),
	INACTIVE("N", "비활성 사용자");

	private final String state;
	private final String name;

	UserState(String state, String name) {
		this.state = state;
		this.name = name;
	}

	public static String getNameByState(String state) {
		for (UserState userState : UserState.values()) {
			if (userState.state.equals(state)) {
				return userState.name;
			}
		}
		return null; // 또는 기본값
	}

}
