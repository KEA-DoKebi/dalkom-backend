package com.dokebi.dalkom.domain.order.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
	private String productName;
	private String imageUrl;
	private Long optionSeq;
	private String detail;
	private LocalDateTime orderDate;
	private Long ordrSeq;
	private Integer amount;
	private Integer totalPrice;
	private String ordrState;
}
