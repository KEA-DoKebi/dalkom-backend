package com.dokebi.dalkom.domain.inquiry.factory;

import com.dokebi.dalkom.domain.inquiry.dto.InquiryCreateRequest;

public class InquiryCreateRequestFactory {
	public static InquiryCreateRequest createInquiryCreateRequest() {
		return new InquiryCreateRequest(
			"title",
			"content",
			1L
		);
	}

	public static InquiryCreateRequest createInquiryCreateRequest(
		String title, String content, Long categorySeq
	) {
		return new InquiryCreateRequest(
			title,
			content,
			categorySeq
		);
	}
}
