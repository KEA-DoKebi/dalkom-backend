package com.dokebi.dalkom.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogInRequest {

	@NotNull(message = "LogInRequest email notnull 에러")
	@NotBlank(message = "LogInRequest email notblank 에러")
	private String email;

	@NotNull(message = "LogInRequest password notnull 에러")
	@NotBlank(message = "LogInRequest password notblank 에러")
	private String password;
}
