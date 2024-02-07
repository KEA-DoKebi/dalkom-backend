package com.dokebi.dalkom.domain.jira.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JiraInquiryAnswerRequest {
	private String description;
	private String nickname;
	private String jiraToken;
}
