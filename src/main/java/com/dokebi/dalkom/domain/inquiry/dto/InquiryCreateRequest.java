package com.dokebi.dalkom.domain.inquiry.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class InquiryCreateRequest {
	@NotNull(message = "InquiryCreateRequest title NotNull 에러")
	@NotBlank(message = "InquiryCreateRequest title NotBlank 에러")
	private String title;

	@NotNull(message = "InquiryCreateRequest content NotNull 에러")
	@NotBlank(message = "InquiryCreateRequest content NotBlank 에러")
	private String content;

	@NotNull(message = "InquiryCreateRequest categorySeq NotNull 에러")
	@PositiveOrZero(message = "InquiryCreateRequest categorySeq PositiveOrZero 에러")
	private Long categorySeq;
}
