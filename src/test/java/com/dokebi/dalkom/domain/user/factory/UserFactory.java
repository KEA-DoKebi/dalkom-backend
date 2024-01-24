package com.dokebi.dalkom.domain.user.factory;

import com.dokebi.dalkom.domain.user.entity.User;

import lombok.EqualsAndHashCode;
import lombok.Setter;

@EqualsAndHashCode
@Setter
public class UserFactory {



	public static User createMockUser() {
		return new User("empId001", // empId
			"password", // password
			"김철수", // name
			"chulsu@example.com", // email
			"서울시 강남구", // address
			"2023-01-01", // joinedAt
			"chulsu", // nickname
			30000 // mileage
		);
	}

	public static User createMockUserWithInsufficientMileage() {
		return new User("empId002", // empId
			"password", // password
			"이영희", // name
			"younghi@example.com", // email
			"서울시 마포구", // address
			"2023-02-01", // joinedAt
			"younghi", // nickname
			500 // mileage
		);
	}
}
