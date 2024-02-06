package com.dokebi.dalkom.domain.admin.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MonthlyCategoryListDtoTest {
	@Test
	void MonthlyCategoryListDtoGetter() {
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
	void MonthlyCategoryListDtoSetter() {
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
	void MonthlyCategoryListDtoEqualsAndHashCode() {
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
	void MonthlyCategoryListDtoAllArgsConstructor() {
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
	void MonthlyCategoryListDtoAllNoArgsConstructor() {
		// Create object using NoArgsConstructor
		MonthlyCategoryListDto dto = new MonthlyCategoryListDto();

		// Verify default values (assuming default values are null for reference types)
		assertNull(dto.getCategorySeq());
		assertNull(dto.getName());
		assertNull(dto.getCnt());
	}
}
