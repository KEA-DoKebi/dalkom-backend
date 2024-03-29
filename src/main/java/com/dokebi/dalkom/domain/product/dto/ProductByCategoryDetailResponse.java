package com.dokebi.dalkom.domain.product.dto;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductByCategoryDetailResponse {
	private String categoryName;
	private Page<ProductByCategoryDetailPage> page;
}
