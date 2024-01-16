package com.dokebi.dalkom.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCartCreateRequest {

	private Long productSeq;
	private Long prdtOptionSeq;
	private Integer amount;
}
