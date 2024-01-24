package com.dokebi.dalkom.domain.mileage.factory;

import java.time.LocalDateTime;

import com.dokebi.dalkom.domain.mileage.dto.MileageHistoryResponse;
import com.dokebi.dalkom.domain.mileage.entity.MileageApply;
import com.dokebi.dalkom.domain.user.entity.User;

public class MileageHistoryResponseFactory {

	public static MileageHistoryResponse createMileageHistoryResponse(User user) {
		return new MileageHistoryResponse(
			"1",LocalDateTime.of(2024, 1, 24, 0, 0),5000,10000);

	}
}
