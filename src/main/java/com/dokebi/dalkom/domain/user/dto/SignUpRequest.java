package com.dokebi.dalkom.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.dokebi.dalkom.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

	@NotNull(message = "SignUpRequest userId notnull 에러")
	@NotBlank(message = "SignUpRequest userId notblank 에러")
	private String userId;

	@NotNull(message = "SignUpRequest empId notnull 에러")
	@NotBlank(message = "SignUpRequest empId notblank 에러")
	private String empId;

	@NotNull(message = "SignUpRequest password notnull 에러")
	@NotBlank(message = "SignUpRequest password notblank 에러")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 최소 8자리이면서 1개 이상의 알파벳,숫자,특수문자를 포함해야 한다.")
	private String password;

	@NotNull(message = "SignUpRequest name notnull 에러")
	@NotBlank(message = "SignUpRequest name notblank 에러")
	private String name;

	@NotNull(message = "SignUpRequest email notnull 에러")
	@NotBlank(message = "SignUpRequest email notblank 에러")
	private String email;

	@NotNull(message = "SignUpRequest address notnull 에러")
	@NotBlank(message = "SignUpRequest address notblank 에러")
	private String address;

	@NotNull(message = "SignUpRequest joinedAt notnull 에러")
	@NotBlank(message = "SignUpRequest joinedAt notblank 에러")
	private String joinedAt;

	@NotNull(message = "SignUpRequest nickname notnull 에러")
	@NotBlank(message = "SignUpRequest nickname notblank 에러")
	@Size(min = 2, message = "사용자 닉네임이 너무 짧습니다.")
	@Pattern(regexp = "^[A-Za-z가-힣]+$", message = "닉네임은 한글 또는 알파벳만 입력해주세요.")
	private String nickname;

	private Integer mileage;

	public static User toEntity(SignUpRequest req) {
		return new User(req.empId, req.password, req.name, req.email, req.address, req.joinedAt, req.nickname,
			req.mileage);
	}
}
