package com.dokebi.dalkom.domain.user.dto;

import java.time.LocalDate;

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

	@NotBlank(message = "SignUpRequest empId notblank 에러")
	private String empId;

	@NotBlank(message = "SignUpRequest email notblank 에러")
	private String email;

	@NotNull(message = "SignUpRequest password notnull 에러")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 최소 8자리이면서 1개 이상의 알파벳,숫자,특수문자를 포함해야 한다.")
	private String password;

	@NotBlank(message = "SignUpRequest name notblank 에러")
	private String name;

	@NotBlank(message = "SignUpRequest nickname notblank 에러")
	@Size(min = 2, message = "사용자 닉네임이 너무 짧습니다.")
	@Pattern(regexp = "^[A-Za-z가-힣]+$", message = "닉네임은 한글 또는 알파벳만 입력해주세요.")
	private String nickname;

	@NotBlank(message = "SignUpRequest address notblank 에러")
	private String address;

	@NotNull(message = "SignUpRequest joinedAt notnull 에러")
	private LocalDate joinedAt;

	private Integer mileage;

	public static User toEntity(SignUpRequest request) {
		return new User(request.empId, request.password, request.name, request.email, request.address, request.joinedAt,
			request.nickname, request.mileage);
	}
}
