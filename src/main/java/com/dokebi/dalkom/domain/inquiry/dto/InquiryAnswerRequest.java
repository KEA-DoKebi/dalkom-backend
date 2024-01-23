package com.dokebi.dalkom.domain.inquiry.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class InquiryAnswerRequest {

	@NotNull(message = "InquiryAnswerRequest answerContent NotNull 에러")
	@NotBlank(message = "InquiryAnswerRequest answerContent NotBlank 에러")
	private String answerContent;
}
