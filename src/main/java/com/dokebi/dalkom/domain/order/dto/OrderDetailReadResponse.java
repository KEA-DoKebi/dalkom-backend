package com.dokebi.dalkom.domain.order.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class OrderDetailReadResponse {
	private String productName;
	private LocalDateTime orderDate;
	private Long ordrSeq;
	private Integer amount;
	private Integer totalPrice;
	private String ordrState;

	private String receiverName;

	private String receiverMobieNum;
	private String receiverAddress;
	private String receiverMemo;


	// product productname
	// order orderdate
	// order orderseq
	// product amount
	// product totalprice
	// order orderstate
	// 배송지 정보
	// 유저 이름 receiverName;
	// 연락처  receiverMobileNum;
	// 배송지 주소 receiverAddress;
	// 배송 요청사항 receiverMemo;
	// 상품 합계   product totalprice
}
