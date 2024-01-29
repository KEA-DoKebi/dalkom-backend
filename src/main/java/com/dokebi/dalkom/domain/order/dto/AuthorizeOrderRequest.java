package com.dokebi.dalkom.domain.order.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorizeOrderRequest {
	@NotNull(message = "AuthorizeOrderRequest orderSeq NotNull 에러")
	@Positive(message = "AuthorizeOrderRequest orderSeq Positive 에러")
	private Long orderSeq;

	@NotBlank(message = "AuthorizeOrderRequest password NotBlank 에러")
	private String password;
}
