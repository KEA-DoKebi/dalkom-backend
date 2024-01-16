package com.dokebi.dalkom.domain.product.dto;

import com.dokebi.dalkom.domain.product.entity.Product;

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
	private Product product;
	private Integer initialStockAmount;
}
