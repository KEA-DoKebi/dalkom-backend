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
	private String state;
	private String imageUrl;
	private String company;
	private String optionDetail;
	private Integer amount;

	public ReadProductResponse(String name, Integer price) {
		this.name = name;
		this.price = price;
	}
}
