package com.dokebi.dalkom.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPageDetailDto {
	//뷰로부터 전달받을 값

	// private Long userSeq;
	private Long productSeq;

	private Long productOptionSeq;
	//상품 양
	private Long productAmount;

	//DB에서 가져올 값
	private String productName;
	private Integer productPrice;

	// 만들어 낼 값


}
