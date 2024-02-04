package com.dokebi.dalkom.domain.admin.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardResponse {

	public Integer totalMileage;
	public Integer totalMonthlyMileage;
	public Integer totalDailyMileage;
	public List<MonthlyPriceListDto> monthlyPriceList;
	public Page<MonthlyProductListDto> monthlyProductList;
	public List<MonthlyCategoryListDto> monthlyCategoryList;
}
