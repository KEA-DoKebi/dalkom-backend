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
public class InquiryListResponse {

	// 카테고리별 문의 조회 response (관리자용)
	private Long inquirySeq;
	private String title;
	private LocalDateTime createdAt;
	private String answerState;
}
