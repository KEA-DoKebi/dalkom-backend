package com.dokebi.dalkom.domain.review.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.order.service.OrderDetailService;
import com.dokebi.dalkom.domain.review.dto.ReviewByProductResponse;
import com.dokebi.dalkom.domain.review.dto.ReviewByUserResponse;
import com.dokebi.dalkom.domain.review.dto.ReviewCreateRequest;
import com.dokebi.dalkom.domain.review.dto.ReviewReadResponse;
import com.dokebi.dalkom.domain.review.dto.ReviewSimpleDto;
import com.dokebi.dalkom.domain.review.dto.ReviewUpdateRequest;
import com.dokebi.dalkom.domain.review.entity.Review;
import com.dokebi.dalkom.domain.review.exception.ReviewNotFoundException;
import com.dokebi.dalkom.domain.review.factory.ReviewByProductResponseFactory;
import com.dokebi.dalkom.domain.review.factory.ReviewByUserResponseFactory;
import com.dokebi.dalkom.domain.review.factory.ReviewCreateRequestFactory;
import com.dokebi.dalkom.domain.review.factory.ReviewUpdateRequestFactory;
import com.dokebi.dalkom.domain.review.repository.ReviewRepository;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

	@InjectMocks
	private ReviewService reviewService;
	@Mock
	private ReviewRepository reviewRepository;
	@Mock
	private UserService userService;
	@Mock
	private OrderDetailService orderDetailService;
	@Captor
	private ArgumentCaptor<Review> reviewArgumentCaptor;

	@Test
	void createReviewTest() {
		// Given: 유저와 주문 상세 정보가 준비됨
		Long userSeq = 1L;
		ReviewCreateRequest request = ReviewCreateRequestFactory.createReviewCreateRequest();
		User user = mock(User.class);
		OrderDetail orderDetail = mock(OrderDetail.class);

		when(userService.readUserByUserSeq(userSeq)).thenReturn(user);
		when(orderDetailService.readOrderDetailByOrderDetailSeq(anyLong())).thenReturn(orderDetail);
		when(reviewRepository.existsByOrderDetail_OrdrDetailSeq(anyLong())).thenReturn(false);

		// When: 리뷰 생성 요청이 들어옴
		reviewService.createReview(userSeq, 1L, request);

		// Then: 리뷰가 저장소에 저장됨
		verify(reviewRepository).save(reviewArgumentCaptor.capture());
		Review capturedReview = reviewArgumentCaptor.getValue();
		assertNotNull(capturedReview);
	}

	@Test
	void readReviewListByProductTest() {
		// Given
		Long productSeq = 1L;
		Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 페이지 당 3개 항목

		ReviewByProductResponse fakeResponse1 = ReviewByProductResponseFactory.createReviewByProductResponse();
		ReviewByProductResponse fakeResponse2 = ReviewByProductResponseFactory.createReviewByProductResponse();
		ReviewByProductResponse fakeResponse3 = ReviewByProductResponseFactory.createReviewByProductResponse();

		List<ReviewByProductResponse> responseList = Arrays.asList(fakeResponse1, fakeResponse2, fakeResponse3);
		Page<ReviewByProductResponse> responsePage = new PageImpl<>(responseList, pageable, responseList.size());

		when(reviewService.readReviewListByProduct(productSeq, pageable)).thenReturn(responsePage);

		// When
		Page<ReviewByProductResponse> reviewByProductResponsePage = reviewService.readReviewListByProduct(productSeq,
			pageable);

		// Then
		assertEquals(responseList.size(), reviewByProductResponsePage.getContent().size());
		assertEquals(responseList, reviewByProductResponsePage.getContent());
	}

	@Test
	void readReviewListByUserTest() {
		// Given
		Long userSeq = 1L;
		Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 페이지 당 3개 항목

		ReviewByUserResponse fakeResponse1 = ReviewByUserResponseFactory.createReviewByUserResponse();
		ReviewByUserResponse fakeResponse2 = ReviewByUserResponseFactory.createReviewByUserResponse();
		ReviewByUserResponse fakeResponse3 = ReviewByUserResponseFactory.createReviewByUserResponse();

		List<ReviewByUserResponse> responseList = Arrays.asList(fakeResponse1, fakeResponse2, fakeResponse3);
		Page<ReviewByUserResponse> responsePage = new PageImpl<>(responseList, pageable, responseList.size());

		when(reviewService.readReviewListByUser(userSeq, pageable)).thenReturn(responsePage);

		// When
		Page<ReviewByUserResponse> reviewByUserResponsePage = reviewService.readReviewListByUser(userSeq, pageable);

		// Then
		assertEquals(responseList.size(), reviewByUserResponsePage.getContent().size());
		assertEquals(responseList, reviewByUserResponsePage.getContent());
	}

	@Test
	void updateReviewTest() {
		// Given
		User user = new User("empId", "pwd", "name", "email", "address", LocalDate.now(), "nickname", 1000);
		OrderDetail orderDetail = new OrderDetail();
		Review review = new Review(user, orderDetail, "content", 5);

		ReviewUpdateRequest request = ReviewUpdateRequestFactory.createReviewUpdateRequest();

		Long reviewSeq = 1L;
		when(reviewRepository.findByReviewSeq(reviewSeq)).thenReturn(review);

		// When
		reviewService.updateReview(reviewSeq, request);

		// Then
		verify(reviewRepository).findByReviewSeq(reviewSeq);
		verify(reviewRepository).save(any(Review.class));
		assertEquals("updatedContent", review.getContent());
		assertEquals(3, review.getRating());
	}

	@Test
	@DisplayName("리뷰 삭제 - 성공")
	void deleteReviewSuccessTest() {
		// Given
		Long reviewSeq = 1L;
		User user = new User("empId", "pwd", "name", "email", "address", LocalDate.now(), "nickname", 1000);
		OrderDetail orderDetail = new OrderDetail();
		Review review = new Review(user, orderDetail, "content", 5);

		when(reviewRepository.findById(reviewSeq)).thenReturn(Optional.of(review));

		// When
		reviewService.deleteReview(reviewSeq);

		// Then
		verify(reviewRepository).deleteById(reviewSeq);
	}

	@Test
	@DisplayName("리뷰 삭제 - 실패 (리뷰를 찾을 수 없음)")
	void deleteReviewFailTest() {
		// Given
		Long reviewSeq = 1L;
		when(reviewRepository.findById(reviewSeq)).thenReturn(Optional.empty());

		// When
		assertThrows(ReviewNotFoundException.class, () -> reviewService.deleteReview(reviewSeq));

		// Then
		verify(reviewRepository, never()).deleteById(anyLong());
	}

	@Test
	@DisplayName("제품 시퀀스별 간단한 리뷰 정보 조회 테스트")
	void readReviewSimpleByProductSeqTest() {
		// Given: 특정 제품 시퀀스에 대한 리뷰 정보가 준비됨
		Long productSeq = 1L;
		List<ReviewSimpleDto> mockResponse = List.of(mock(ReviewSimpleDto.class));

		when(reviewRepository.readReviewSimpleByProductSeq(productSeq)).thenReturn(mockResponse);

		// When: 제품 시퀀스별 리뷰 정보 조회 요청이 들어옴
		List<ReviewSimpleDto> response = reviewService.readReviewSimpleByProductSeq(productSeq);

		// Then: 조회된 정보가 반환됨
		assertNotNull(response);
		assertEquals(mockResponse.size(), response.size());
	}

	@Test
	@DisplayName("단일 리뷰 조회 테스트")
	void readReviewByReviewSeqTest() {
		// Given: 특정 리뷰 시퀀스에 대한 리뷰 정보가 준비됨
		Long reviewSeq = 1L;
		ReviewReadResponse mockResponse = mock(ReviewReadResponse.class);

		when(reviewRepository.findReviewByReviewSeq(reviewSeq)).thenReturn(Optional.of(mockResponse));

		// When: 단일 리뷰 조회 요청이 들어옴
		ReviewReadResponse response = reviewService.readReviewByReviewSeq(reviewSeq);

		// Then: 조회된 리뷰 정보가 반환됨
		assertNotNull(response);
	}

}