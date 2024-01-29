package com.dokebi.dalkom.domain.inquiry.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class FaqCreateRequest {
	@NotNull(message = "InquiryCreateRequest title NotNull 에러")
	@NotBlank(message = "InquiryCreateRequest title NotBlank 에러")
	private String title;

	@NotNull(message = "InquiryCreateRequest content NotNull 에러")
	@NotBlank(message = "InquiryCreateRequest content NotBlank 에러")
	private String content;
}
