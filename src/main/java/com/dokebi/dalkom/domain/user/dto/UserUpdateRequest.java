package com.dokebi.dalkom.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

	@NotNull(message = "UserUpdateRequest password NotNull 에러")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 최소 8자리이면서 1개 이상의 알파벳,숫자,특수문자를 포함해야 한다.")
	private String password;

	@NotBlank(message = "UserUpdateRequest nickname NotBlank 에러")
	private String nickname;

	@NotBlank(message = "UserUpdateRequest address NotBlank 에러")
	private String address;

	public void encodedPassword(String password) {
		this.password = password;
	}
}
