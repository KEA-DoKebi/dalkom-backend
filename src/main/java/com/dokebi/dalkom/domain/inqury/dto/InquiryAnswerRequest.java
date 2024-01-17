package com.dokebi.dalkom.domain.inqury.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryAnswerRequest {

	private String answerContent;
	private String answerState;
}
