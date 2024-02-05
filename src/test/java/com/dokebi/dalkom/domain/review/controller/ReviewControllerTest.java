package com.dokebi.dalkom.domain.review.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.dokebi.dalkom.domain.review.dto.ReviewCreateRequest;
import com.dokebi.dalkom.domain.review.dto.ReviewUpdateRequest;
import com.dokebi.dalkom.domain.review.factory.ReviewCreateRequestFactory;
import com.dokebi.dalkom.domain.review.factory.ReviewUpdateRequestFactory;
import com.dokebi.dalkom.domain.review.service.ReviewService;
import com.dokebi.dalkom.domain.user.config.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {

	@InjectMocks
	private ReviewController reviewController;
	@Mock
	private ReviewService reviewService;
	private MockMvc mockMvc;

	@BeforeEach
	void beforeEach() {
		this.mockMvc = MockMvcBuilders
			.standaloneSetup(reviewController)
			.setCustomArgumentResolvers(
				new PageableHandlerMethodArgumentResolver(),
				new HandlerMethodArgumentResolver() {
					@Override
					public boolean supportsParameter(MethodParameter parameter) {
						return parameter.getParameterType().equals(Long.class) &&
							parameter.hasParameterAnnotation(LoginUser.class);
					}

					@Override
					public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
						NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
						return 1L; // 여기서는 userSeq를 1L로 가정하고 직접 제공
					}
				}
			)
			.build();
	}

	private String asJsonString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("REVIEW-001 상품별 리뷰 리스트 조회 테스트")
	void readReviewByProduct() throws Exception {
		// Given
		Long productSeq = 1L;

		// When, Then
		mockMvc.perform(get("/api/review/product/{productSeq}", productSeq)
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk());

		// verify를 사용하여 실제 호출 포착
		verify(reviewService).readReviewListByProduct(eq(productSeq), any(Pageable.class));

	}

	@Test
	@DisplayName("REVIEW-002 사용자별 리뷰 리스트 조회 테스트")
	void readReviewByUser() throws Exception {
		// Given
		Long userSeq = 1L;

		// When, Then
		mockMvc.perform(get("/api/review/user", userSeq)
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk());

		// 서비스 메소드 호출 검증 with pagination
		verify(reviewService).readReviewListByUser(eq(userSeq), any(Pageable.class));
	}

	@Test
	@DisplayName("REVIEW-003 주문 상품에 대한 리뷰 작성 테스트")
	void createReviewCorrected() throws Exception {
		// Given
		Long userSeq = 1L; // 가정
		Long orderDetailSeq = 1L; // 주문 상세 시퀀스
		ReviewCreateRequest request = ReviewCreateRequestFactory.createReviewCreateRequest();

		// When, Then
		mockMvc.perform(post("/api/review/{orderDetailSeq}", orderDetailSeq)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
			.andExpect(status().isOk());

		// 서비스 메소드 호출 검증
		verify(reviewService).createReview(eq(userSeq), eq(orderDetailSeq), any(ReviewCreateRequest.class));
	}

	@Test
	@DisplayName("REVIEW-004 리뷰 수정 테스트")
	void updateReview() throws Exception {
		// Given
		Long reviewSeq = 1L;
		ReviewUpdateRequest updateRequest = ReviewUpdateRequestFactory.createReviewUpdateRequest();

		// When, Then
		mockMvc.perform(put("/api/review/{reviewSeq}", reviewSeq)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(updateRequest)))
			.andExpect(status().isOk());

		verify(reviewService).updateReview(reviewSeq, updateRequest);
	}

	@Test
	@DisplayName("REVIEW-005 리뷰 삭제 테스트")
	void deleteReview() throws Exception {
		// Given
		Long reviewSeq = 1L;

		// When
		doNothing().when(reviewService).deleteReview(reviewSeq);

		// Then
		mockMvc.perform(delete("/api/review/{reviewSeq}", reviewSeq))
			.andExpect(status().isOk());

		verify(reviewService).deleteReview(reviewSeq);
	}

	@Test
	@DisplayName("REVIEW-006 단일 리뷰 조회 테스트")
	void readReview() throws Exception {
		// Given
		Long reviewSeq = 1L; // 리뷰 시퀀스

		// When, Then
		mockMvc.perform(get("/api/review/{reviewSeq}", reviewSeq))
			.andExpect(status().isOk());

		// 서비스 메소드 호출 검증
		verify(reviewService).readReviewByReviewSeq(reviewSeq);
	}
}
