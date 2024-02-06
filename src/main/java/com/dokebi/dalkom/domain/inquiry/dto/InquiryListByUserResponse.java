package com.dokebi.dalkom.domain.inquiry.dto;

import java.time.LocalDateTime;

import com.dokebi.dalkom.common.magicnumber.InquiryAnswerState;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 사용자별 문의 조회 response (사용자용)
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class InquiryListByUserResponse {
	private Long inquirySeq;
	private String category;
	private String title;
	private LocalDateTime createdAt;
	private String answerState;
	private String answerStateName;

	public InquiryListByUserResponse(Long inquirySeq, String category, String title, LocalDateTime createdAt,
		String answerState) {
		this.inquirySeq = inquirySeq;
		this.category = category;
		this.title = title;
		this.createdAt = createdAt;
		this.answerState = answerState;
		this.answerStateName = InquiryAnswerState.getNameByState(answerState);
	}
}
