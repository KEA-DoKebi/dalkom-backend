package com.dokebi.dalkom.domain.notice.factory;

import com.dokebi.dalkom.domain.notice.dto.NoticeUpdateRequest;

public class NoticeUpdateRequestFactory {

	public static NoticeUpdateRequest createNoticeUpdateRequest() {
		return new NoticeUpdateRequest(
			"updatedTitle",
			"updatedContent",
			"N"
		);
	}

	public static NoticeUpdateRequest createNoticeUpdateRequest(
		String title, String content, String state
	) {
		return new NoticeUpdateRequest(title, content, state);
	}
}