package com.dokebi.dalkom.domain.notice.dto;

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

import com.dokebi.dalkom.domain.notice.factory.NoticeCreateRequestFactory;

public class NoticeCreateRequestTest {

	private Validator validator;

	@BeforeEach
	void beforeEach() {
		// Given: Validator 인스턴스를 초기화
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	@DisplayName("유효한 공지 생성 요청 검증")
	void noticeCreateRequestValidation() {
		// Given: 유효한 공지 생성 요청
		NoticeCreateRequest request = NoticeCreateRequestFactory.createNoticeCreateRequest();

		// When: 요청 검증
		Set<ConstraintViolation<NoticeCreateRequest>> violations = validator.validate(request);

		// Then: 위반 사항 없음 확인
		assertEquals(0, violations.size(), "Valid request should have no violations.");
	}

	@Test
	@DisplayName("제목이 없는 공지 생성 요청 검증")
	void noticeCreateRequestValidation_title_NotNull_NotBlank() {
		// Given: 제목이 null 또는 빈 문자열인 공지 생성 요청
		NoticeCreateRequest request = NoticeCreateRequestFactory.createNoticeCreateRequest(null, "content", "Y");

		// When: 요청 검증
		Set<ConstraintViolation<NoticeCreateRequest>> violations = validator.validate(request);

		// Then: NotNull 및 NotBlank 위반 사항 확인
		assertThat(violations).hasSize(2)
			.extracting("message")
			.contains("NoticeCreateRequest title NotNull 에러", "NoticeCreateRequest title NotBlank 에러");
	}

	@Test
	@DisplayName("제목이 빈 문자열인 공지 생성 요청 검증")
	void noticeCreateRequestValidation_title_NotBlank() {
		// Given: 제목이 빈 문자열인 공지 생성 요청
		NoticeCreateRequest request = NoticeCreateRequestFactory.createNoticeCreateRequest("", "content", "Y");

		// When: 요청 검증
		Set<ConstraintViolation<NoticeCreateRequest>> violations = validator.validate(request);

		// Then: NotBlank 위반 사항 확인
		assertThat(violations).hasSize(1)
			.extracting("message")
			.contains("NoticeCreateRequest title NotBlank 에러");
	}

	@Test
	@DisplayName("내용이 없는 공지 생성 요청 검증")
	void noticeCreateRequestValidation_content_NotNull_NotBlank() {
		// Given: 내용이 null인 공지 생성 요청
		NoticeCreateRequest request = NoticeCreateRequestFactory.createNoticeCreateRequest("title", null, "Y");

		// When: 요청 검증
		Set<ConstraintViolation<NoticeCreateRequest>> violations = validator.validate(request);

		// Then: NotNull 및 NotBlank 위반 사항 확인
		assertThat(violations).hasSize(2)
			.extracting("message")
			.contains("NoticeCreateRequest content NotNull 에러", "NoticeCreateRequest content NotBlank 에러");
	}

	@Test
	@DisplayName("내용이 빈 문자열인 공지 생성 요청 검증")
	void noticeCreateRequestValidation_content_NotBlank() {
		// Given: 내용이 빈 문자열인 공지 생성 요청
		NoticeCreateRequest request = NoticeCreateRequestFactory.createNoticeCreateRequest("title", "", "Y");

		// When: 요청 검증
		Set<ConstraintViolation<NoticeCreateRequest>> violations = validator.validate(request);

		// Then: NotBlank 위반 사항 확인
		assertThat(violations).hasSize(1)
			.extracting("message")
			.contains("NoticeCreateRequest content NotBlank 에러");
	}

	@Test
	@DisplayName("상태 값이 null인 공지 생성 요청 검증")
	void noticeCreateRequestValidation_state_NotNull() {
		// Given: 상태 값이 null인 공지 생성 요청
		NoticeCreateRequest request = NoticeCreateRequestFactory.createNoticeCreateRequest("title", "content", null);

		// When: 요청 검증
		Set<ConstraintViolation<NoticeCreateRequest>> violations = validator.validate(request);

		// Then: NotNull 위반 사항 확인
		assertThat(violations).hasSize(1)
			.extracting("message")
			.contains("NoticeCreateRequest state NotNull 에러");
	}

	@Test
	@DisplayName("상태 값이 유효하지 않은 패턴인 공지 생성 요청 검증")
	void noticeCreateRequestValidation_state_Pattern() {
		// Given: 상태 값이 유효하지 않은 패턴('A')인 공지 생성 요청
		NoticeCreateRequest request = NoticeCreateRequestFactory.createNoticeCreateRequest("title", "content", "A");

		// When: 요청 검증
		Set<ConstraintViolation<NoticeCreateRequest>> violations = validator.validate(request);

		// Then: Pattern 위반 사항 확인
		assertThat(violations).hasSize(1)
			.extracting("message")
			.contains("NoticeCreateRequest state Pattern 에러");
	}
}
