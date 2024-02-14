package com.dokebi.dalkom.domain.mileage.factory;

import static com.dokebi.dalkom.domain.user.factory.UserFactory.*;

import java.time.LocalDateTime;

import com.dokebi.dalkom.domain.mileage.entity.MileageApply;
import com.dokebi.dalkom.domain.user.entity.User;

public class MileageApplyFactory {
	public static MileageApply createMileageApply(User user, Integer amount, String approvedState) {
		MileageApply mileageApply = new MileageApply(user, amount, approvedState);
		mileageApply.setUser(user);
		mileageApply.setAmount(amount);
		mileageApply.setApprovedState(approvedState);
		mileageApply.setApprovedAt(LocalDateTime.now());
		return mileageApply;
	}

	public static MileageApply createMileageApply() {
		// You can customize default values as needed
		User defaultUser = createMockUser(); // create a default user
		return createMileageApply(defaultUser, 100, "N");
	}

}
