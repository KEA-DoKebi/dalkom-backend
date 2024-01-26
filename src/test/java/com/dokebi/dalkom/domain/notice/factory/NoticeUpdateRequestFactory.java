package com.dokebi.dalkom.domain.notice.factory;

import com.dokebi.dalkom.domain.notice.dto.NoticeUpdateRequest;

public class NoticeUpdateRequestFactory {

	public static NoticeUpdateRequest createNoticeUpdateRequest() {
		return new NoticeUpdateRequest(
			"updatedTitle",
			"updatedContent",
			1L,
			"N"
		);
	}

	public static NoticeUpdateRequest createNoticeUpdateRequest(
		String title, String content, Long adminSeq, String state
	) {
		return new NoticeUpdateRequest(title, content, adminSeq, state);
	}
}