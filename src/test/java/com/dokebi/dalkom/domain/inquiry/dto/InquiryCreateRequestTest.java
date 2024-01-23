package com.dokebi.dalkom.domain.inquiry.dto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dokebi.dalkom.domain.inquiry.factory.InquiryCreateRequestFactory;

public class InquiryCreateRequestTest {
	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void InquiryCreateRequestValidation() {
		// Given
		InquiryCreateRequest request = InquiryCreateRequestFactory.createInquiryCreateRequest();

		// When
		Set<ConstraintViolation<InquiryCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertEquals(0, violations.size(), "위반 사항이 없습니다.");
	}

	@Test
	void InquiryCreateRequestValidation_title_NotNull() {
		// Given
		InquiryCreateRequest request = InquiryCreateRequestFactory
			.createInquiryCreateRequest(
				null,
				"content",
				1L);

		// When
		Set<ConstraintViolation<InquiryCreateRequest>> violations =
			validator.validate(request);

		// Then
		// NotBlank는 null도 에러로 인식한다.
		assertThat(violations)
			.hasSize(2)
			.extracting("message")
			.contains("InquiryCreateRequest title NotNull 에러");
	}

	@Test
	void InquiryCreateRequestValidation_title_NotBlank() {
		// Given
		InquiryCreateRequest request = InquiryCreateRequestFactory
			.createInquiryCreateRequest(
				" ",
				"content",
				1L);

		// When
		Set<ConstraintViolation<InquiryCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("InquiryCreateRequest title NotBlank 에러");
	}

	@Test
	void InquiryCreateRequestValidation_content_NotNull() {
		// Given
		InquiryCreateRequest request = InquiryCreateRequestFactory
			.createInquiryCreateRequest(
				"title",
				null,
				1L);

		// When
		Set<ConstraintViolation<InquiryCreateRequest>> violations =
			validator.validate(request);

		// Then
		// NotBlank는 null도 에러로 인식한다.
		assertThat(violations)
			.hasSize(2)
			.extracting("message")
			.contains("InquiryCreateRequest content NotNull 에러");
	}

	@Test
	void InquiryCreateRequestValidation_content_NotBlank() {
		// Given
		InquiryCreateRequest request = InquiryCreateRequestFactory
			.createInquiryCreateRequest(
				"title",
				" ",
				1L);

		// When
		Set<ConstraintViolation<InquiryCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("InquiryCreateRequest content NotBlank 에러");
	}

	@Test
	void InquiryCreateRequestValidation_categorySeq_NotNull() {
		// Given
		InquiryCreateRequest request = InquiryCreateRequestFactory
			.createInquiryCreateRequest(
				"title",
				"content",
				null);

		// When
		Set<ConstraintViolation<InquiryCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("InquiryCreateRequest categorySeq NotNull 에러");
	}

	@Test
	void InquiryCreateRequestValidation_categorySeq_PositiveOrZero() {
		// Given
		InquiryCreateRequest request = InquiryCreateRequestFactory
			.createInquiryCreateRequest(
				"title",
				"content",
				-1L);

		// When
		Set<ConstraintViolation<InquiryCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("InquiryCreateRequest categorySeq PositiveOrZero 에러");
	}
}
