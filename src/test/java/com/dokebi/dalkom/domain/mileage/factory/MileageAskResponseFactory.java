package com.dokebi.dalkom.domain.mileage.factory;

import java.time.LocalDateTime;

import com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse;

public class MileageAskResponseFactory {
	public static MileageApplyResponse createMileageAskResponse() {
		return new MileageApplyResponse(
			1L,
			50000,
			3000,
			"N",
			LocalDateTime.of(2024, 1, 24, 0, 0)

		);
	}
}
