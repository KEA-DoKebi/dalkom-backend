package com.dokebi.dalkom.domain.order.dto;

import java.util.List;

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

	// userSeq는 나중에 token에서 얻어야 하는데 지금은 token에서 얻지를 못하니 일단은 추가
	private Long userSeq;
	private String receiverName;
	private String receiverAddress;
	private String receiverMobileNum;
	private String receiverMemo;
	private List<Long> productSeqList;
	private List<Long> prdtOptionSeqList;
	private List<Integer> amountList;
}
