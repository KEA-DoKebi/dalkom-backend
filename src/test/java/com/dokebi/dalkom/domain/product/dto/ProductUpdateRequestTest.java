package com.dokebi.dalkom.domain.product.dto;

import static com.dokebi.dalkom.domain.product.factory.ProductUpdateRequestFactory.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductUpdateRequestTest {
	private Validator validator;

	@BeforeEach
	void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("정상 작동")
	void validateProductUpdateRequestSuccess() {
		ProductUpdateRequest request = createProductUpdateRequest();

		Set<ConstraintViolation<ProductUpdateRequest>> violations = validator.validate(request);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("전부 실패")
	void validateProductUpdateRequestFailure() {
		ProductUpdateRequest request = createFalseProductUpdateRequest();

		Set<javax.validation.ConstraintViolation<ProductUpdateRequest>> violations = validator.validate(request);
		assertFalse(violations.isEmpty());

		// 실패한 필드 및 메시지 확인
		violations.forEach(violation -> {
			System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
		});
		// CheckList
		// categorySeq: ProductUpdateRequest categorySeq Positive 에러
		// name: ProductUpdateRequest name NotBlank 에러
		// price: ProductUpdateRequest price Positive 에러
		// info: ProductUpdateRequest info NotBlank 에러
		// imageUrl: ProductUpdateRequest imageUrl NotBlank 에러
		// company: ProductUpdateRequest company NotBlank 에러
		// state: ProductUpdateRequest state Pattern 에러
		// optionAmountList: ProductUpdateRequest stockByOptionList NotEmpty 에러
	}
}