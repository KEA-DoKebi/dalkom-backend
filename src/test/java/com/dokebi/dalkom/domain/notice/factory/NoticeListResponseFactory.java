package com.dokebi.dalkom.domain.notice.factory;

import java.time.LocalDateTime;

import com.dokebi.dalkom.domain.notice.dto.NoticeListResponse;

public class NoticeListResponseFactory {

	public static NoticeListResponse createNoticeListResponse() {
		return new NoticeListResponse(
			1L,
			"title",
			"content",
			LocalDateTime.of(2024, 1, 15, 0, 22, 32),
			LocalDateTime.of(2024, 1, 15, 0, 22, 32),
			"nickname",
			"Y"
		);
	}

	public static NoticeListResponse createNoticeListResponse(
		Long noticeSeq, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt,
		String nickname, String state) {
		return new NoticeListResponse(noticeSeq, title, content, createdAt, modifiedAt, nickname, state);
	}
}

