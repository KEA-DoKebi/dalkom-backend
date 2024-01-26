package com.dokebi.dalkom.domain.notice.factory;

import java.time.LocalDateTime;

import com.dokebi.dalkom.domain.notice.dto.NoticeOneResponse;

public class NoticeOneResponseFactory {

	public static NoticeOneResponse createNoticeOneResponse() {
		return new NoticeOneResponse(
			"title",
			"content",
			LocalDateTime.of(2024, 1, 15, 0, 22, 32),
			"nickname",
			"N"
		);
	}

	public static NoticeOneResponse createNoticeOneResponse(
		String title, String content, LocalDateTime createdAt, String nickname, String state) {
		return new NoticeOneResponse(title, content, createdAt, nickname, state);
	}
}