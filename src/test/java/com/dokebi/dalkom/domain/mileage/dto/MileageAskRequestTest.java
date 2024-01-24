package com.dokebi.dalkom.domain.mileage.dto;

import static com.dokebi.dalkom.domain.mileage.factory.mileageAskRequestFactory.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MileageAskRequestTest {
	private Validator validator;

	@BeforeEach
	void beforeEach(){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void mileageAskRequest_amount_Positive(){
		MileageAskRequest request = createMileageAskRequestFactory(-5000);

		Set<ConstraintViolation<MileageAskRequest>> violations = validator.validate(request);

		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("MileageAskRequest amount Positive 에러");

	}
}
