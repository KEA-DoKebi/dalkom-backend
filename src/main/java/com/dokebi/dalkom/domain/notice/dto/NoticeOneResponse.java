package com.dokebi.dalkom.domain.notice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class NoticeOneResponse {

	private String title;
	private String content;
	private LocalDateTime createdAt;
	private String nickname;
	private String state;
}
