package com.dokebi.dalkom.domain.jira.dto;

import lombok.AllArgsConstructor;
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
<<<<<<< HEAD
	private String InquiryCategory;
=======
>>>>>>> 4b8f81afc49e6512730462d214f424d2bb7b2043
}
