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

	public Boolean checkValue() {
		if (categorySeq == null || categorySeq <= 0) {
			return false;
		} else if (name == null || name.isBlank()) {
			return false;
		} else if (price == null || price <= 0) {
			return false;
		} else if (info == null || info.isBlank()) {
			return false;
		} else if (state == null || (!state.equalsIgnoreCase("Y") && !state.equalsIgnoreCase("N"))) {
			return false;
		} else if (imageUrl == null || imageUrl.isBlank()) {
			return false;
		} else if (company == null || company.isBlank()) {
			return false;
		} else if (prdtOptionList == null || prdtOptionList.isEmpty()) {
			return false;
		}
		return true;
	}
}
