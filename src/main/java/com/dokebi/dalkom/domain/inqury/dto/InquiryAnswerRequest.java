package com.dokebi.dalkom.domain.inqury.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryAnswerRequest {

	@NotNull(message = "InquiryAnswerRequest answerContent NotNull 에러")
	@NotBlank(message = "InquiryAnswerRequest answerContent NotBlank 에러")
	private String answerContent;

	@NotNull(message = "InquiryAnswerRequest answerState NotNull 에러")
	@Pattern(regexp = "[NY]", message = "InquiryAnswerRequest answerState Pattern 에러")
	private String answerState;
}
