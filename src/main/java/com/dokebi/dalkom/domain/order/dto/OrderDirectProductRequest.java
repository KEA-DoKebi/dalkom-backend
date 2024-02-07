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
public class OrderDirectProductRequest {
	@NotNull(message = "OrderProductRequest productSeq NotNull 에러")
	@Positive(message = "OrderProductRequest productSeq Positive 에러")
	private long productSeq;

	@NotNull(message = "OrderProductRequest productOptionSeq NotNull 에러")
	@Positive(message = "OrderProductRequest productOptionSeq Positive 에러")
	private long productOptionSeq;

	@NotNull(message = "OrderProductRequest productAmount NotNull 에러")
	@Positive(message = "OrderProductRequest productAmount Positive 에러")
	private int productAmount;
}
