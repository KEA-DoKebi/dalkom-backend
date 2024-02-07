package com.dokebi.dalkom.common.magicnumber;

import lombok.Getter;

@Getter
public enum AdminRole {

	SUPER_ADMIN("1", "최고 관리자"),
	NORMAL_ADMIN("2", "일반 관리자");

	private final String state;
	private final String name;

	AdminRole(String state, String name) {
		this.state = state;
		this.name = name;
	}

	public static String getNameByState(String state) {
		for (AdminRole adminRole : AdminRole.values()) {
			if (adminRole.state.equals(state)) {
				return adminRole.name;
			}
		}
		return null; // 또는 기본값
	}
}
