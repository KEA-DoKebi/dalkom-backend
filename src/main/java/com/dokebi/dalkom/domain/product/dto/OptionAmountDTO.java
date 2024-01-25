package com.dokebi.dalkom.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

// 옵션과 재고를 List로 다루기 위한 DTO
public class OptionAmountDTO {

	private Long prdtOptionSeq;
	private Integer amount;
}
