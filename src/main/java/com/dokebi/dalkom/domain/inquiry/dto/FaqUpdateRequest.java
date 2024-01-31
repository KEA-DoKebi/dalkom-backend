package com.dokebi.dalkom.domain.inquiry.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqUpdateRequest {
	@NotNull(message = "FaqCreateRequest title NotNull 에러")
	// @NotBlank(message = "FaqCreateRequest title NotBlank 에러")
	private String title;

	@NotNull(message = "FaqCreateRequest content NotNull 에러")
	// @NotBlank(message = "FaqCreateRequest content NotBlank 에러")
	private String content;
}
