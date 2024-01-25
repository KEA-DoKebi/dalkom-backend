package com.dokebi.dalkom.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

// ProductCreateRequest의 List용 DTO
public class OptionAmountDTO {

	private Long prdtOptionSeq;
	private Integer amount;
}
