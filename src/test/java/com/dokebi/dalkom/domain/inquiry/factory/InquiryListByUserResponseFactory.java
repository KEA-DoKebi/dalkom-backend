package com.dokebi.dalkom.domain.inquiry.factory;

import java.time.LocalDateTime;

import com.dokebi.dalkom.domain.inquiry.dto.InquiryListByUserResponse;

public class InquiryListByUserResponseFactory {

	public static InquiryListByUserResponse createInquiryListByUserResponse() {
		return new InquiryListByUserResponse(
			1L,
			"상품",
			"title",
			LocalDateTime.of(2024, 1, 15, 0, 0),
			"Y"
		);
	}
}