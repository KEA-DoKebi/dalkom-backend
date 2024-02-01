package com.dokebi.dalkom.domain.user.dto;

import com.dokebi.dalkom.common.magicnumber.AdminRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LogInAdminResponse {

	private String accessToken;
	private String role;
	private String roleName;

	public LogInAdminResponse(String accessToken, String role) {
		this.accessToken = accessToken;
		this.role = role;
		this.roleName = AdminRole.getNameByState(role);
	}
}
