package com.dokebi.dalkom.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LogInAdminRequest {

	@NotNull(message = "LogInAdminRequest title notnull 에러")
	@NotBlank(message = "LogInAdminRequest title notblank 에러")
	private String adminId;

	@NotNull(message = "LogInAdminRequest password notnull 에러")
	@NotBlank(message = "LogInAdminRequest password notblank 에러")
	private String password;
}
