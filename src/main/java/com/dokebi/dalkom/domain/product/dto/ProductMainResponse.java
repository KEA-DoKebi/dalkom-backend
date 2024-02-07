package com.dokebi.dalkom.domain.product.dto;

import com.dokebi.dalkom.common.magicnumber.ProductActiveState;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ProductMainResponse {

	private Long productSeq;
	private String name;
	private Integer price;
	private String state;
	private String imageUrl;
	private String company;
	private Double rating; // 리뷰 테이블에서 입력 받은 productSeq와 같은 데이터의 평점의 평균
	private Long reviewAmount; // 리뷰 테이블에서 입력받은 productSeq와 같은 데이터의 개수
	private String stateName;

	public ProductMainResponse(Long productSeq, String name, Integer price, String state, String imageUrl,
		String company, Double rating, Long reviewAmount) {
		this.productSeq = productSeq;
		this.name = name;
		this.price = price;
		this.state = state;
		this.imageUrl = imageUrl;
		this.company = company;
		this.rating = rating;
		this.reviewAmount = reviewAmount;
		this.stateName = ProductActiveState.getNameByState(state);
	}
}
