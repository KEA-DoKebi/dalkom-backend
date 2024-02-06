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

import com.dokebi.dalkom.domain.inquiry.factory.InquiryAnswerRequestFactory;

public class InquiryAnswerRequestTest {
	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("정상 동작 테스트")
	void inquiryAnswerRequestValidation() {
		// Given
		InquiryAnswerRequest request = InquiryAnswerRequestFactory.createInquiryAnswerRequest();

		// When
		Set<ConstraintViolation<InquiryAnswerRequest>> violations =
			validator.validate(request);

		// Then
		assertEquals(0, violations.size(), "위반 사항이 없습니다.");
	}

	@Test
	@DisplayName("answerContent NotNull 테스트")
	void inquiryAnswerRequestValidation_answerContent_NotNull() {
		// Given
		InquiryAnswerRequest request = InquiryAnswerRequestFactory
			.createInquiryAnswerRequest(null);

		// When
		Set<ConstraintViolation<InquiryAnswerRequest>> violations =
			validator.validate(request);

		// Then
		// NotBlank는 null이 들어오면 안되기에 2개
		assertThat(violations)
			.hasSize(2)
			.extracting("message")
			.contains("InquiryAnswerRequest answerContent NotNull 에러");
	}

	@Test
	@DisplayName("answerContent NotBlank 테스트")
	void inquiryAnswerRequestValidation_answerContent_NotBlank() {
		// Given
		InquiryAnswerRequest request = InquiryAnswerRequestFactory
			.createInquiryAnswerRequest(" ");

		// When
		Set<ConstraintViolation<InquiryAnswerRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("InquiryAnswerRequest answerContent NotBlank 에러");
	}
}
