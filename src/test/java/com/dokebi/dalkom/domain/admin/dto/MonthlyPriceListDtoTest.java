package com.dokebi.dalkom.domain.admin.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MonthlyPriceListDtoTest {
	@Test
	@DisplayName("monthlyPriceListDtoGetter 테스트")
	void monthlyPriceListDtoGetter() {
		// Test data
		String month = "January";
		Long monthlyPrice = 1000L;

		// Create object
		MonthlyPriceListDto dto = new MonthlyPriceListDto(month, monthlyPrice);

		// Verify values using Getter methods
		assertEquals(month, dto.getMonth());
		assertEquals(monthlyPrice, dto.getMonthlyPrice());
	}

	@Test
	@DisplayName("monthlyPriceListDtoSetter 테스트")
	void monthlyPriceListDtoSetter() {
		// Test data
		String month = "January";
		Long monthlyPrice = 1000L;

		// Create object
		MonthlyPriceListDto dto = new MonthlyPriceListDto();

		// Set values using Setter methods
		dto.setMonth(month);
		dto.setMonthlyPrice(monthlyPrice);

		// Verify values
		assertEquals(month, dto.getMonth());
		assertEquals(monthlyPrice, dto.getMonthlyPrice());
	}

	@Test
	@DisplayName("monthlyPriceListDtoEqualsAndHashCode 테스트")
	void monthlyPriceListDtoEqualsAndHashCode() {
		// Test data
		String month = "January";
		Long monthlyPrice = 1000L;

		// Create two objects with the same values
		MonthlyPriceListDto dto1 = new MonthlyPriceListDto(month, monthlyPrice);
		MonthlyPriceListDto dto2 = new MonthlyPriceListDto(month, monthlyPrice);

		// Verify equals() method
		assertEquals(dto1, dto2);

		// Verify hashCode() method
		assertEquals(dto1.hashCode(), dto2.hashCode());
	}

	@Test
	@DisplayName("monthlyPriceListDtoAllArgsConstructor 테스트")
	void monthlyPriceListDtoAllArgsConstructor() {
		// Test data
		String month = "January";
		Long monthlyPrice = 1000L;

		// Create object using AllArgsConstructor
		MonthlyPriceListDto dto = new MonthlyPriceListDto(month, monthlyPrice);

		// Verify values
		assertEquals(month, dto.getMonth());
		assertEquals(monthlyPrice, dto.getMonthlyPrice());
	}

	@Test
	@DisplayName("monthlyPriceListDtoNoArgsConstructor 테스트")
	void monthlyPriceListDtoNoArgsConstructor() {
		// Create object using NoArgsConstructor
		MonthlyPriceListDto dto = new MonthlyPriceListDto();

		// Verify default values (assuming default values are null for reference types)
		assertNull(dto.getMonth());
		assertNull(dto.getMonthlyPrice());
	}
}
