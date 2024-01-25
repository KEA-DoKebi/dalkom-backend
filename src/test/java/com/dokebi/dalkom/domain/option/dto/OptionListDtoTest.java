package com.dokebi.dalkom.domain.option.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.dokebi.dalkom.domain.option.dto.OptionListDto;

public class OptionListDtoTest {

	@Test
	public void testOptionListDTO() {
		// Given: 옵션 시퀀스와 세부사항 값 설정
		Long expectedProductOptionSeq = 1L;
		String expectedDetail = "세부사항 예시";

		// When: OptionListDTO 객체 생성 및 값 설정
		OptionListDto dto = new OptionListDto(expectedProductOptionSeq, expectedDetail);

		// Then: 설정된 값이 기대한 값과 일치하는지 검증
		assertEquals(expectedProductOptionSeq, dto.getProductOptionSeq());
		assertEquals(expectedDetail, dto.getDetail());
	}
}
