package com.dokebi.dalkom.domain.order.dto;

import static com.dokebi.dalkom.domain.order.factory.OrderProductRequestFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderProductRequestTest {
	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void OrderProductReqeustValidation() {
		OrderProductRequest orderProductRequest = createOrderProductRequest();
		Set<ConstraintViolation<OrderProductRequest>> violations =
			validator.validate(orderProductRequest);

		assertEquals(0, violations.size(), "위반 사항이 없습니다.");
	}

	@Test
	void OrderProductReqeustProductSeqPositive() {
		OrderProductRequest orderProductRequest = createOrderProductRequest(-1L, 1L, 1L, 3);
		Set<ConstraintViolation<OrderProductRequest>> violations =
			validator.validate(orderProductRequest);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("OrderProductRequest productSeq Positive 에러");

	}

	@Test
	void OrderProductReqeustProductOptionSeqPositive() {
		OrderProductRequest orderProductRequest = createOrderProductRequest(1L, -1L, 1L, 3);
		Set<ConstraintViolation<OrderProductRequest>> violations =
			validator.validate(orderProductRequest);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("OrderProductRequest productOptionSeq Positive 에러");

	}

	@Test
	void OrderProductReqeustOptionCartSeqPositive() {
		OrderProductRequest orderProductRequest = createOrderProductRequest(1L, 1L, -1L, 3);
		Set<ConstraintViolation<OrderProductRequest>> violations =
			validator.validate(orderProductRequest);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("OrderProductRequest orderCartSeq Positive 에러");

	}

	@Test
	void OrderProductReqeustProductAmountPositive() {
		OrderProductRequest orderProductRequest = createOrderProductRequest(1L, 1L, 1L, -3);
		Set<ConstraintViolation<OrderProductRequest>> violations =
			validator.validate(orderProductRequest);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("OrderProductRequest productAmount Positive 에러");

	}

}
