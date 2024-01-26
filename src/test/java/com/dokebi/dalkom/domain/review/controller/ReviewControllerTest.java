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
	@DisplayName("상품별 리뷰 리스트 조회 테스트")
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
	@DisplayName("사용자별 리뷰 리스트 조회 테스트")
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
	@DisplayName("리뷰 작성 테스트")
	void createReview() throws Exception {
		// Given
		// Long userSeq = 1L;
		ReviewCreateRequest request = ReviewCreateRequestFactory.createReviewCreateRequest();

		// When, Then
		mockMvc.perform(post("/api/review/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
			.andExpect(status().isOk());

		// reviewService가 createReview를 1번 호출하여 createReview를 수행하는지 확인
		// verify(reviewService, times(1)).createReview(userSeq, request);

		// 서비스 메소드 호출 검증
		verify(reviewService).createReview(any(Long.class), any(ReviewCreateRequest.class));
	}

	@Test
	@DisplayName("리뷰 수정 테스트")
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
	@DisplayName("리뷰 삭제 테스트")
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
}