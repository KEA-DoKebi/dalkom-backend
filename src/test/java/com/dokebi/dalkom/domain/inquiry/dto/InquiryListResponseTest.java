package com.dokebi.dalkom.domain.inquiry.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dokebi.dalkom.common.magicnumber.InquiryAnswerState;

class InquiryListResponseTest {
	@Test
	@DisplayName("Getter 테스트")
	void inquiryListResponseGetterTest() {
		// Given
		Long inquirySeq = 1L;
		String title = "Test Inquiry";
		LocalDateTime createdAt = LocalDateTime.now();
		String answerState = "PENDING";

		// When
		InquiryListResponse inquiryResponse = new InquiryListResponse(
			inquirySeq, title, createdAt, answerState
		);

		// Then
		assertNotNull(inquiryResponse);
		assertEquals(inquirySeq, inquiryResponse.getInquirySeq());
		assertEquals(title, inquiryResponse.getTitle());
		assertEquals(createdAt, inquiryResponse.getCreatedAt());
		assertEquals(answerState, inquiryResponse.getAnswerState());
		assertEquals(InquiryAnswerState.getNameByState(answerState), inquiryResponse.getAnswerStateName());
	}

	@Test
	@DisplayName("Setter 테스트")
	void inquiryListResponseSetterTest() {
		// Given
		InquiryListResponse inquiryResponse = new InquiryListResponse();

		// When
		Long inquirySeq = 1L;
		String title = "Test Inquiry";
		LocalDateTime createdAt = LocalDateTime.now();
		String answerState = "PENDING";

		inquiryResponse.setInquirySeq(inquirySeq);
		inquiryResponse.setTitle(title);
		inquiryResponse.setCreatedAt(createdAt);
		inquiryResponse.setAnswerState(answerState);

		// Then
		assertEquals(inquirySeq, inquiryResponse.getInquirySeq());
		assertEquals(title, inquiryResponse.getTitle());
		assertEquals(createdAt, inquiryResponse.getCreatedAt());
		assertEquals(answerState, inquiryResponse.getAnswerState());
		assertEquals(InquiryAnswerState.getNameByState(answerState), inquiryResponse.getAnswerStateName());
	}
}
