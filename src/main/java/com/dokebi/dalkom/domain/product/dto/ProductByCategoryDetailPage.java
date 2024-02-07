package com.dokebi.dalkom.domain.product.dto;

import com.dokebi.dalkom.common.magicnumber.ProductActiveState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductByCategoryDetailPage {
	private Long productSeq;
	private String name;
	private Integer price;
	private String state;
	private String stateName;
	private String imageUrl;
	private String company;
	private Double rating; // 리뷰 테이블에서 입력 받은 productSeq와 같은 데이터의 평점의 평균
	private Long reviewAmount; // 리뷰 테이블에서 입력받은 productSeq와 같은 데이터의 개수

	public ProductByCategoryDetailPage(Long productSeq, String name, Integer price, String state,
		String imageUrl, String company, Double rating, Long reviewAmount) {
		this.productSeq = productSeq;
		this.name = name;
		this.price = price;
		this.state = state;
		this.stateName = ProductActiveState.getNameByState((state));
		this.imageUrl = imageUrl;
		this.company = company;
		this.rating = rating;
		this.reviewAmount = reviewAmount;
	}
}
