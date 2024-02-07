package com.dokebi.dalkom.domain.order.dto;

import static com.dokebi.dalkom.domain.order.factory.AuthorizeOrderRequestFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorizeOrderRequestTest {
	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void AuthorizeOrderRequestValidation() {
		AuthorizeOrderRequest authorizeOrderRequest = createAuthorizeOrderRequest();
		Set<ConstraintViolation<AuthorizeOrderRequest>> violations =
			validator.validate(authorizeOrderRequest);

		assertEquals(0, violations.size(), "위반 사항이 없습니다.");
	}

	@Test
	void AuthorizeOrderRequestPasswordNotBlank() {
		String password = "";
		AuthorizeOrderRequest authorizeOrderRequest = createAuthorizeOrderRequest(password);
		Set<ConstraintViolation<AuthorizeOrderRequest>> violations = validator.validate(authorizeOrderRequest);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("AuthorizeOrderRequest password NotBlank 에러");

	}

}
