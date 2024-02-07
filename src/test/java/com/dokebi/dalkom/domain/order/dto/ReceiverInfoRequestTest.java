package com.dokebi.dalkom.domain.order.dto;

import static com.dokebi.dalkom.domain.order.factory.ReceiverInfoRequestFactory.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReceiverInfoRequestTest {
	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void ReceiverInfoRequestValidation() {
		ReceiverInfoRequest receiverInfoRequest = createReceiverInfoRequest();
		Set<ConstraintViolation<ReceiverInfoRequest>> violations =
			validator.validate(receiverInfoRequest);

		assertEquals(0, violations.size(), "위반 사항이 없습니다.");
	}

	@Test
	void ReceiverInfoRequestReceiverNameNotBlank() {
		ReceiverInfoRequest receiverInfoRequest = createReceiverInfoRequest("", // receiverName
			"123 Main St",          // receiverAddress
			"010-1234-5678",        // receiverMobileNum
			"Special instructions");

		Set<ConstraintViolation<ReceiverInfoRequest>> violations = validator.validate(receiverInfoRequest);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReceiverInfoRequest receiverName NotBlank 에러");
	}

	@Test
	void ReceiverInfoRequestReceiverAddressNotBlank() {
		ReceiverInfoRequest receiverInfoRequest = createReceiverInfoRequest("John Doe", // receiverName
			"",          // receiverAddress
			"010-1234-5678",        // receiverMobileNum
			"Special instructions");

		Set<ConstraintViolation<ReceiverInfoRequest>> violations = validator.validate(receiverInfoRequest);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReceiverInfoRequest receiverAddress NotBlank 에러");
	}

	@Test
	void ReceiverInfoRequestReceiverMobileNumNotBlank() {
		ReceiverInfoRequest receiverInfoRequest = createReceiverInfoRequest("John Doe", // receiverName
			"123 Main St",          // receiverAddress
			"",        // receiverMobileNum
			"Special instructions");

		Set<ConstraintViolation<ReceiverInfoRequest>> violations = validator.validate(receiverInfoRequest);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReceiverInfoRequest receiverMobileNum NotBlank 에러");
	}

	@Test
	void ReceiverInfoRequestReceiverMemoNotBlank() {
		ReceiverInfoRequest receiverInfoRequest = createReceiverInfoRequest("John Doe", // receiverName
			"123 Main St",          // receiverAddress
			"010-1234-5678",        // receiverMobileNum
			"");

		Set<ConstraintViolation<ReceiverInfoRequest>> violations = validator.validate(receiverInfoRequest);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("ReceiverInfoRequest receiverMemo NotBlank 에러");
	}
}
