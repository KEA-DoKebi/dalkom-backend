package com.dokebi.dalkom.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadProductResponse {

	private Long productSeq;
	private String name;
	private Integer price;
	private Character state;
	private String imageUrl;
	private String company;

	public ReadProductResponse(Long productSeq, String name, Integer price,
		String state, String imageUrl, String company) {
		this.productSeq = productSeq;
		this.name = name;
		this.price = price;
		this.state = state.charAt(0);
		this.imageUrl = imageUrl;
		this.company = company;
	}
}