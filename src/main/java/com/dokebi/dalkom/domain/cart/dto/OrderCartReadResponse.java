package com.dokebi.dalkom.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCartReadResponse {

	private Long orderCartSeq;
	private String imageUrl;
	private Integer price;
	private Integer amount;
}
