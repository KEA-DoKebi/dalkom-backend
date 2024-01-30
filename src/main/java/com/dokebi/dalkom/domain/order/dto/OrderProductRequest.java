package com.dokebi.dalkom.domain.order.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductRequest {
	@NotNull(message = "OrderProductRequest productSeq NotNull 에러")
	@Positive(message = "OrderProductRequest productSeq Positive 에러")
	private long productSeq;

	@NotNull(message = "OrderProductRequest productOptionSeq NotNull 에러")
	@Positive(message = "OrderProductRequest productOptionSeq Positive 에러")
	private long productOptionSeq;

	@NotNull(message = "OrderProductRequest productAmount NotNull 에러")
	@Positive(message = "OrderProductRequest productAmount Positive 에러")
	private int productAmount;

	// @NotBlank(message = "OrderProductRequest productName NotBlank 에러")
	// private String productName;
	//
	// @NotNull(message = "OrderProductRequest productPrice NotNull 에러")
	// @Positive(message = "OrderProductRequest productPrice Positive 에러")
	// private int productPrice;
	//
	// @NotNull(message = "OrderProductRequest totalPrice NotNull 에러")
	// @Positive(message = "OrderProductRequest totalPrice Positive 에러")
	// private int totalPrice;
}
