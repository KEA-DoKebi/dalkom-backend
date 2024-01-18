package com.dokebi.dalkom.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LogInAdminRequest {
	private String adminId;
	private String password;
}
