package com.dokebi.dalkom.common.magicnumber;

import lombok.Getter;

@Getter
public enum AdminRole {

	SUPER_ADMIN("1", "최고 관리자"),
	NORMAL_ADMIN("2", "일반 관리자");

	private final String state;
	private final String name;

	AdminRole(String state, String message) {
		this.state = state;
		this.name = message;
	}

}
