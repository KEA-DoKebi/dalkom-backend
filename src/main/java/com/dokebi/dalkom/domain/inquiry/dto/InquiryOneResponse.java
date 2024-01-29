package com.dokebi.dalkom.domain.inquiry.dto;

import java.time.LocalDateTime;

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
public class InquiryOneResponse {

	private String title;
	private String content;
	private LocalDateTime createdAt;
	private String answerContent;
	private LocalDateTime answeredAt;
	private String nickname; // 관리자 닉네임

	public InquiryOneResponse(String title, String content, LocalDateTime createdAt) {
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
	}
}
