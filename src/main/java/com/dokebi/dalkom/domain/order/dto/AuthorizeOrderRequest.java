package com.dokebi.dalkom.domain.order.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorizeOrderRequest {
	@NotNull
	@Positive
	private Long orderSeq;

	@NotBlank
	private String password;
}
