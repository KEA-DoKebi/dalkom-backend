package com.dokebi.dalkom.domain.stock.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;

public class ProductStockEditRequestTest {
	private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private final Validator validator = factory.getValidator();

	@Test
	void productStockEditRequestValidAmount() {
		// Given
		ProductStockEditRequest request = new ProductStockEditRequest(10);

		// When
		Set<ConstraintViolation<ProductStockEditRequest>> violations = validator.validate(request);

		// Then
		assertEquals(0, violations.size(), "유효한 amount 값에 대해 위반 사항이 없어야 합니다.");
	}

	@Test
	void productStockEditRequestAmountNotNull() {
		// Given
		ProductStockEditRequest request = new ProductStockEditRequest(null);

		// When
		Set<ConstraintViolation<ProductStockEditRequest>> violations = validator.validate(request);

		// Then
		assertEquals(1, violations.size(), "amount가 null일 경우 NotNull 위반으로 유효성 검증 오류가 발생해야 합니다.");
	}

	@Test
	void productStockEditRequestAmountPositiveOrZero() {
		// Given
		ProductStockEditRequest request = new ProductStockEditRequest(-1);

		// When
		Set<ConstraintViolation<ProductStockEditRequest>> violations = validator.validate(request);

		// Then
		assertEquals(1, violations.size(), "amount가 음수일 경우 PositiveOrZero 위반으로 유효성 검증 오류가 발생해야 합니다.");
	}
}
