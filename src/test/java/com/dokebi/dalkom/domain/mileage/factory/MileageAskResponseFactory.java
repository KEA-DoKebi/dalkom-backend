package com.dokebi.dalkom.domain.mileage.factory;

import java.time.LocalDateTime;

import com.dokebi.dalkom.domain.mileage.dto.MileageAskResponse;

public class MileageAskResponseFactory {
	public static MileageAskResponse createMileageAskResponse(){
		return new MileageAskResponse(
			1L,
			50000,
			3000,
			"N",
			LocalDateTime.of(2024, 1, 24, 0, 0)

		);
	}
}
