package com.dokebi.dalkom.domain.inquiry.factory;

import com.dokebi.dalkom.domain.inquiry.dto.FaqCreateRequest;

public class FaqCreateRequestFactory {
	public static FaqCreateRequest createFaqCreateRequest() {
		return new FaqCreateRequest("Title", "Content");
	}

	public static FaqCreateRequest createFaqCreateRequest(String title, String content) {
		return new FaqCreateRequest(title, content);
	}
}
