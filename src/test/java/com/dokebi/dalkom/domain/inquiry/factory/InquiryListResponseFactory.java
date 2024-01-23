package com.dokebi.dalkom.domain.inquiry.factory;

import java.time.LocalDateTime;

import com.dokebi.dalkom.domain.inquiry.dto.InquiryListResponse;

public class InquiryListResponseFactory {
	public static InquiryListResponse createInquiryListResponse() {
		return new InquiryListResponse(
			"title",
			"content",
			LocalDateTime.of(2024, 1, 15, 0, 0),
			"N",
			null,
			null
		);
	}
}
