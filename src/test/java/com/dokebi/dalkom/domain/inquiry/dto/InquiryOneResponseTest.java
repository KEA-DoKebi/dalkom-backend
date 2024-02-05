package com.dokebi.dalkom.domain.inquiry.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InquiryOneResponseTest {
	@Test
	@DisplayName("Getter 테스트")
	void inquiryOneResponseGetterTest() {
		// Given
		String title = "Test Inquiry";
		String content = "This is a test inquiry content.";
		LocalDateTime createdAt = LocalDateTime.now();

		// When
		InquiryOneResponse inquiryResponse = new InquiryOneResponse(
			title, content, createdAt
		);

		// Then
		assertNotNull(inquiryResponse);
		assertEquals(title, inquiryResponse.getTitle());
		assertEquals(content, inquiryResponse.getContent());
		assertEquals(createdAt, inquiryResponse.getCreatedAt());

		assertNull(inquiryResponse.getAnswerContent());
		assertNull(inquiryResponse.getAnsweredAt());
		assertNull(inquiryResponse.getNickname());
	}

	@Test
	@DisplayName("Setter 테스트")
	void inquiryOneResponseSetterTest() {
		// Given
		InquiryOneResponse inquiryResponse = new InquiryOneResponse();

		// When
		String title = "Test Inquiry";
		String content = "This is a test inquiry content.";
		LocalDateTime createdAt = LocalDateTime.now();
		String answerContent = "This is a test answer.";
		LocalDateTime answeredAt = LocalDateTime.now();
		String nickname = "JohnDoe";

		inquiryResponse.setTitle(title);
		inquiryResponse.setContent(content);
		inquiryResponse.setCreatedAt(createdAt);
		inquiryResponse.setAnswerContent(answerContent);
		inquiryResponse.setAnsweredAt(answeredAt);
		inquiryResponse.setNickname(nickname);

		// Then
		assertEquals(title, inquiryResponse.getTitle());
		assertEquals(content, inquiryResponse.getContent());
		assertEquals(createdAt, inquiryResponse.getCreatedAt());
		assertEquals(answerContent, inquiryResponse.getAnswerContent());
		assertEquals(answeredAt, inquiryResponse.getAnsweredAt());
		assertEquals(nickname, inquiryResponse.getNickname());
	}
}
