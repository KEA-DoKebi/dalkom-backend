package com.dokebi.dalkom.domain.product.factory;

import java.util.Arrays;
import java.util.List;

import com.dokebi.dalkom.domain.option.dto.OptionAmountDto;

public class OptionAmountDtoListFactory {

	// 길이가 2인 List<OptionAmountDto>를 생성하고 반환하는 메서드
	public static List<OptionAmountDto> createList() {
		// 예시로 사용할 OptionAmountDto 객체 두 개 생성
		OptionAmountDto optionAmountDto1 = new OptionAmountDto(1L, 10); // 첫 번째 옵션 시퀀스와 수량
		OptionAmountDto optionAmountDto2 = new OptionAmountDto(2L, 20); // 두 번째 옵션 시퀀스와 수량

		// 생성된 두 객체를 리스트에 담아 반환
		return Arrays.asList(optionAmountDto1, optionAmountDto2);
	}
}