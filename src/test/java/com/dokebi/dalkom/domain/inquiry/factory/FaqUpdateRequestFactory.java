package com.dokebi.dalkom.domain.inquiry.factory;

import com.dokebi.dalkom.domain.inquiry.dto.FaqUpdateRequest;

public class FaqUpdateRequestFactory {
	public static FaqUpdateRequest createFaqUpdateRequest() {
		return new FaqUpdateRequest("Title", "Content");
	}

	public static FaqUpdateRequest createFaqUpdateRequest(String title, String content) {
		return new FaqUpdateRequest(title, content);
	}
}
