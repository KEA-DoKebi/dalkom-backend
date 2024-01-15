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

	public ProductByCategoryResponse(Long productSeq, String name, Integer price, String state, String imageUrl,
		String company, Integer stock) {
		this.productSeq = productSeq;
		this.name = name;
		this.price = price;
		this.state = state.charAt(0);
		this.imageUrl = imageUrl;
		this.company = company;
		this.stock = stock;
	}
}
