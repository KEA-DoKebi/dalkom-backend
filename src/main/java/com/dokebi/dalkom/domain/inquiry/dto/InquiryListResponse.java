package com.dokebi.dalkom.domain.inquiry.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryListResponse {

	private String title;
	private String content;
	private LocalDateTime createdAt;
	private String answerState;
	private LocalDateTime answeredAt;
	private String answerContent;
}
