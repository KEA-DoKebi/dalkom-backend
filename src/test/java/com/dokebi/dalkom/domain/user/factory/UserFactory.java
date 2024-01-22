package com.dokebi.dalkom.domain.user.factory;

import com.dokebi.dalkom.domain.user.entity.User;

public class UserFactory {

	public static User createUser() {
		return new User(
			"john_doe",
			"password123",
			"John Doe",
			"john@example.com",
			"123 Main St",
			"2022-01-20",
			"johnny",
			100
		);
	}
}
