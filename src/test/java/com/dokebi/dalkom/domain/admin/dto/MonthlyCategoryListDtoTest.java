package com.dokebi.dalkom.domain.admin.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MonthlyCategoryListDtoTest {
	@Test
	@DisplayName("monthlyCategoryListDtoGetter 테스트")
	void monthlyCategoryListDtoGetterTest() {
		// Test data
		Long categorySeq = 1L;
		String name = "CategoryName";
		Long cnt = 10L;

		// Create object
		MonthlyCategoryListDto dto = new MonthlyCategoryListDto(categorySeq, name, cnt);

		// Verify values using Getter methods
		assertEquals(categorySeq, dto.getCategorySeq());
		assertEquals(name, dto.getName());
		assertEquals(cnt, dto.getCnt());
	}

	@Test
	@DisplayName("MonthlyCategoryListDtoSetter 테스트")
	void monthlyCategoryListDtoSetter() {
		// Test data
		Long categorySeq = 1L;
		String name = "CategoryName";
		Long cnt = 10L;

		// Create object
		MonthlyCategoryListDto dto = new MonthlyCategoryListDto();

		// Set values using Setter methods
		dto.setCategorySeq(categorySeq);
		dto.setName(name);
		dto.setCnt(cnt);

		// Verify values
		assertEquals(categorySeq, dto.getCategorySeq());
		assertEquals(name, dto.getName());
		assertEquals(cnt, dto.getCnt());
	}

	@Test
	@DisplayName("monthlyCategoryListDtoEqualsAndHashCode 테스트")
	void monthlyCategoryListDtoEqualsAndHashCode() {
		// Test data
		Long categorySeq = 1L;
		String name = "CategoryName";
		Long cnt = 10L;

		// Create two objects with the same values
		MonthlyCategoryListDto dto1 = new MonthlyCategoryListDto(categorySeq, name, cnt);
		MonthlyCategoryListDto dto2 = new MonthlyCategoryListDto(categorySeq, name, cnt);

		// Verify equals() method
		assertEquals(dto1, dto2);

		// Verify hashCode() method
		assertEquals(dto1.hashCode(), dto2.hashCode());
	}

	@Test
	@DisplayName("monthlyCategoryListDtoAllArgsConstructor 테스트")
	void monthlyCategoryListDtoAllArgsConstructor() {
		// Test data
		Long categorySeq = 1L;
		String name = "CategoryName";
		Long cnt = 10L;

		// Create object using AllArgsConstructor
		MonthlyCategoryListDto dto = new MonthlyCategoryListDto(categorySeq, name, cnt);

		// Verify values
		assertEquals(categorySeq, dto.getCategorySeq());
		assertEquals(name, dto.getName());
		assertEquals(cnt, dto.getCnt());
	}

	@Test
	@DisplayName("monthlyCategoryListDtoAllNoArgsConstructor 테스트")
	void monthlyCategoryListDtoAllNoArgsConstructor() {
		// Create object using NoArgsConstructor
		MonthlyCategoryListDto dto = new MonthlyCategoryListDto();

		// Verify default values (assuming default values are null for reference types)
		assertNull(dto.getCategorySeq());
		assertNull(dto.getName());
		assertNull(dto.getCnt());
	}
}
