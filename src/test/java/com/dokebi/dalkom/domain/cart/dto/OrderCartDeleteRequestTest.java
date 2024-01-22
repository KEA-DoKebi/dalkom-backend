package com.dokebi.dalkom.domain.cart.dto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dokebi.dalkom.domain.cart.factory.OrderCartDeleteRequestFactory;

public class OrderCartDeleteRequestTest {
	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void orderCartDeleteRequestValidation() {
		// Given
		OrderCartDeleteRequest request = OrderCartDeleteRequestFactory.createOrderCartDeleteRequest();

		// When
		Set<ConstraintViolation<OrderCartDeleteRequest>> violations =
			validator.validate(request);

		// Then
		assertEquals(0, violations.size(), "위반 사항이 없습니다.");
	}

	@Test
	void orderCartDeleteRequestValidation_orderCartSeqList_NotEmpty() {
		// Given
		OrderCartDeleteRequest request = OrderCartDeleteRequestFactory.createOrderCartDeleteRequest(null);

		// When
		Set<ConstraintViolation<OrderCartDeleteRequest>> violations =
			validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("OrderCartDeleteRequest orderCartSeqList NotEmpty 에러");
	}
}
