package com.dokebi.dalkom.domain.mileage.factory;

import com.dokebi.dalkom.domain.mileage.entity.MileageApply;
import com.dokebi.dalkom.domain.user.entity.User;

public class MileageApplyResponseFactory {
	public static MileageApply createMileageApplyResponse(User user) {
		return new MileageApply(
			user, 5000, "2"
		);
	}

}
