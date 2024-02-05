package com.dokebi.dalkom.domain.inquiry.dto;

import java.time.LocalDateTime;

import com.dokebi.dalkom.common.magicnumber.InquiryAnswerState;

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
public class InquiryListResponse {

	// 카테고리별 문의 조회 response (관리자용)
	private Long inquirySeq;
	private String title;
	private LocalDateTime createdAt;
	private String answerState;
	private String answerStateName;

	public InquiryListResponse(Long inquirySeq, String title, LocalDateTime createdAt, String answerState) {
		this.inquirySeq = inquirySeq;
		this.title = title;
		this.createdAt = createdAt;
		this.answerState = answerState;
		this.answerStateName = InquiryAnswerState.getNameByState(answerState);
	}
}
