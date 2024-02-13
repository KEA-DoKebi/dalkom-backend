package com.dokebi.dalkom.domain.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.admin.dto.MonthlyCategoryListDto;
import com.dokebi.dalkom.domain.admin.dto.MonthlyPriceListDto;
import com.dokebi.dalkom.domain.admin.dto.MonthlyProductListDto;

@Mapper
public interface AdminMapper {
	Integer findTotalPrice();

	Integer findTotalMonthlyPrice();

	Integer findTotalDailyPrice();

	List<MonthlyPriceListDto> findMonthlyPriceList();

	List<MonthlyProductListDto> findMonthlyProductList(@Param("offset") int offset, @Param("limit") int limit);

	List<MonthlyCategoryListDto> findMonthlyCategoryList();

	Integer countMonthlyProductList();
}
