package com.dokebi.dalkom.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeCreateRequest {

	private String title;
	private String content;
	private String state;
	private Long adminSeq;
}
