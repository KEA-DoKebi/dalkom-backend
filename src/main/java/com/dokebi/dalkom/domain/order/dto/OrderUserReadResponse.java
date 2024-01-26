package com.dokebi.dalkom.domain.order.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderUserReadResponse {

	//상품의 값과 개수 외
	private Long ordrSeq;
	private Integer totalPrice;
	private String ordrState;
	private LocalDateTime ordrDate;
}
