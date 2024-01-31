package com.dokebi.dalkom.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCompareDetailDto {
	private String productName;
	private String imageUrl;
	private Integer price;
}
