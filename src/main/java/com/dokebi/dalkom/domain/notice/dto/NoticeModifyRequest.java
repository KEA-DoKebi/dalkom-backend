package com.dokebi.dalkom.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeModifyRequest {

	private String title;
	private String content;
	private Long adminSeq;
	private String state;
}
