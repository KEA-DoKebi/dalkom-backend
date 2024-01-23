package com.dokebi.dalkom.domain.notice.dto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dokebi.dalkom.domain.notice.factory.NoticeUpdateRequestFactory;

public class NoticeUpdateRequestTest {

	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void noticeUpdateRequestValidation() {
		// Given
		NoticeUpdateRequest request = NoticeUpdateRequestFactory.createNoticeUpdateRequest();

		// When
		Set<ConstraintViolation<NoticeUpdateRequest>> violations =
			validator.validate(request);

		// Then
		assertEquals(0, violations.size(), "위반 사항이 없습니다.");
	}

	@Test
	void noticeUpdateRequestValidation_title_NotNull_NotBlank() {
		// Given
		NoticeUpdateRequest request = NoticeUpdateRequestFactory
			.createNoticeUpdateRequest(null, "content", 1L, "Y");

		// When
		Set<ConstraintViolation<NoticeUpdateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(2)
			.extracting("message")
			.contains("NoticeUpdateRequest title NotNull 에러", "NoticeUpdateRequest title NotBlank 에러");
	}

	@Test
	void noticeUpdateRequestValidation_title_NotBlank() {
		// Given
		NoticeUpdateRequest request = NoticeUpdateRequestFactory
			.createNoticeUpdateRequest("", "content", 1L, "Y");

		// When
		Set<ConstraintViolation<NoticeUpdateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("NoticeUpdateRequest title NotBlank 에러");
	}

	@Test
	void noticeUpdateRequestValidation_content_NotNull_NotBlank() {
		// Given
		NoticeUpdateRequest request = NoticeUpdateRequestFactory
			.createNoticeUpdateRequest("title", null, 1L, "Y");

		// When
		Set<ConstraintViolation<NoticeUpdateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(2)
			.extracting("message")
			.contains("NoticeUpdateRequest content NotNull 에러", "NoticeUpdateRequest content NotBlank 에러");
	}

	@Test
	void noticeUpdateRequestValidation_content_NotBlank() {
		// Given
		NoticeUpdateRequest request = NoticeUpdateRequestFactory
			.createNoticeUpdateRequest("title", "", 1L, "Y");

		// When
		Set<ConstraintViolation<NoticeUpdateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("NoticeUpdateRequest content NotBlank 에러");
	}

	@Test
	void noticeUpdateRequestValidation_adminSeq_NotNull() {
		// Given
		NoticeUpdateRequest request = NoticeUpdateRequestFactory
			.createNoticeUpdateRequest("title", "content", null, "Y");

		// When
		Set<ConstraintViolation<NoticeUpdateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("NoticeUpdateRequest adminSeq NotNull 에러");
	}

	@Test
	void noticeUpdateRequestValidation_state_NotNull() {
		// Given
		NoticeUpdateRequest request = NoticeUpdateRequestFactory
			.createNoticeUpdateRequest("title", "content", 1L, null);

		// When
		Set<ConstraintViolation<NoticeUpdateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("NoticeUpdateRequest state NotNull 에러");
	}

	@Test
	void noticeUpdateRequestValidation_state_Pattern() {
		// Given
		NoticeUpdateRequest request = NoticeUpdateRequestFactory
			.createNoticeUpdateRequest("title", "content", 1L, "A");

		// When
		Set<ConstraintViolation<NoticeUpdateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("NoticeUpdateRequest state Pattern 에러");
	}
}