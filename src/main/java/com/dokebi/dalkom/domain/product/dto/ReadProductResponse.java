package com.dokebi.dalkom.domain.product.dto;

import com.dokebi.dalkom.common.magicnumber.ProductActiveState;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ReadProductResponse {
	private Long productSeq;
	private String name;
	private Integer price;
	private String state;
	private String stateName;
	private String imageUrl;
	private String company;
	private String optionDetail;
	private Integer amount;

	public ReadProductResponse(String name, Integer price) {
		this.name = name;
		this.price = price;
	}

	public ReadProductResponse(Long productSeq, String name, Integer price, String state, String imageUrl,
		String company,
		String optionDetail, Integer amount) {
		this.productSeq = productSeq;
		this.name = name;
		this.price = price;
		this.state = state;
		this.imageUrl = imageUrl;
		this.company = company;
		this.optionDetail = optionDetail;
		this.amount = amount;
		this.stateName = ProductActiveState.getNameByState(state);
	}
}
