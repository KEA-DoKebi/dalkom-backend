package com.dokebi.dalkom.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderPageDetailDto {
	// 뷰로부터 전달받을 값

	// private Long userSeq;
	private Long productSeq;
	private Long productOptionSeq;
	// 상품 양
	private Integer productAmount;
	// DB에서 가져올 값
	private String productName;
	private Integer productPrice;
	// 만들어 낼 값
	private Integer totalPrice;

}
