package com.dokebi.dalkom.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

// ReadProductDetailResponse의 List용 DTO
public class OptionListDTO {

	private Long productOptionSeq;
	private String detail;
}
