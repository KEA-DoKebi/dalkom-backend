package com.dokebi.dalkom.domain.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCartCreateRequest {

	private Long productSeq;
	private String optionDetail;
	private Integer amount;
}
