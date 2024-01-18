package com.dokebi.dalkom.domain.stock.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

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
	@NotNull(message = "ProductStockEditRequest amount NotNull 에러")
	@PositiveOrZero(message = "ProductStockEditRequest amount PositiveOrZero 에러")
	private Integer amount;
}
