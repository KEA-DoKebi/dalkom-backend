package com.dokebi.dalkom.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

// ReadProductDetailResponse의 List용 DTO
public class StockListDTO {

	private Long productStockSeq;
	private Integer amount;
}
