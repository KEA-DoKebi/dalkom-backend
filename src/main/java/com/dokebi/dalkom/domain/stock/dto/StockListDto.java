package com.dokebi.dalkom.domain.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
// ReadProductDetailResponse의 List용 DTO
public class StockListDto {

	private Long productStockSeq;
	private Integer amount;
}
