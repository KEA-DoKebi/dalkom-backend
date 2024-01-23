package com.dokebi.dalkom.domain.admin.factory;

import com.dokebi.dalkom.domain.admin.dto.CreateAdminRequest;

public class CreateAdminRequestFactory {
	public static CreateAdminRequest createCreateAdminRequest() {
		return new CreateAdminRequest(
			"adminId",
			"password",
			"nickname",
			"name",
			"depart"
		);
	}

	public static CreateAdminRequest createCreateAdminRequest(
		String adminId, String password, String nickname, String name, String depart
	) {
		return new CreateAdminRequest(
			adminId, password, nickname, name, depart
		);
	}
}
