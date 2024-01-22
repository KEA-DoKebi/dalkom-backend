package com.dokebi.dalkom.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

	@NotNull(message = "UserUpdateRequest password notnull 에러")
	@NotBlank(message = "UserUpdateRequest password notblank 에러")
	private String password;

	@NotNull(message = "UserUpdateRequest nickname notnull 에러")
	@NotBlank(message = "UserUpdateRequest nickname notblank 에러")
	private String nickname;

	@NotNull(message = "UserUpdateRequest address notnull 에러")
	@NotBlank(message = "UserUpdateRequest address notblank 에러")
	private String address;
}
