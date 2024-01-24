package com.dokebi.dalkom.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductByCategoryDetailResponse {

	private Long productSeq;
	private String name;
	private Integer price;
	private String state;
	private String imageUrl;
	private String company;
	private Integer amount;
}
