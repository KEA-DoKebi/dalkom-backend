package com.dokebi.dalkom.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderReadResponse {

	private Long ordrSeq;
	private String receiverName;
	private String receiverAddress;
	private String receiverMobileNum;
	private String receiverMemo;
	private Integer totalPrice;
}
