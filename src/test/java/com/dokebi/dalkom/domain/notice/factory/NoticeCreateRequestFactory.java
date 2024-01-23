package com.dokebi.dalkom.domain.notice.factory;

import com.dokebi.dalkom.domain.notice.dto.NoticeCreateRequest;

public class NoticeCreateRequestFactory {

	public static NoticeCreateRequest createNoticeCreateRequest() {
		return new NoticeCreateRequest(
			"title",
			"content",
			"Y"
		);
	}

	public static NoticeCreateRequest createNoticeCreateRequest(
		String title, String content, String state
	) {
		return new NoticeCreateRequest(title, content, state);
	}
}