package com.dokebi.dalkom.domain.order.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizeOrderRequest {
	@NotBlank(message = "AuthorizeOrderRequest password NotBlank 에러")
	private String password;
}
