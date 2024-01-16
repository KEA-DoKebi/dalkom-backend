package com.dokebi.dalkom.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// ProductCreateRequest의 List용 DTO
public class OptionAmountDTO {
	private Long prdtOptionSeq;
	private Integer amount;
}
