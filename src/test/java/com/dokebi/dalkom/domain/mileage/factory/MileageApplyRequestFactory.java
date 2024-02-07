package com.dokebi.dalkom.domain.mileage.factory;

import com.dokebi.dalkom.domain.mileage.dto.MileageApplyRequest;

public class MileageApplyRequestFactory {
	// 기본적인 생성 메서드
	public static MileageApplyRequest createMileageApplyRequestFactory(int amount) {
		return new MileageApplyRequest(amount);
	}
}
