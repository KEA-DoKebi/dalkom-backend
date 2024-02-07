package com.dokebi.dalkom.domain.order.dto;

import java.time.LocalDateTime;

import com.dokebi.dalkom.common.magicnumber.OrderState;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor

public class OrderAdminReadResponse {
	private Long ordrSeq;
	private LocalDateTime ordrDate;
	private Long ordrCnt;
	private String name;
	private String receiveName;
	private Integer totalPrice;
	private String ordrState;
	private String ordrStateName;

	public OrderAdminReadResponse(Long ordrSeq, LocalDateTime ordrDate, Long ordrCnt, String name, String receiveName,
		Integer totalPrice, String ordrState) {
		this.ordrSeq = ordrSeq;
		this.ordrDate = ordrDate;
		this.ordrCnt = ordrCnt;
		this.name = name;
		this.receiveName = receiveName;
		this.totalPrice = totalPrice;
		this.ordrState = ordrState;
		this.ordrStateName = OrderState.getNameByState(ordrState);
	}
}
