package com.dokebi.dalkom.domain.user.factory;

import java.time.LocalDate;

import com.dokebi.dalkom.domain.user.dto.SignUpRequest;

public class SignUpRequestFactory {

	// 기본적인 SignUpRequest 객체를 생성하는 메서드
	public static SignUpRequest createSignUpRequest() {
		return new SignUpRequest(
			"DKT123456", // empId
			"user@example.com", // email
			"Password@1", // password - 규칙에 맞는 비밀번호 예시
			"User Name", // name
			"Nickname", // nickname
			"User Address", // address
			LocalDate.now(), // joinedAt - 현재 날짜로 설정
			1000 // mileage - 초기 마일리지 값
		);
	}

	// 커스텀 값을 받아 SignUpRequest 객체를 생성하는 메서드
	public static SignUpRequest createSignUpRequest(
		String empId, String email, String password, String name, String nickname, String address, LocalDate joinedAt,
		Integer mileage) {
		return new SignUpRequest(empId, email, password, name, nickname, address, joinedAt, mileage);
	}
}
