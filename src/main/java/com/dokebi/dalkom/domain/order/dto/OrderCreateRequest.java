package com.dokebi.dalkom.domain.order.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderCreateRequest {

	@NotNull(message = "OrderCreateRequest userSeq NotNull 에러")
	@Positive(message = "OrderCreateRequest userSeq Positive 에러")
	private Long userSeq;

	@NotNull(message = "OrderCreateRequest receiverName NotNull 에러")
	@Size(max = 10, message = "OrderCreateRequest receiverName Size 에러")
	private String receiverName;

	@NotNull(message = "OrderCreateRequest receiverAddress NotNull 에러")
	@Size(max = 200, message = "OrderCreateRequest receiverAddress Size 에러")
	private String receiverAddress;

	@NotNull(message = "OrderCreateRequest receiverMobileNum NotNull 에러")
	@Size(max = 30, message = "OrderCreateRequest receiverMobileNum Size 에러")
	private String receiverMobileNum;

	@NotNull(message = "OrderCreateRequest receiverMemo NotNull 에러")
	private String receiverMemo;

	@NotNull(message = "OrderCreateRequest productSeqList NotNull 에러")
	@Positive(message = "OrderCreateRequest productSeqList Positive 에러")
	private List<Long> productSeqList;

	@NotNull(message = "OrderCreateRequest prdtOptionSeqList NotNull 에러")
	@Positive(message = "OrderCreateRequest prdtOptionSeqList Positive 에러")
	private List<Long> prdtOptionSeqList;

	@NotNull(message = "OrderCreateRequest amountList NotNull 에러")
	@Positive(message = "OrderCreateRequest amountList Positive 에러")
	private List<Integer> amountList;
}
