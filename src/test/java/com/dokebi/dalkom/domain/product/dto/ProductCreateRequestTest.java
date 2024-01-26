package com.dokebi.dalkom.domain.product.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.dokebi.dalkom.domain.option.dto.OptionAmountDto;

public class ProductCreateRequestTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	private ProductCreateRequest createValidDto() {
		return new ProductCreateRequest(
			1L, "상품명", 10000, "상품 정보", "Y", "image/url", "회사명",
			Collections.singletonList(new OptionAmountDto(1L, 10))
		);
	}

	@Test
	public void testValidDto() {
		ProductCreateRequest dto = createValidDto();
		assertTrue(validator.validate(dto).isEmpty());
	}

	@Test
	public void testInvalidCategorySeq() {
		ProductCreateRequest dto = createValidDto();
		dto.setCategorySeq(null);
		Set<ConstraintViolation<ProductCreateRequest>> violations = validator.validate(dto);
		assertFalse(violations.isEmpty());
		assertEquals("ProductCreateRequest categorySeq NotNull 에러", violations.iterator().next().getMessage());
	}

	@Test
	public void testInvalidName() {
		ProductCreateRequest dto = createValidDto();
		dto.setName(""); // 빈 문자열
		Set<ConstraintViolation<ProductCreateRequest>> violations = validator.validate(dto);
		assertFalse(violations.isEmpty());
		assertEquals("ProductCreateRequest name NotBlank 에러", violations.iterator().next().getMessage());
	}

	@Test
	public void testInvalidPrice() {
		ProductCreateRequest dto = createValidDto();
		dto.setPrice(-100); // 유효하지 않은 값
		Set<ConstraintViolation<ProductCreateRequest>> violations = validator.validate(dto);
		assertFalse(violations.isEmpty());
		assertEquals("ProductCreateRequest price Positive 에러", violations.iterator().next().getMessage());
	}

	@Test
	public void testInvalidInfo() {
		ProductCreateRequest dto = createValidDto();
		dto.setInfo(""); // 빈 문자열
		Set<ConstraintViolation<ProductCreateRequest>> violations = validator.validate(dto);
		assertFalse(violations.isEmpty());
		assertEquals("ProductCreateRequest info NotBlank 에러", violations.iterator().next().getMessage());
	}

	@Test
	public void testInvalidState() {
		ProductCreateRequest dto = createValidDto();
		dto.setState("Invalid"); // 유효하지 않은 값
		Set<ConstraintViolation<ProductCreateRequest>> violations = validator.validate(dto);
		assertFalse(violations.isEmpty());
		assertEquals("ProductCreateRequest state Pattern 에러", violations.iterator().next().getMessage());
	}

	@Test
	public void testInvalidImageUrl() {
		ProductCreateRequest dto = createValidDto();
		dto.setImageUrl(""); // 빈 문자열
		Set<ConstraintViolation<ProductCreateRequest>> violations = validator.validate(dto);
		assertFalse(violations.isEmpty());
		assertEquals("ProductCreateRequest imageUrl NotBlank 에러", violations.iterator().next().getMessage());
	}

	@Test
	public void testInvalidCompany() {
		ProductCreateRequest dto = createValidDto();
		dto.setCompany(""); // 빈 문자열
		Set<ConstraintViolation<ProductCreateRequest>> violations = validator.validate(dto);
		assertFalse(violations.isEmpty());
		assertEquals("ProductCreateRequest company NotBlank 에러", violations.iterator().next().getMessage());
	}

	@Test
	public void testInvalidPrdtOptionList() {
		ProductCreateRequest dto = createValidDto();
		dto.setPrdtOptionList(Collections.emptyList()); // 비어있는 리스트
		Set<ConstraintViolation<ProductCreateRequest>> violations = validator.validate(dto);
		assertFalse(violations.isEmpty());
		assertEquals("ProductCreateRequest prdtOptionList NotEmpty 에러", violations.iterator().next().getMessage());
	}
}
