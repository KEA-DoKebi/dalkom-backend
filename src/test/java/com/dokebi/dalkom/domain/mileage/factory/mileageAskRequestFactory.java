package com.dokebi.dalkom.domain.mileage.factory;

import com.dokebi.dalkom.domain.mileage.dto.MileageAskRequest;

public class mileageAskRequestFactory {
	// 기본적인 생성 메서드
	public static MileageAskRequest createMileageAskRequestFactory(int amount) {
		return new MileageAskRequest(amount);
	}
}
