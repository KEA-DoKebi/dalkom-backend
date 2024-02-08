package com.dokebi.dalkom.domain.user.factory;

import java.time.LocalDate;

import com.dokebi.dalkom.domain.user.entity.User;

import lombok.EqualsAndHashCode;
import lombok.Setter;

@EqualsAndHashCode
@Setter
public class UserFactory {

	public static User createMockUser() {
		return new User("DKT123456789", // empId
			"password", // password
			"김철수", // name
			"chulsu@example.com", // email
			"서울시 강남구", // address
			LocalDate.now(), // joinedAt
			"chulsu", // nickname
<<<<<<< HEAD
			60000 // mileage
=======
			30000 // mileage
>>>>>>> 4b8f81afc49e6512730462d214f424d2bb7b2043
		);
	}

	public static User createMockUserWithInsufficientMileage() {
		return new User("DKT123456788", // empId
			"password", // password
			"이영희", // name
			"younghi@example.com", // email
			"서울시 마포구", // address
			LocalDate.now(), // joinedAt
			"younghi", // nickname
			500 // mileage
		);
	}
}
