package com.dokebi.dalkom.domain.admin.factory;

import com.dokebi.dalkom.domain.admin.entity.Admin;

public class AdminFactory {
	public static Admin createMockAdmin() {
		return new Admin(
			"adminId",
			"password",
			"nickname",
			"name",
			"depart"
		);
	}

	public static Admin createMockAdmin(
		String adminId, String password, String nickname, String name, String depart
	) {
		return new Admin(
			adminId, password, nickname, name, depart
		);
	}
}
