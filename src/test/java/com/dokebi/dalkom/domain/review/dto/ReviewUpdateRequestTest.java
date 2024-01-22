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

import com.dokebi.dalkom.domain.review.factory.ReviewUpdateRequestFactory;

public class ReviewUpdateRequestTest {

	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void reviewUpdateRequestValidation() {
		// Given
		ReviewUpdateRequest request = ReviewUpdateRequestFactory.createReviewUpdateRequest();

		// When
		Set<ConstraintViolation<ReviewUpdateRequest>> violations =
			validator.validate(request);

		// Then
		assertEquals(0, violations.size(), "위반 사항이 없습니다.");
	}

	@Test
	void reviewUpdateRequestValidation_content_NotNull_NotBlank() {
		// Given
		ReviewUpdateRequest request = ReviewUpdateRequestFactory
			.createReviewUpdateRequest(null, 4);

		// When
		Set<ConstraintViolation<ReviewUpdateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(2)
			.extracting("message")
			.contains("ReviewUpdateRequest content NotNull 에러", "ReviewUpdateRequest content NotBlank 에러");
	}

	@Test
	void reviewUpdateRequestValidation_content_NotBlank() {
		// Given
		ReviewUpdateRequest request = ReviewUpdateRequestFactory
			.createReviewUpdateRequest("", 4);

		// When
		Set<ConstraintViolation<ReviewUpdateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReviewUpdateRequest content NotBlank 에러");
	}

	@Test
	void reviewUpdateRequestValidation_rating_NotNull() {
		// Given
		ReviewUpdateRequest request = ReviewUpdateRequestFactory
			.createReviewUpdateRequest("content", null);

		// When
		Set<ConstraintViolation<ReviewUpdateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReviewUpdateRequest rating NotNull 에러");
	}

	@Test
	void reviewUpdateRequestValidation_rating_Min() {
		// Given
		ReviewUpdateRequest request = ReviewUpdateRequestFactory
			.createReviewUpdateRequest("content", 0);

		// When
		Set<ConstraintViolation<ReviewUpdateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReviewUpdateRequest rating Min 에러");
	}

	@Test
	void reviewUpdateRequestValidation_rating_Max() {
		// Given
		ReviewUpdateRequest request = ReviewUpdateRequestFactory
			.createReviewUpdateRequest("content", 6);

		// When
		Set<ConstraintViolation<ReviewUpdateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReviewUpdateRequest rating Max 에러");
	}
}
