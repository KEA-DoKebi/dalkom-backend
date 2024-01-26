package com.dokebi.dalkom.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LogInAdminResponse {
	private String accessToken;
	private String role;
}
