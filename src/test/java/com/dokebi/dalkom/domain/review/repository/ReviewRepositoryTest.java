package com.dokebi.dalkom.domain.review.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dokebi.dalkom.domain.review.dto.ReviewByProductResponse;
import com.dokebi.dalkom.domain.review.dto.ReviewByUserResponse;
import com.dokebi.dalkom.domain.review.factory.ReviewByProductResponseFactory;
import com.dokebi.dalkom.domain.review.factory.ReviewByUserResponseFactory;

@ExtendWith(MockitoExtension.class)
public class ReviewRepositoryTest {

	@Mock
	private ReviewRepository reviewRepository;

	@Test
	void findReviewListByProductTest() {

		// Given
		Long productSeq = 1L;

		ReviewByProductResponse fakeResponse1 = ReviewByProductResponseFactory.createReviewByProductResponse();
		ReviewByProductResponse fakeResponse2 = ReviewByProductResponseFactory.createReviewByProductResponse();
		ReviewByProductResponse fakeResponse3 = ReviewByProductResponseFactory.createReviewByProductResponse();

		List<ReviewByProductResponse> responseList = Arrays.asList(fakeResponse1, fakeResponse2, fakeResponse3);

		when(reviewRepository.findReviewListByProduct(productSeq)).thenReturn(responseList);

		// When
		List<ReviewByProductResponse> reviewByProductResponseList = reviewRepository.findReviewListByProduct(
			productSeq);

		// Then
		assertEquals(responseList, reviewByProductResponseList);
	}

	@Test
	void findReviewListByUserTest() {

		// Given
		Long userSeq = 1L;

		ReviewByUserResponse fakeResponse1 = ReviewByUserResponseFactory.createReviewByUserResponse();
		ReviewByUserResponse fakeResponse2 = ReviewByUserResponseFactory.createReviewByUserResponse();
		ReviewByUserResponse fakeResponse3 = ReviewByUserResponseFactory.createReviewByUserResponse();

		List<ReviewByUserResponse> responseList = Arrays.asList(fakeResponse1, fakeResponse2, fakeResponse3);

		when(reviewRepository.findReviewListByUser(userSeq)).thenReturn(responseList);

		// When
		List<ReviewByUserResponse> reviewByUserResponsesList = reviewRepository.findReviewListByUser(userSeq);

		// Then
		assertEquals(responseList, reviewByUserResponsesList);
	}
}
