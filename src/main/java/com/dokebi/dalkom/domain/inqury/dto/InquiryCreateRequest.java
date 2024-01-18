package com.dokebi.dalkom.domain.inqury.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryCreateRequest {

	@NotNull(message = "InquiryCreateRequest title notnull 에러")
	@NotBlank(message = "InquiryCreateRequest title notblank 에러")
	private String title;

	@NotNull(message = "InquiryCreateRequest content notnull 에러")
	@NotBlank(message = "InquiryCreateRequest content notbalnk 에러")
	private String content;

	@NotNull(message = "InquiryCreateRequest categorySeq notnull 에러")
	@Min(value = 34, message = "InquiryCreateRequest categorySeq min 에러")
	@Max(value = 36, message = "InquiryCreateRequest categorySeq max 에러")
	private Long categorySeq;
}
