package com.dokebi.dalkom.domain.order.dto;

import static com.dokebi.dalkom.domain.order.factory.OrderCreateRequestFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderCreateRequestTest {
	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void orderCreateRequestValidation() {
		OrderCreateRequest request = createOrderCreateRequest();
		Set<ConstraintViolation<OrderCreateRequest>> violations =
			validator.validate(request);

		assertEquals(0, violations.size(), "위반 사항이 없습니다.");
	}

	@Test
	void orderCreateRequestValidation_userSeq_NotNull() {
		OrderCreateRequest request = createOrderCreateRequest(
			null,
			"John Doe",
			"123 Main St",
			"555-1234",
			"Some memo",
			List.of(1L),
			List.of(2L),
			List.of(3));
		Set<ConstraintViolation<OrderCreateRequest>> violations = validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("OrderCreateRequest userSeq NotNull 에러");
	}

	@Test
	void orderCreateRequestValidation_userSeq_Positive() {
		OrderCreateRequest request = createOrderCreateRequest(
			-1L,
			"John Doe",
			"123 Main St",
			"555-1234",
			"Some memo",
			List.of(1L),
			List.of(2L),
			List.of(3));
		Set<ConstraintViolation<OrderCreateRequest>> violations = validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("OrderCreateRequest userSeq Positive 에러");
	}

	@Test
	void orderCreateRequestValidation_receiverName_NotNull() {
		OrderCreateRequest request = createOrderCreateRequest(
			1L,
			null,
			"123 Main St",
			"555-1234",
			"Some memo",
			List.of(1L),
			List.of(2L),
			List.of(3));
		Set<ConstraintViolation<OrderCreateRequest>> violations = validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("OrderCreateRequest receiverName NotNull 에러");
	}

	// DTO OrderCreateRequest receiverName Size 에러 까지함
	@Test
	void orderCreateRequestValidation_receiverName_Size() {
		OrderCreateRequest request = createOrderCreateRequest(
			1L,
			"123 Main St",
			"123 Main St",
			"555-1234",
			"Some memo",
			List.of(1L),
			List.of(2L),
			List.of(3));
		Set<ConstraintViolation<OrderCreateRequest>> violations = validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("OrderCreateRequest receiverName Size 에러");
	}

	@Test
	void orderCreateRequestValidation_receiverAddress_NotNull() {
		OrderCreateRequest request = createOrderCreateRequest(
			1L,
			null,
			"123 Main St",
			"555-1234",
			"Some memo",
			List.of(1L),
			List.of(2L),
			List.of(3));
		Set<ConstraintViolation<OrderCreateRequest>> violations = validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("OrderCreateRequest receiverName NotNull 에러");
	}
}
