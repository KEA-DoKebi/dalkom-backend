package com.dokebi.dalkom.domain.mileage.factory;

import com.dokebi.dalkom.domain.mileage.dto.MileageStateRequest;

public class MileageStateRequestFactory {

	public static MileageStateRequest createMileageStateRequestFactory(String approvedState) {
		return new MileageStateRequest(approvedState);
	}
}
