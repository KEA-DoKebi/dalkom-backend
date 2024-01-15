package com.dokebi.dalkom.domain.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductByCategoryResponse {

	private Long productSeq;
	private String name;
	private Integer price;
	private Character state;
	private String imageUrl;
	private String company;
	private Integer stock;
}
