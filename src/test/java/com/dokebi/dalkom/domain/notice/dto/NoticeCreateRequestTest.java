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

import com.dokebi.dalkom.domain.notice.factory.NoticeCreateRequestFactory;

public class NoticeCreateRequestTest {

	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void noticeCreateRequestValidation() {
		// Given
		NoticeCreateRequest request = NoticeCreateRequestFactory.createNoticeCreateRequest();

		// When
		Set<ConstraintViolation<NoticeCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertEquals(0, violations.size(), "위반 사항이 없습니다.");
	}

	@Test
	void noticeCreateRequestValidation_title_NotNull_NotBlank() {
		// Given
		NoticeCreateRequest request = NoticeCreateRequestFactory
			.createNoticeCreateRequest(null, "content", "Y");

		// When
		Set<ConstraintViolation<NoticeCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(2)
			.extracting("message")
			.contains("NoticeCreateRequest title NotNull 에러", "NoticeCreateRequest title NotBlank 에러");
	}

	@Test
	void noticeCreateRequestValidation_title_NotBlank() {
		// Given
		NoticeCreateRequest request = NoticeCreateRequestFactory
			.createNoticeCreateRequest("", "content", "Y");

		// When
		Set<ConstraintViolation<NoticeCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("NoticeCreateRequest title NotBlank 에러");
	}

	@Test
	void noticeCreateRequestValidation_content_NotNull_NotBlank() {
		// Given
		NoticeCreateRequest request = NoticeCreateRequestFactory
			.createNoticeCreateRequest("title", null, "Y");

		// When
		Set<ConstraintViolation<NoticeCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(2)
			.extracting("message")
			.contains("NoticeCreateRequest content NotNull 에러", "NoticeCreateRequest content NotBlank 에러");
	}

	@Test
	void noticeCreateRequestValidation_content_NotBlank() {
		// Given
		NoticeCreateRequest request = NoticeCreateRequestFactory
			.createNoticeCreateRequest("title", "", "Y");

		// When
		Set<ConstraintViolation<NoticeCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("NoticeCreateRequest content NotBlank 에러");
	}

	@Test
	void noticeCreateRequestValidation_state_NotNull() {
		// Given
		NoticeCreateRequest request = NoticeCreateRequestFactory
			.createNoticeCreateRequest("title", "content", null);

		// When
		Set<ConstraintViolation<NoticeCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("NoticeCreateRequest state NotNull 에러");
	}

	@Test
	void noticeCreateRequestValidation_state_Pattern() {
		// Given
		NoticeCreateRequest request = NoticeCreateRequestFactory
			.createNoticeCreateRequest("title", "content", "A");

		// When
		Set<ConstraintViolation<NoticeCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("NoticeCreateRequest state Pattern 에러");
	}
}