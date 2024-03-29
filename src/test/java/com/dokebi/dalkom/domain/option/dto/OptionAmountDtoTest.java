package com.dokebi.dalkom.domain.option.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class OptionAmountDtoTest {

	@Test
	public void testOptionAmountDTO() {
		// Given: 옵션과 수량 값 설정
		Long expectedPrdtOptionSeq = 1L;
		Integer expectedAmount = 100;

		// When: OptionAmountDTO 객체 생성 및 값 설정
		OptionAmountDto dto = new OptionAmountDto(expectedPrdtOptionSeq, expectedAmount);

		// Then: 설정된 값이 기대한 값과 일치하는지 검증
		assertEquals(expectedPrdtOptionSeq, dto.getPrdtOptionSeq());
		assertEquals(expectedAmount, dto.getAmount());
	}
}
