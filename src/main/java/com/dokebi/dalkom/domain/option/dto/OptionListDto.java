package com.dokebi.dalkom.domain.option.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
// ReadProductDetailResponse의 List용 DTO
public class OptionListDto {

	private Long productOptionSeq;
	private String detail;
}
