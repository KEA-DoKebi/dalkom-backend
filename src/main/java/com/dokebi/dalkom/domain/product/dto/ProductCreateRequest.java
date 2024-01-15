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
public class ProductCreateRequest {
	private Product product;
	private Integer initialStockAmount;

}
