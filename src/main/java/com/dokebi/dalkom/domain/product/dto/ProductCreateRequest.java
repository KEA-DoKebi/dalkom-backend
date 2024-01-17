package com.dokebi.dalkom.domain.product.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// 상품 생성 requestBody
// TODO 상품 상세 이미지 추가(후순위)
public class ProductCreateRequest {
	private Long categorySeq;
	private String name;
	private Integer price;
	private String info;
	private String state;
	private String imageUrl;
	private String company;
	private List<OptionAmountDTO> prdtOptionList;
}
