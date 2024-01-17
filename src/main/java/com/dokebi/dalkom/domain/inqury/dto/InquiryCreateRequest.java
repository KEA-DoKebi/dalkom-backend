package com.dokebi.dalkom.domain.inqury.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryCreateRequest {

	private String title;
	private String content;
	private Long categorySeq;
}
