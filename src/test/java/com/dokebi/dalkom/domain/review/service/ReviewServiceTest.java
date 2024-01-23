package com.dokebi.dalkom.domain.review.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.order.entity.Order;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.order.service.OrderDetailService;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.review.dto.ReviewByProductResponse;
import com.dokebi.dalkom.domain.review.dto.ReviewByUserResponse;
import com.dokebi.dalkom.domain.review.dto.ReviewCreateRequest;
import com.dokebi.dalkom.domain.review.entity.Review;
import com.dokebi.dalkom.domain.review.factory.ReviewByProductResponseFactory;
import com.dokebi.dalkom.domain.review.factory.ReviewByUserResponseFactory;
import com.dokebi.dalkom.domain.review.factory.ReviewCreateRequestFactory;
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
	@Captor
	private ArgumentCaptor<Long> reviewSeqCaptor;

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
		ReviewByProductResponse fakeResponse1 = ReviewByProductResponseFactory.createReviewByProductResponse();
		ReviewByProductResponse fakeResponse2 = ReviewByProductResponseFactory.createReviewByProductResponse();
		ReviewByProductResponse fakeResponse3 = ReviewByProductResponseFactory.createReviewByProductResponse();

		List<ReviewByProductResponse> responseList = Arrays.asList(fakeResponse1, fakeResponse2, fakeResponse3);
		when(reviewService.readReviewListByProduct(productSeq)).thenReturn(responseList);

		// When
		List<ReviewByProductResponse> reviewByProductResponseList = reviewService.readReviewListByProduct(productSeq);

		// Then
		for (int i = 0; i < responseList.size(); i++) {
			ReviewByProductResponse fakeResponse = responseList.get(i);
			ReviewByProductResponse response = reviewByProductResponseList.get(i);
			assertEquals(fakeResponse, response);
		}
	}

	@Test
	void readReviewListByUserTest() {
		// Given
		Long userSeq = 1L;
		ReviewByUserResponse fakeResponse1 = ReviewByUserResponseFactory.createReviewByUserResponse();
		ReviewByUserResponse fakeResponse2 = ReviewByUserResponseFactory.createReviewByUserResponse();
		ReviewByUserResponse fakeResponse3 = ReviewByUserResponseFactory.createReviewByUserResponse();

		List<ReviewByUserResponse> responseList = Arrays.asList(fakeResponse1, fakeResponse2, fakeResponse3);
		when(reviewService.readReviewListByUser(userSeq)).thenReturn(responseList);

		// When
		List<ReviewByUserResponse> reviewByUserResponseList = reviewService.readReviewListByUser(userSeq);

		// Then
		for (int i = 0; i < responseList.size(); i++) {
			ReviewByUserResponse fakeResponse = responseList.get(i);
			ReviewByUserResponse response = reviewByUserResponseList.get(i);
			assertEquals(fakeResponse, response);
		}
	}

}