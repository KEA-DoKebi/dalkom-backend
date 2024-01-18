package com.dokebi.dalkom.domain.cart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCartCreateRequest {

	@NotBlank(message = "OrderCartCreateRequest productSeq NotBlank 에러")
	@PositiveOrZero(message = "OrderCartCreateRequest productSeq PositiveOrZero 에러")
	private Long productSeq;

	@NotBlank(message = "OrderCartCreateRequest prdtOptionSeq NotBlank 에러")
	@PositiveOrZero(message = "OrderCartCreateRequest prdtOptionSeq  PositiveOrZero에러")
	private Long prdtOptionSeq;

	@NotBlank(message = "OrderCartCreateRequest amount NotBlank 에러")
	@Positive(message = "OrderCartCreateRequest amount Positive 에러")
	private Integer amount;
}
