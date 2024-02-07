package com.dokebi.dalkom.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ProductCompareDetailResponse {
	private ProductCompareDetailDto productCompareDetailDto;
	private Double rating;
	private Integer reviewNum;
	private String goodReviewSummery;
	private String badReviewSummery;
}
