package com.dokebi.dalkom.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ReceiverDetailDto {
	private String receiverName;
	private String receiverMobileNum;
	private String receiverAddress;
	private String receiverMemo;
}
