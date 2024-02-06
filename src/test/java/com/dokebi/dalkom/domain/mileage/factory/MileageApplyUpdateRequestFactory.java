package com.dokebi.dalkom.domain.mileage.factory;

import com.dokebi.dalkom.domain.mileage.dto.MileageStateRequest;

public class MileageApplyUpdateRequestFactory {
	public static MileageStateRequest createMileageUpdateRequestFactory(String approvedState) {
		return new MileageStateRequest(approvedState);
	}
}
