package com.dokebi.dalkom.domain.mileage.dto;

import static com.dokebi.dalkom.domain.mileage.factory.MileageApplyUpdateRequestFactory.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MileageStateRequestTest {
	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void mileageStateRequest_approvedState_NotNull() {
		MileageStateRequest request = createMileageUpdateRequestFactory(null);

		Set<ConstraintViolation<MileageStateRequest>> violations = validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("MileageStateRequest approvedState NotNull 에러");

	}

}
