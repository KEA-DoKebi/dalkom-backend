package com.dokebi.dalkom.domain.inquiry.dto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dokebi.dalkom.domain.inquiry.factory.FaqCreateRequestFactory;

class FaqCreateRequestTest {
	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("정상 동작 확인")
	void faqCreateRequestValidation() {
		// Given
		FaqCreateRequest request = FaqCreateRequestFactory.createFaqCreateRequest();

		// When
		Set<ConstraintViolation<FaqCreateRequest>> violations = validator.validate(request);

		// Then
		assertEquals(0, violations.size(), "위반 사항이 없습니다.");
	}

	@Test
	@DisplayName("title NotNull 테스트")
	void faqCreateRequestValidation_title_NotNull() {
		// Given
		FaqCreateRequest request = FaqCreateRequestFactory.createFaqCreateRequest(null, "content");

		// When
		Set<ConstraintViolation<FaqCreateRequest>> violations = validator.validate(request);

		// Then
		// NotBlank는 null도 에러로 인식한다.
		assertThat(violations)
			.hasSize(2)
			.extracting("message").contains("FaqCreateRequest title NotNull 에러");
	}

	@Test
	@DisplayName("title NotBlank 테스트")
	void faqCreateRequestValidation_title_NotBlank() {
		// Given
		FaqCreateRequest request = FaqCreateRequestFactory.createFaqCreateRequest(" ", "content");

		// When
		Set<ConstraintViolation<FaqCreateRequest>> violations = validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message").contains("FaqCreateRequest title NotBlank 에러");
	}

	@Test
	@DisplayName("content NotNull 테스트")
	void faqCreateRequestValidation_content_NotNull() {
		// Given
		FaqCreateRequest request = FaqCreateRequestFactory.createFaqCreateRequest("title", null);

		// When
		Set<ConstraintViolation<FaqCreateRequest>> violations = validator.validate(request);

		// Then
		// NotBlank는 null도 에러로 인식한다.
		assertThat(violations)
			.hasSize(2)
			.extracting("message").contains("FaqCreateRequest content NotNull 에러");
	}

	@Test
	@DisplayName("content NotBlank 테스트")
	void faqCreateRequestValidation_content_NotBlank() {
		// Given
		FaqCreateRequest request = FaqCreateRequestFactory.createFaqCreateRequest("title", " ");

		// When
		Set<ConstraintViolation<FaqCreateRequest>> violations = validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message").contains("FaqCreateRequest content NotBlank 에러");
	}
}
