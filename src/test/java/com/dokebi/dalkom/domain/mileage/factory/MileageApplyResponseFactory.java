package com.dokebi.dalkom.domain.mileage.factory;

import java.time.LocalDateTime;

import com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse;
import com.dokebi.dalkom.domain.mileage.entity.MileageApply;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.factory.UserFactory;

public class MileageApplyResponseFactory {

	public static MileageApplyResponse createMileageApplyResponse() {
		User user = UserFactory.createMockUser();
		return new MileageApplyResponse(
			1L,
			user.getUserSeq(),
			user.getName(),
			user.getEmail(),
			user.getNickname(),
			50000,
			3000,
			"W",
			LocalDateTime.of(2024, 1, 24, 0, 0),
			LocalDateTime.of(2024, 1, 24, 0, 0)

		);
	}

	public static MileageApply createMileageApplyResponse(User user) {
		return new MileageApply(
			user, 5000, "2"
		);
	}
}
