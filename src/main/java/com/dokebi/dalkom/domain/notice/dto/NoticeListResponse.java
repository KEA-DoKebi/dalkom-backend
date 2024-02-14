package com.dokebi.dalkom.domain.notice.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class NoticeListResponse {

	private Long noticeSeq;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private String nickname;
	private String state;

	@QueryProjection
	public NoticeListResponse(Long noticeSeq, String title, String content, LocalDateTime createdAt,
		LocalDateTime modifiedAt, String nickname, String state) {
		this.noticeSeq = noticeSeq;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
		this.nickname = nickname;
		this.state = state;
	}

}
