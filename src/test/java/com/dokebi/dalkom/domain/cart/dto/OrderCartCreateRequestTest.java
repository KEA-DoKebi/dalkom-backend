package com.dokebi.dalkom.domain.cart.dto;

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

import com.dokebi.dalkom.domain.cart.factory.OrderCartCreateRequestFactory;

public class OrderCartCreateRequestTest {
	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("orderCartCreateRequestValidation 테스트")
	void orderCartCreateRequestValidation() {
		// Given
		OrderCartCreateRequest request = OrderCartCreateRequestFactory.createOrderCartCreateRequest();

		// When
		Set<ConstraintViolation<OrderCartCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertEquals(0, violations.size(), "위반 사항이 없습니다.");
	}

	@Test
	@DisplayName("orderCartCreateRequestValidation_productSeq_NotNull 테스트")
	void orderCartCreateRequestValidation_productSeq_NotNull() {
		// Given
		OrderCartCreateRequest request = OrderCartCreateRequestFactory
			.createOrderCartCreateRequest(null, 1L, 5);

		// When
		Set<ConstraintViolation<OrderCartCreateRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("OrderCartCreateRequest productSeq NotNull 에러");
	}

	@Test
	@DisplayName("orderCartCreateRequestValidation_productSeq_PositiveOrZero 테스트")
	void orderCartCreateRequestValidation_productSeq_PositiveOrZero() {
		// Given
		OrderCartCreateRequest request = OrderCartCreateRequestFactory
			.createOrderCartCreateRequest(-1L, 1L, 5);

		// When
		Set<ConstraintViolation<OrderCartCreateRequest>> violations =
			validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("OrderCartCreateRequest productSeq PositiveOrZero 에러");
	}

	@Test
	@DisplayName("orderCartCreateRequestValidation_prdtOptionSeq_NotNull 테스트")
	void orderCartCreateRequestValidation_prdtOptionSeq_NotNull() {
		// Given
		OrderCartCreateRequest request = OrderCartCreateRequestFactory
			.createOrderCartCreateRequest(1L, null, 5);

		// When
		Set<ConstraintViolation<OrderCartCreateRequest>> violations =
			validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("OrderCartCreateRequest prdtOptionSeq NotNull 에러");
	}

	@Test
	@DisplayName("orderCartCreateRequestValidation_prdtOptionSeq_PositiveOrZero 테스트")
	void orderCartCreateRequestValidation_prdtOptionSeq_PositiveOrZero() {
		// Given
		OrderCartCreateRequest request = OrderCartCreateRequestFactory
			.createOrderCartCreateRequest(1L, -1L, 5);

		// When
		Set<ConstraintViolation<OrderCartCreateRequest>> violations =
			validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("OrderCartCreateRequest prdtOptionSeq  PositiveOrZero 에러");
	}

	@Test
	@DisplayName("orderCartCreateRequestValidation_amount_NotNull 테스트")
	void orderCartCreateRequestValidation_amount_NotNull() {
		// Given
		OrderCartCreateRequest request = OrderCartCreateRequestFactory
			.createOrderCartCreateRequest(1L, 1L, null);

		// When
		Set<ConstraintViolation<OrderCartCreateRequest>> violations =
			validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("OrderCartCreateRequest amount NotNull 에러");
	}

	@Test
	@DisplayName("orderCartCreateRequestValidation_amount_Positive 테스트")
	void orderCartCreateRequestValidation_amount_Positive() {
		// Given
		OrderCartCreateRequest request = OrderCartCreateRequestFactory
			.createOrderCartCreateRequest(1L, 1L, 0);

		// When
		Set<ConstraintViolation<OrderCartCreateRequest>> violations =
			validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("OrderCartCreateRequest amount Positive 에러");
	}
}
