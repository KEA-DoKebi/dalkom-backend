package com.dokebi.dalkom.domain.admin.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.dokebi.dalkom.domain.admin.dto.MonthlyCategoryListDto;
import com.dokebi.dalkom.domain.admin.dto.MonthlyPriceListDto;
import com.dokebi.dalkom.domain.admin.dto.MonthlyProductListDto;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AdminMapperTest {

	@Autowired
	private AdminMapper adminMapper;

	@Test
	@DisplayName("findTotalPrice 테스트")
	void findTotalPrice() {
		// When
		Integer totalPrice = adminMapper.findTotalPrice();

		// Then
		assertNotNull(totalPrice);
	}

	@Test
	@DisplayName("findTotalMonthlyPrice 테스트")
	void findTotalMonthlyPrice() {
		// When
		Integer totalMonthlyPrice = adminMapper.findTotalMonthlyPrice();

		// Then
		assertNotNull(totalMonthlyPrice);
	}

	@Test
	@DisplayName("findTotalDailyPrice 테스트")
	void findTotalDailyPrice() {
		// When
		Integer totalDailyPrice = adminMapper.findTotalDailyPrice();

		// Then
		assertNotNull(totalDailyPrice);
	}

	@Test
	@DisplayName("findMonthlyPriceList 테스트")
	void findMonthlyPriceList() {
		// When
		List<MonthlyPriceListDto> priceList = adminMapper.findMonthlyPriceList();

		// Then
		assertNotNull(priceList);
		assertFalse(priceList.isEmpty());
	}

	@Test
	@DisplayName("findMonthlyProductList 테스트")
	void findMonthlyProductList() {
		// Given
		int offset = 0;
		int limit = 10;

		// When
		List<MonthlyProductListDto> productList = adminMapper.findMonthlyProductList(offset, limit);

		// Then
		assertNotNull(productList);
		assertFalse(productList.isEmpty());
	}

	@Test
	@DisplayName("findMonthlyCategoryList 테스트")
	void findMonthlyCategoryList() {
		// When
		List<MonthlyCategoryListDto> categoryList = adminMapper.findMonthlyCategoryList();

		// Then
		assertNotNull(categoryList);
		assertFalse(categoryList.isEmpty());
	}

	@Test
	@DisplayName("countMonthlyProductList 테스트")
	void countMonthlyProductList() {
		// When
		Integer count = adminMapper.countMonthlyProductList();

		// Then
		assertNotNull(count);
		assertTrue(count >= 0);
	}
}
