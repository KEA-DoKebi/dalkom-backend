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

import com.dokebi.dalkom.domain.inquiry.factory.FaqUpdateRequestFactory;

public class FaqUpdateRequestTest {
	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("정상 동작 확인")
	void faqUpdateRequestValidation() {
		// Given
		FaqUpdateRequest request = FaqUpdateRequestFactory.createFaqUpdateRequest();

		// When
		Set<ConstraintViolation<FaqUpdateRequest>> violations = validator.validate(request);

		// Then
		assertEquals(0, violations.size(), "위반 사항이 없습니다.");
	}

	@Test
	@DisplayName("title NotNull 테스트")
	void faqUpdateRequestValidation_title_NotNull() {
		// Given
		FaqUpdateRequest request = FaqUpdateRequestFactory.createFaqUpdateRequest(null, "content");

		// When
		Set<ConstraintViolation<FaqUpdateRequest>> violations = validator.validate(request);

		// Then
		// NotBlank는 null도 에러로 인식한다.
		assertThat(violations)
			.hasSize(2)
			.extracting("message").contains("FaqUpdateRequest title NotNull 에러");
	}

	@Test
	@DisplayName("title NotBlank 테스트")
	void faqUpdateRequestValidation_title_NotBlank() {
		// Given
		FaqUpdateRequest request = FaqUpdateRequestFactory.createFaqUpdateRequest(" ", "content");

		// When
		Set<ConstraintViolation<FaqUpdateRequest>> violations = validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message").contains("FaqUpdateRequest title NotBlank 에러");
	}

	@Test
	@DisplayName("content NotNull 테스트")
	void faqUpdateRequestValidation_content_NotNull() {
		// Given
		FaqUpdateRequest request = FaqUpdateRequestFactory.createFaqUpdateRequest("title", null);

		// When
		Set<ConstraintViolation<FaqUpdateRequest>> violations = validator.validate(request);

		// Then
		// NotBlank는 null도 에러로 인식한다.
		assertThat(violations)
			.hasSize(2)
			.extracting("message").contains("FaqUpdateRequest content NotNull 에러");
	}

	@Test
	@DisplayName("content NotBlank 테스트")
	void faqUpdateRequestValidation_content_NotBlank() {
		// Given
		FaqUpdateRequest request = FaqUpdateRequestFactory.createFaqUpdateRequest("title", " ");

		// When
		Set<ConstraintViolation<FaqUpdateRequest>> violations = validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message").contains("FaqUpdateRequest content NotBlank 에러");
	}
}
