package com.dokebi.dalkom.domain.cart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCartCreateRequest {
	@NotNull(message = "OrderCartCreateRequest productSeq NotNull 에러")
	@PositiveOrZero(message = "OrderCartCreateRequest productSeq PositiveOrZero 에러")
	private Long productSeq;

	@NotNull(message = "OrderCartCreateRequest prdtOptionSeq NotNull 에러")
	@PositiveOrZero(message = "OrderCartCreateRequest prdtOptionSeq  PositiveOrZero 에러")
	private Long prdtOptionSeq;

	@NotNull(message = "OrderCartCreateRequest amount NotNull 에러")
	@Positive(message = "OrderCartCreateRequest amount Positive 에러")
	private Integer amount;
}
