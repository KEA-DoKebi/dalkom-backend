package com.dokebi.dalkom.domain.cart.dto;

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
public class OrderCartReadResponse {
	private Long orderCartSeq;
	private Long productSeq;
	private Long prdtOptionSeq;
	private String productName;
	private String prdtOptionDetail;
	private String imageUrl;
	private Integer price;
	private Integer amount;
	private Integer stock;
}
