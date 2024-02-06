package com.dokebi.dalkom.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class OrderDetailSimpleResponse {
	private String productName;
	private String imageUrl;
	private Long optionSeq;
	private String detail;
}
