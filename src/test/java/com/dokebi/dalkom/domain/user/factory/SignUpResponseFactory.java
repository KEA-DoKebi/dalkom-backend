package com.dokebi.dalkom.domain.user.factory;

import com.dokebi.dalkom.domain.user.dto.SignUpResponse;

public class SignUpResponseFactory {

	// 기본적인 SignUpResponse 객체를 생성하는 메서드
	public static SignUpResponse createSignUpResponse() {
		return new SignUpResponse(
			"DKT123456", // empId
			"user@example.com", // email
			"회원가입 성공" // message
		);
	}

	// 커스텀 값을 받아 SignUpResponse 객체를 생성하는 메서드
	public static SignUpResponse createSignUpResponse(String empId, String email, String message) {
		return new SignUpResponse(empId, email, message);
	}
}
