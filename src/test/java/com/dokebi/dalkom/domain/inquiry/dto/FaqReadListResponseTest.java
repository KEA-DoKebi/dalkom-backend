package com.dokebi.dalkom.domain.inquiry.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FaqReadListResponseTest {
	@Test
	@DisplayName("Getter 및 Setter 테스트")
	void faqReadListResponseGetterSetterTest() {
		// Given
		Long inquirySeq = 1L;
		LocalDateTime createdAt = LocalDateTime.now();
		String title = "Test Title";

		// When
		FaqReadListResponse response = new FaqReadListResponse();
		response.setInquirySeq(inquirySeq);
		response.setCreatedAt(createdAt);
		response.setTitle(title);

		// Then
		assertNotNull(response);
		assertEquals(inquirySeq, response.getInquirySeq());
		assertEquals(createdAt, response.getCreatedAt());
		assertEquals(title, response.getTitle());
	}
}
