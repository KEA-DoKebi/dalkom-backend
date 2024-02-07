package com.dokebi.dalkom.domain.jira.dto;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class JiraInquiryRequest {
	private String summary;
	private String description;
	private String nickname;
	private Long inquirySeq;
}
