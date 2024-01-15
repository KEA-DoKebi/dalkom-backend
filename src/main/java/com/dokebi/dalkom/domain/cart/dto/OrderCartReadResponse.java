package com.dokebi.dalkom.domain.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCartReadResponse {

	private Long orderCartSeq;
	private String imageUrl;
	private Integer price;
	private Integer amount;
}
