package com.dokebi.dalkom.domain.order.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverInfoRequest {
	@NotNull(message = "ReceiverInfoRequest userSeq NotNull 에러")
	@Positive(message = "ReceiverInfoRequest userSeq Positive 에러")
	private long userSeq;

	@NotBlank(message = "ReceiverInfoRequest receiverName NotBlank 에러")
	private String receiverName;

	@NotBlank(message = "ReceiverInfoRequest receiverAddress NotBlank 에러")
	private String receiverAddress;

	@NotBlank(message = "ReceiverInfoRequest receiverMobileNum NotBlank 에러")
	private String receiverMobileNum;

	@NotBlank(message = "ReceiverInfoRequest receiverMemo NotBlank 에러")
	private String receiverMemo;
}
