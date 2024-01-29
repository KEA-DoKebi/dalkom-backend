package com.dokebi.dalkom.domain.order.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorizeOrderRequest {
	@NotBlank(message = "AuthorizeOrderRequest password NotBlank 에러")
	private String password;
}
