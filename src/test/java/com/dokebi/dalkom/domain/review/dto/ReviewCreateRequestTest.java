package com.dokebi.dalkom.domain.review.dto;

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

public class ReviewCreateRequestTest {

	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("올바른 리뷰 생성 요청 검증")
	void reviewCreateRequestValidation() {
		ReviewCreateRequest request = new ReviewCreateRequest("Great product!", 5);

		Set<ConstraintViolation<ReviewCreateRequest>> violations = validator.validate(request);

		assertTrue(violations.isEmpty(), "No constraints should be violated for a valid request");
	}

	@Test
	@DisplayName("리뷰 내용이 null일 때 검증")
	void reviewCreateRequestValidation_content_NotNull_NotBlank() {
		ReviewCreateRequest request = new ReviewCreateRequest(null, 4);

		Set<ConstraintViolation<ReviewCreateRequest>> violations = validator.validate(request);

		assertThat(violations)
			.hasSize(2)
			.extracting("message")
			.contains("ReviewCreateRequest content NotNull 에러", "ReviewCreateRequest content NotBlank 에러");
	}

	@Test
	@DisplayName("리뷰 내용이 빈 문자열일 때 검증")
	void reviewCreateRequestValidation_content_NotBlank() {
		ReviewCreateRequest request = new ReviewCreateRequest("", 4);

		Set<ConstraintViolation<ReviewCreateRequest>> violations = validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReviewCreateRequest content NotBlank 에러");
	}

	@Test
	@DisplayName("리뷰 평점이 null일 때 검증")
	void reviewCreateRequestValidation_rating_NotNull() {
		ReviewCreateRequest request = new ReviewCreateRequest("Good service", null);

		Set<ConstraintViolation<ReviewCreateRequest>> violations = validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReviewCreateRequest rating NotNull 에러");
	}

	@Test
	@DisplayName("리뷰 평점이 최소값 미만일 때 검증")
	void reviewCreateRequestValidation_rating_Min() {
		ReviewCreateRequest request = new ReviewCreateRequest("Good service", 0);

		Set<ConstraintViolation<ReviewCreateRequest>> violations = validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReviewCreateRequest rating Min 에러");
	}

	@Test
	@DisplayName("리뷰 평점이 최대값 초과일 때 검증")
	void reviewCreateRequestValidation_rating_Max() {
		ReviewCreateRequest request = new ReviewCreateRequest("Good service", 6);

		Set<ConstraintViolation<ReviewCreateRequest>> violations = validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReviewCreateRequest rating Max 에러");
	}
}
