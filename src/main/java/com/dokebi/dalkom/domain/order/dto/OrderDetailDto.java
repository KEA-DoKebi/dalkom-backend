package com.dokebi.dalkom.domain.order.dto;

import java.time.LocalDateTime;

import com.dokebi.dalkom.common.magicnumber.OrderState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
	private Long ordrDetailSeq;
	private String productName;
	private String imageUrl;
	private Long optionSeq;
	private String detail;
	private LocalDateTime orderDate;
	private Long ordrSeq;
	private Integer amount;
	private Integer totalPrice;
	private String ordrState;
	private String ordrStateName;

	public OrderDetailDto(Long ordrDetailSeq, String productName, String imageUrl, Long optionSeq, String detail,
		LocalDateTime orderDate, Long ordrSeq, Integer amount, Integer totalPrice, String ordrState) {
		this.ordrDetailSeq = ordrDetailSeq;
		this.productName = productName;
		this.imageUrl = imageUrl;
		this.optionSeq = optionSeq;
		this.detail = detail;
		this.orderDate = orderDate;
		this.ordrSeq = ordrSeq;
		this.amount = amount;
		this.totalPrice = totalPrice;
		this.ordrState = ordrState;
		this.ordrStateName = OrderState.getNameByState(ordrState);
	}
}
