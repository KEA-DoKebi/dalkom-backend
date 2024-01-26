package com.dokebi.dalkom.domain.option.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

// 옵션과 재고를 List로 다루기 위한 DTO
public class OptionAmountDto {

	private Long prdtOptionSeq;
	private Integer amount;
}
