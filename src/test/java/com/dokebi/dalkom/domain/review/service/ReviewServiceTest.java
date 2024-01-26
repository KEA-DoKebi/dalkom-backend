package com.dokebi.dalkom.domain.review.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.order.entity.Order;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.order.service.OrderDetailService;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.review.dto.ReviewByProductResponse;
import com.dokebi.dalkom.domain.review.dto.ReviewByUserResponse;
import com.dokebi.dalkom.domain.review.dto.ReviewCreateRequest;
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
		// Given
		Long userSeq = 1L;
		ReviewCreateRequest request = ReviewCreateRequestFactory.createReviewCreateRequest();

		User user = new User("empId", "password", "name", "email@email.com",
			"address", "joinedAt", "nickname", 1200000);
		Category category = new Category("name", 1L, "imageUrl");
		Product product = new Product(category, "name", 1000, "info", "imageUrl", "company", "Y");
		Order order = new Order(user, "rcvrName", "rcvrAddress", "rcvrMobileNum", "rcvrMemo", 100000);
		ProductOption productOption = new ProductOption(1L, "optionCode", "name", "detail");
		OrderDetail orderDetail = new OrderDetail(order, product, productOption, 1, 100000);

		when(userService.readUserByUserSeq(userSeq)).thenReturn(user);
		when(orderDetailService.readOrderDetailByOrderDetailSeq(request.getOrderDetailSeq())).thenReturn(orderDetail);

		// When
		reviewService.createReview(userSeq, request);

		// Then
		verify(reviewRepository).save(reviewArgumentCaptor.capture());

		Review capturedReview = reviewArgumentCaptor.getValue();

		assertEquals(user, capturedReview.getUser());
		assertEquals(orderDetail, capturedReview.getOrderDetail());
		assertEquals(request.getContent(), capturedReview.getContent());
		assertEquals(request.getRating(), capturedReview.getRating());
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
		User user = new User("empId", "pwd", "name", "email", "address", "joinedAt", "nickname", 1000);
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
		User user = new User("empId", "pwd", "name", "email", "address", "joinedAt", "nickname", 1000);
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
}