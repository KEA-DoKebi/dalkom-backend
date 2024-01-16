package com.dokebi.dalkom.domain.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// PathVariable이 이미 존재하여 amount를 받기 위해 만든 DTO
public class ProductStockEditRequest {
	private Integer amount;
}
