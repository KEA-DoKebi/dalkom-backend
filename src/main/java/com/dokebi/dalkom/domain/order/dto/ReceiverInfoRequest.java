package com.dokebi.dalkom.domain.order.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverInfoRequest {

	@NotBlank(message = "ReceiverInfoRequest receiverName NotBlank 에러")
	private String receiverName;

	@NotBlank(message = "ReceiverInfoRequest receiverAddress NotBlank 에러")
	private String receiverAddress;

	@NotBlank(message = "ReceiverInfoRequest receiverMobileNum NotBlank 에러")
	private String receiverMobileNum;

	@NotBlank(message = "ReceiverInfoRequest receiverMemo NotBlank 에러")
	private String receiverMemo;
}
