package com.dokebi.dalkom.domain.review.dto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dokebi.dalkom.domain.review.factory.ReviewCreateRequestFactory;

public class ReviewCreateRequestTest {

	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void reviewCreateRequestValidation() {
		// Given
		ReviewCreateRequest request = ReviewCreateRequestFactory.createReviewCreateRequest();

		// When
		Set<ConstraintViolation<ReviewCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertEquals(0, violations.size(), "위반 사항이 없습니다.");
	}

	@Test
	void reviewCreateRequestValidation_orderDetailSeq_NotNull() {
		// Given
		ReviewCreateRequest request = ReviewCreateRequestFactory
			.createReviewCreateRequest(null, "content", 4);

		// When
		Set<ConstraintViolation<ReviewCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReviewCreateRequest orderDetailSeq NotNull 에러");
	}

	@Test
	void reviewCreateRequestValidation_content_NotNull_NotBlank() {
		// null 값은 두 제약조건을 모두 위반하기 때문에 null 값이 들어왔을 때 두 조건을 모두 위반하도록 작성
		// Given
		ReviewCreateRequest request = ReviewCreateRequestFactory
			.createReviewCreateRequest(1L, null, 4);

		// When
		Set<ConstraintViolation<ReviewCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(2)
			.extracting("message")
			.contains("ReviewCreateRequest content NotNull 에러", "ReviewCreateRequest content NotBlank 에러");
	}

	@Test
	void reviewCreateRequestValidation_content_NotBlank() {
		// Given
		ReviewCreateRequest request = ReviewCreateRequestFactory
			.createReviewCreateRequest(1L, "", 4);

		// When
		Set<ConstraintViolation<ReviewCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReviewCreateRequest content NotBlank 에러");
	}

	@Test
	void reviewCreateRequestValidation_rating_NotNull() {
		// Given
		ReviewCreateRequest request = ReviewCreateRequestFactory
			.createReviewCreateRequest(1L, "content", null);

		// When
		Set<ConstraintViolation<ReviewCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReviewCreateRequest rating NotNull 에러");
	}

	@Test
	void reviewCreateRequestValidation_rating_Min() {
		// Given
		ReviewCreateRequest request = ReviewCreateRequestFactory
			.createReviewCreateRequest(1L, "content", 0);

		// When
		Set<ConstraintViolation<ReviewCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReviewCreateRequest rating Min 에러");
	}

	@Test
	void reviewCreateRequestValidation_rating_Max() {
		// Given
		ReviewCreateRequest request = ReviewCreateRequestFactory
			.createReviewCreateRequest(1L, "content", 6);

		// When
		Set<ConstraintViolation<ReviewCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReviewCreateRequest rating Max 에러");
	}
}
