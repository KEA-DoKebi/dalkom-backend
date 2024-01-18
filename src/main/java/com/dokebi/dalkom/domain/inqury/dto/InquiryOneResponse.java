package com.dokebi.dalkom.domain.inqury.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryOneResponse {

	private String title;
	private String content;
	private LocalDateTime createdAt;
	private String answerContent;
	private LocalDateTime answeredAt;
	private String nickname; // 관리자 닉네임
}
