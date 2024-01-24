package com.dokebi.dalkom.domain.order.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderStateUpdateRequestTest {

	private Validator validator;

	@BeforeEach
	void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("유효한 값")
	void orderStateUpdateRequestValidPattern() {
		String validOrderState = "10";
		OrderStateUpdateRequest request = new OrderStateUpdateRequest(validOrderState);

		Set<ConstraintViolation<OrderStateUpdateRequest>> violations = validator.validate(request);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("유효하지 않은 값")
	void orderStateUpdateRequestInvalidPattern() {
		String invalidOrderState = "0";
		OrderStateUpdateRequest request = new OrderStateUpdateRequest(invalidOrderState);

		Set<ConstraintViolation<OrderStateUpdateRequest>> violations = validator.validate(request);
		assertThrows(ConstraintViolationException.class, () -> {
			if (!violations.isEmpty()) {
				throw new ConstraintViolationException(violations);
			}
		});
	}
}
