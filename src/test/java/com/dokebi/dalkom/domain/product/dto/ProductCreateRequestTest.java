package com.dokebi.dalkom.domain.product.dto;

import static com.dokebi.dalkom.domain.product.factory.ProductCreateRequestFactory.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductCreateRequestTest {

	private Validator validator;

	@BeforeEach
	void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("정상 작동")
	void validateProductCreateRequestSuccess() {
		ProductCreateRequest request = createProductCreateRequest();

		Set<javax.validation.ConstraintViolation<ProductCreateRequest>> violations = validator.validate(request);
		assertTrue(violations.isEmpty());
	}

	@Test
	@DisplayName("전부 실패")
	void validateProductCreateRequestFailure() {
		ProductCreateRequest request = createFalseProductCreateRequest();

		Set<javax.validation.ConstraintViolation<ProductCreateRequest>> violations = validator.validate(request);
		assertFalse(violations.isEmpty());

		// 실패한 필드 및 메시지 확인
		violations.forEach(violation -> {
			System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
		});
		// CheckList
		// categorySeq: ProductCreateRequest categorySeq Positive 에러
		// name: ProductCreateRequest name NotBlank 에러
		// price: ProductCreateRequest price Positive 에러
		// info: ProductCreateRequest info NotBlank 에러
		// state: ProductCreateRequest state Pattern 에러
		// imageUrl: ProductCreateRequest imageUrl NotBlank 에러
		// company: ProductCreateRequest company NotBlank 에러
		// prdtOptionList: ProductCreateRequest prdtOptionList NotEmpty 에러
	}
}
