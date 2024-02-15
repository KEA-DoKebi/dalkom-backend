package com.dokebi.dalkom.domain.admin.dto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.factory.CreateAdminRequestFactory;

public class CreateAdminRequestTest {
	private Validator validator;

	@BeforeEach
	void beforeEach() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("createAdminRequestTestValidation 테스트")
	void createAdminRequestTestValidation() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory.createCreateAdminRequest();

		// When
		Set<ConstraintViolation<CreateAdminRequest>> violations =
			validator.validate(request);

		// Then
		assertEquals(0, violations.size(), "위반 사항이 없습니다.");
	}

	@Test
	@DisplayName("createAdminRequestTestValidation_adminId_NotNull 테스트")
	void createAdminRequestTestValidation_adminId_NotNull() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory
			.createCreateAdminRequest(
				null,
				"password",
				"nickname",
				"name",
				"depart"
			);

		// When
		Set<ConstraintViolation<CreateAdminRequest>> violations =
			validator.validate(request);

		// Then
		// hasSize의 경우 NotBlank가 null이 들어오면 오류라고 인식해서 2개의 오류가 발생한다.
		assertThat(violations)
			.hasSize(2)
			.extracting("message")
			.contains("CreateAdminRequest adminId notnull 에러");
	}

	@Test
	@DisplayName("createAdminRequestTestValidation_adminId_NotBlank 테스트")
	void createAdminRequestTestValidation_adminId_NotBlank() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory
			.createCreateAdminRequest(
				" ",
				"password",
				"nickname",
				"name",
				"depart"
			);

		// When
		Set<ConstraintViolation<CreateAdminRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("CreateAdminRequest adminId notblank 에러");
	}

	@Test
	@DisplayName("createAdminRequestTestValidation_password_NotNull 테스트")
	void createAdminRequestTestValidation_password_NotNull() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory
			.createCreateAdminRequest(
				"adminId",
				null,
				"nickname",
				"name",
				"depart"
			);

		// When
		Set<ConstraintViolation<CreateAdminRequest>> violations =
			validator.validate(request);

		// Then
		// hasSize의 경우 NotBlank가 null이 들어오면 오류라고 인식해서 2개의 오류가 발생한다.
		assertThat(violations)
			.hasSize(2)
			.extracting("message")
			.contains("CreateAdminRequest password notnull 에러");
	}

	@Test
	@DisplayName("createAdminRequestTestValidation_password_NotBlank 테스트")
	void createAdminRequestTestValidation_password_NotBlank() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory
			.createCreateAdminRequest(
				"adminId",
				" ",
				"nickname",
				"name",
				"depart"
			);

		// When
		Set<ConstraintViolation<CreateAdminRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("CreateAdminRequest password notblank 에러");
	}

	@Test
	@DisplayName("createAdminRequestTestValidation_nickname_NotNull 테스트")
	void createAdminRequestTestValidation_nickname_NotNull() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory
			.createCreateAdminRequest(
				"adminId",
				"password",
				null,
				"name",
				"depart"
			);

		// When
		Set<ConstraintViolation<CreateAdminRequest>> violations =
			validator.validate(request);

		// Then
		// hasSize의 경우 NotBlank가 null이 들어오면 오류라고 인식해서 2개의 오류가 발생한다.
		assertThat(violations)
			.hasSize(2)
			.extracting("message")
			.contains("CreateAdminRequest nickname notnull 에러");
	}

	@Test
	@DisplayName("createAdminRequestTestValidation_nickname_NotBlank 테스트")
	void createAdminRequestTestValidation_nickname_NotBlank() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory
			.createCreateAdminRequest(
				"adminId",
				"password",
				" ",
				"name",
				"depart"
			);

		// When
		Set<ConstraintViolation<CreateAdminRequest>> violations =
			validator.validate(request);

		// Then
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("CreateAdminRequest nickname notblank 에러");
	}

	@Test
	@DisplayName("createAdminRequestTestValidation_name_NotNull 테스트")
	void createAdminRequestTestValidation_name_NotNull() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory
			.createCreateAdminRequest(
				"adminId",
				"password",
				"nickname",
				null,
				"depart"
			);

		// When
		Set<ConstraintViolation<CreateAdminRequest>> violations =
			validator.validate(request);

		// Then
		// hasSize의 경우 NotBlank가 null이 들어오면 오류라고 인식해서 2개의 오류가 발생한다.
		assertThat(violations)
			.hasSize(2)
			.extracting("message")
			.contains("CreateAdminRequest name notnull 에러");
	}

	@Test
	@DisplayName("createAdminRequestTestValidation_name_NotBlank 테스트")
	void createAdminRequestTestValidation_name_NotBlank() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory
			.createCreateAdminRequest(
				"adminId",
				"password",
				"nickname",
				" ",
				"depart"
			);

		// When
		Set<ConstraintViolation<CreateAdminRequest>> violations =
			validator.validate(request);

		// Then
		// hasSize의 경우 NotBlank가 null이 들어오면 오류라고 인식해서 2개의 오류가 발생한다.
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("CreateAdminRequest name notblank 에러");
	}

	@Test
	@DisplayName("createAdminRequestTestValidation_depart_NotNull 테스트")
	void createAdminRequestTestValidation_depart_NotNull() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory
			.createCreateAdminRequest(
				"adminId",
				"password",
				"nickname",
				"name",
				null
			);

		// When
		Set<ConstraintViolation<CreateAdminRequest>> violations =
			validator.validate(request);

		// Then
		// hasSize의 경우 NotBlank가 null이 들어오면 오류라고 인식해서 2개의 오류가 발생한다.
		assertThat(violations)
			.hasSize(2)
			.extracting("message")
			.contains("CreateAdminRequest depart notnull 에러");
	}

	@Test
	@DisplayName("createAdminRequestTestValidation_depart_NotBlank 테스트")
	void createAdminRequestTestValidation_depart_NotBlank() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory
			.createCreateAdminRequest(
				"adminId",
				"password",
				"nickname",
				"name",
				" "
			);

		// When
		Set<ConstraintViolation<CreateAdminRequest>> violations =
			validator.validate(request);

		// Then
		// hasSize의 경우 NotBlank가 null이 들어오면 오류라고 인식해서 2개의 오류가 발생한다.
		assertThat(violations)
			.hasSize(1)
			.extracting("message")
			.contains("CreateAdminRequest depart notblank 에러");
	}

	@Test
	@DisplayName("createAdminRequestToEntityTest 테스트")
	void createAdminRequestToEntityTest() {
		// Given
		CreateAdminRequest request = CreateAdminRequestFactory.createCreateAdminRequest(
			"adminId",
			"password",
			"nickname",
			"name",
			"depart"
		);

		// When
		Admin admin = CreateAdminRequest.toEntity(request);

		// Then
		assertNotNull(admin);
		assertEquals(request.getAdminId(), admin.getAdminId());
		assertEquals(request.getPassword(), admin.getPassword());
		assertEquals(request.getNickname(), admin.getNickname());
		assertEquals(request.getName(), admin.getName());
		assertEquals(request.getDepart(), admin.getDepart());
	}
}
