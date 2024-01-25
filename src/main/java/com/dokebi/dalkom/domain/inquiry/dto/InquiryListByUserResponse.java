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
public class InquiryListByUserResponse {

	// 사용자별 문의 조회 response (사용자용)
	private Long inquirySeq;
	private String category;
	private String title;
	private LocalDateTime createdAt;
	private String answerState;
}
