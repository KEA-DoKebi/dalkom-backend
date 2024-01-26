package com.dokebi.dalkom.domain.order.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
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
	private LocalDateTime ordrDate;

	public void makeOrderTitle(String productName, Long productCnt) {
		System.out.println(productCnt);
		System.out.println(productCnt > 1);
		if (productCnt > 1)
			this.orderTitle = productName + " 외 " + (productCnt - 1) + " 건";
	}

}
