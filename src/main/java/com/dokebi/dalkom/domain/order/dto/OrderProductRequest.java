package com.dokebi.dalkom.domain.order.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderProductRequest {
	@NotNull(message = "OrderProductRequest productSeq NotNull 에러")
	@Positive(message = "OrderProductRequest productSeq Positive 에러")
	private Long productSeq;

	@NotNull(message = "OrderProductRequest productOptionSeq NotNull 에러")
	@Positive(message = "OrderProductRequest productOptionSeq Positive 에러")
	private Long productOptionSeq;

	@NotNull(message = "OrderProductRequest orderCartSeq NotNull 에러")
	@Positive(message = "OrderProductRequest orderCartSeq Positive 에러")
	private Long orderCartSeq;

	@NotNull(message = "OrderProductRequest productAmount NotNull 에러")
	@Positive(message = "OrderProductRequest productAmount Positive 에러")
	private int productAmount;

}
