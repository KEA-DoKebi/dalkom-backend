package com.dokebi.dalkom.domain.order.dto;

import java.time.LocalDateTime;

import com.dokebi.dalkom.common.magicnumber.OrderState;

import lombok.AllArgsConstructor;
import lombok.Generated;
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
	private String orderTitle;
	private Long productCnt;
	private Integer totalPrice;
	private String ordrState;
	private String ordrStateName;
	private LocalDateTime ordrDate;

	public void makeOrderTitle(String productName, Long productCnt) {
		System.out.println(productCnt);
		System.out.println(productCnt > 1);
		if (productCnt > 1)
			this.orderTitle = productName + " 외 " + (productCnt - 1) + " 건";
	}

	public OrderUserReadResponse(Long ordrSeq, String orderTitle, Long productCnt, Integer totalPrice, String ordrState,
		LocalDateTime ordrDate) {
		this.ordrSeq = ordrSeq;
		this.orderTitle = orderTitle;
		this.productCnt = productCnt;
		this.totalPrice = totalPrice;
		this.ordrState = ordrState;
		this.ordrDate = ordrDate;
		this.ordrStateName = OrderState.getNameByState(ordrState);
	}

}
