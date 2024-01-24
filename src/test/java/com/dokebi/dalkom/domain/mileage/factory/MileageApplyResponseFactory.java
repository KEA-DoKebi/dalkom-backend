package com.dokebi.dalkom.domain.mileage.factory;

import java.time.LocalDateTime;

import com.dokebi.dalkom.domain.mileage.entity.MileageApply;
import com.dokebi.dalkom.domain.user.entity.User;

public class MileageApplyResponseFactory {
	public static MileageApply createMileageApplyResponse(User user) {
		return new MileageApply(
			user, 5000, "2", LocalDateTime.of(2024, 1, 24, 0, 0)
		);
	}

}
