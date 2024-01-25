package com.dokebi.dalkom.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
// ReadProductDetailResponse에 이하의 정보를 넣기 위한 DTO
public class ReadProductDetailDTO {

	private Long categorySeq;
	private String name;
	private Integer price;
	private String info;
	private String imageUrl;
	private String company;
}
