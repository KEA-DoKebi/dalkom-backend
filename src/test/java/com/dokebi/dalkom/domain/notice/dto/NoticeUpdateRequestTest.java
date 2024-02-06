package com.dokebi.dalkom.domain.notice.dto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dokebi.dalkom.domain.notice.factory.NoticeUpdateRequestFactory;

public class NoticeUpdateRequestTest {

	private Validator validator;

	@BeforeEach
	void beforeEach() {
		// Given: Validator 인스턴스 초기화
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void noticeUpdateRequestValidation() {
		// Given: 유효한 공지 수정 요청
		NoticeUpdateRequest request = NoticeUpdateRequestFactory.createNoticeUpdateRequest();

		// When: 요청 검증
		Set<ConstraintViolation<NoticeUpdateRequest>> violations = validator.validate(request);

		// Then: 검증 위반 사항이 없어야 함
		assertEquals(0, violations.size(), "No violations should be present for a valid request.");
	}

	@Test
	void noticeUpdateRequestValidation_title_NotNull_NotBlank() {
		// Given: 제목이 null 또는 빈 문자열인 공지 수정 요청
		NoticeUpdateRequest request = NoticeUpdateRequestFactory.createNoticeUpdateRequest(null, "content", "Y");

		// When: 요청 검증
		Set<ConstraintViolation<NoticeUpdateRequest>> violations = validator.validate(request);

		// Then: NotNull 및 NotBlank 위반 사항 확인
		assertThat(violations).hasSize(2)
			.extracting("message")
			.contains("NoticeUpdateRequest title NotNull 에러", "NoticeUpdateRequest title NotBlank 에러");
	}

	@Test
	void noticeUpdateRequestValidation_title_NotBlank() {
		// Given: 제목이 빈 문자열인 공지 수정 요청
		NoticeUpdateRequest request = NoticeUpdateRequestFactory.createNoticeUpdateRequest("", "content", "Y");

		// When: 요청 검증
		Set<ConstraintViolation<NoticeUpdateRequest>> violations = validator.validate(request);

		// Then: NotBlank 위반 사항 확인
		assertThat(violations).hasSize(1)
			.extracting("message")
			.contains("NoticeUpdateRequest title NotBlank 에러");
	}

	@Test
	void noticeUpdateRequestValidation_content_NotNull_NotBlank() {
		// Given: 내용이 null인 공지 수정 요청
		NoticeUpdateRequest request = NoticeUpdateRequestFactory.createNoticeUpdateRequest("title", null, "Y");

		// When: 요청 검증
		Set<ConstraintViolation<NoticeUpdateRequest>> violations = validator.validate(request);

		// Then: NotNull 및 NotBlank 위반 사항 확인
		assertThat(violations).hasSize(2)
			.extracting("message")
			.contains("NoticeUpdateRequest content NotNull 에러", "NoticeUpdateRequest content NotBlank 에러");
	}

	@Test
	void noticeUpdateRequestValidation_content_NotBlank() {
		// Given: 내용이 빈 문자열인 공지 수정 요청
		NoticeUpdateRequest request = NoticeUpdateRequestFactory.createNoticeUpdateRequest("title", "", "Y");

		// When: 요청 검증
		Set<ConstraintViolation<NoticeUpdateRequest>> violations = validator.validate(request);

		// Then: NotBlank 위반 사항 확인
		assertThat(violations).hasSize(1)
			.extracting("message")
			.contains("NoticeUpdateRequest content NotBlank 에러");
	}

	@Test
	void noticeUpdateRequestValidation_state_NotNull() {
		// Given: 상태 값이 null인 공지 수정 요청
		NoticeUpdateRequest request = NoticeUpdateRequestFactory.createNoticeUpdateRequest("title", "content", null);

		// When: 요청 검증
		Set<ConstraintViolation<NoticeUpdateRequest>> violations = validator.validate(request);

		// Then: NotNull 위반 사항 확인
		assertThat(violations).hasSize(1)
			.extracting("message")
			.contains("NoticeUpdateRequest state NotNull 에러");
	}

	@Test
	void noticeUpdateRequestValidation_state_Pattern() {
		// Given: 상태 값이 유효한 패턴에 맞지 않는 공지 수정 요청 ('A'는 유효하지 않음)
		NoticeUpdateRequest request = NoticeUpdateRequestFactory.createNoticeUpdateRequest("title", "content", "A");

		// When: 요청 검증
		Set<ConstraintViolation<NoticeUpdateRequest>> violations = validator.validate(request);

		// Then: Pattern 위반 사항 확인
		assertThat(violations).hasSize(1)
			.extracting("message")
			.contains("NoticeUpdateRequest state Pattern 에러");
	}
}
