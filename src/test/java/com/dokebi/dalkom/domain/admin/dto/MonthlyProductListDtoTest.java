package com.dokebi.dalkom.domain.admin.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MonthlyProductListDtoTest {
	@Test
	@DisplayName("monthlyProductListDtoGetter 테스트")
	void monthlyProductListDtoGetter() {
		// Test data
		String month = "January";
		Long productSeq = 1L;
		String name = "Product";
		String company = "Company";
		Integer price = 1000;
		Long cnt = 5L;
		Long amount = 5000L;
		Long totalPrice = 5500L;

		// Create object
		MonthlyProductListDto dto = new MonthlyProductListDto(month, productSeq, name, company, price, cnt, amount,
			totalPrice);

		// Verify values using Getter methods
		assertEquals(month, dto.getMonth());
		assertEquals(productSeq, dto.getProductSeq());
		assertEquals(name, dto.getName());
		assertEquals(company, dto.getCompany());
		assertEquals(price, dto.getPrice());
		assertEquals(cnt, dto.getCnt());
		assertEquals(amount, dto.getAmount());
		assertEquals(totalPrice, dto.getTotalPrice());
	}

	@Test
	@DisplayName("monthlyProductListDtoGetter 테스트")
	void monthlyProductListDtoSetter() {
		// Test data
		String month = "January";
		Long productSeq = 1L;
		String name = "Product";
		String company = "Company";
		Integer price = 1000;
		Long cnt = 5L;
		Long amount = 5000L;
		Long totalPrice = 5500L;

		// Create object
		MonthlyProductListDto dto = new MonthlyProductListDto();

		// Set values using Setter methods
		dto.setMonth(month);
		dto.setProductSeq(productSeq);
		dto.setName(name);
		dto.setCompany(company);
		dto.setPrice(price);
		dto.setCnt(cnt);
		dto.setAmount(amount);
		dto.setTotalPrice(totalPrice);

		// Verify values
		assertEquals(month, dto.getMonth());
		assertEquals(productSeq, dto.getProductSeq());
		assertEquals(name, dto.getName());
		assertEquals(company, dto.getCompany());
		assertEquals(price, dto.getPrice());
		assertEquals(cnt, dto.getCnt());
		assertEquals(amount, dto.getAmount());
		assertEquals(totalPrice, dto.getTotalPrice());
	}

	@Test
	@DisplayName("monthlyProductListDtoEqualsAndHashCode 테스트")
	void monthlyProductListDtoEqualsAndHashCode() {
		// Test data
		String month = "January";
		Long productSeq = 1L;
		String name = "Product";
		String company = "Company";
		Integer price = 1000;
		Long cnt = 5L;
		Long amount = 5000L;
		Long totalPrice = 5500L;

		// Create two objects with the same values
		MonthlyProductListDto dto1 = new MonthlyProductListDto(month, productSeq, name, company, price, cnt, amount,
			totalPrice);
		MonthlyProductListDto dto2 = new MonthlyProductListDto(month, productSeq, name, company, price, cnt, amount,
			totalPrice);

		// Verify equals() method
		assertEquals(dto1, dto2);

		// Verify hashCode() method
		assertEquals(dto1.hashCode(), dto2.hashCode());
	}

	@Test
	@DisplayName("monthlyProductListDtoAllArgsConstructor 테스트")
	void monthlyProductListDtoAllArgsConstructor() {
		// Test data
		String month = "January";
		Long productSeq = 1L;
		String name = "Product";
		String company = "Company";
		Integer price = 1000;
		Long cnt = 5L;
		Long amount = 5000L;
		Long totalPrice = 5500L;

		// Create object using AllArgsConstructor
		MonthlyProductListDto dto = new MonthlyProductListDto(month, productSeq, name, company, price, cnt, amount,
			totalPrice);

		// Verify values
		assertEquals(month, dto.getMonth());
		assertEquals(productSeq, dto.getProductSeq());
		assertEquals(name, dto.getName());
		assertEquals(company, dto.getCompany());
		assertEquals(price, dto.getPrice());
		assertEquals(cnt, dto.getCnt());
		assertEquals(amount, dto.getAmount());
		assertEquals(totalPrice, dto.getTotalPrice());
	}

	@Test
	@DisplayName("monthlyProductListDtoNoArgsConstructor 테스트")
	void monthlyProductListDtoNoArgsConstructor() {
		// Create object using NoArgsConstructor
		MonthlyProductListDto dto = new MonthlyProductListDto();

		// Verify default values (assuming default values are null for reference types)
		assertNull(dto.getMonth());
		assertNull(dto.getProductSeq());
		assertNull(dto.getName());
		assertNull(dto.getCompany());
		assertNull(dto.getPrice());
		assertNull(dto.getCnt());
		assertNull(dto.getAmount());
		assertNull(dto.getTotalPrice());
	}
}
