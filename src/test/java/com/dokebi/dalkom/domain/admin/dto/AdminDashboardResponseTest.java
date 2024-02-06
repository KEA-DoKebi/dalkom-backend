package com.dokebi.dalkom.domain.admin.dto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

public class AdminDashboardResponseTest {

	@Test
	@SuppressWarnings("unchecked")
	@DisplayName("adminDashboardResponse 테스트")
	void adminDashboardResponseTest() {
		// Mock 데이터 생성
		Integer totalMileage = 1000;
		Integer totalMonthlyMileage = 500;
		Integer totalDailyMileage = 200;

		List<MonthlyPriceListDto> monthlyPriceList = new ArrayList<>();
		// 추가적인 Mock 데이터 생성 및 리스트에 추가

		Page<MonthlyProductListDto> monthlyProductList = mock(Page.class);
		// Page<MonthlyProductListDto>에 대한 Mock 데이터 생성

		List<MonthlyCategoryListDto> monthlyCategoryList = new ArrayList<>();
		// 추가적인 Mock 데이터 생성 및 리스트에 추가

		// AdminDashboardResponse 객체 생성
		AdminDashboardResponse adminDashboardResponse = new AdminDashboardResponse(
			totalMileage,
			totalMonthlyMileage,
			totalDailyMileage,
			monthlyPriceList,
			monthlyProductList,
			monthlyCategoryList
		);

		// 검증
		assertEquals(totalMileage, adminDashboardResponse.getTotalMileage());
		assertEquals(totalMonthlyMileage, adminDashboardResponse.getTotalMonthlyMileage());
		assertEquals(totalDailyMileage, adminDashboardResponse.getTotalDailyMileage());
		assertEquals(monthlyPriceList, adminDashboardResponse.getMonthlyPriceList());
		assertEquals(monthlyProductList, adminDashboardResponse.getMonthlyProductList());
		assertEquals(monthlyCategoryList, adminDashboardResponse.getMonthlyCategoryList());
	}
}
