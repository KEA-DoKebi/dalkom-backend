package com.dokebi.dalkom.domain.review.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
		Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 페이지 당 3개 항목

		ReviewByProductResponse fakeResponse1 = ReviewByProductResponseFactory.createReviewByProductResponse();
		ReviewByProductResponse fakeResponse2 = ReviewByProductResponseFactory.createReviewByProductResponse();
		ReviewByProductResponse fakeResponse3 = ReviewByProductResponseFactory.createReviewByProductResponse();
		List<ReviewByProductResponse> responseList = Arrays.asList(fakeResponse1, fakeResponse2, fakeResponse3);

		Page<ReviewByProductResponse> responsePage = new PageImpl<>(responseList, pageable, responseList.size());

		when(reviewRepository.findReviewListByProduct(productSeq, pageable)).thenReturn(responsePage);

		// When
		Page<ReviewByProductResponse> reviewByProductResponsePage = reviewRepository.findReviewListByProduct(productSeq,
			pageable);

		// Then
		assertEquals(responseList.size(), reviewByProductResponsePage.getContent().size());
		assertEquals(responseList, reviewByProductResponsePage.getContent());
	}

	@Test
	void findReviewListByUserTest() {
		// Given
		Long userSeq = 1L;
		Pageable pageable = PageRequest.of(0, 3); // Example for first page with 3 items per page

		ReviewByUserResponse fakeResponse1 = ReviewByUserResponseFactory.createReviewByUserResponse();
		ReviewByUserResponse fakeResponse2 = ReviewByUserResponseFactory.createReviewByUserResponse();
		ReviewByUserResponse fakeResponse3 = ReviewByUserResponseFactory.createReviewByUserResponse();
		List<ReviewByUserResponse> responseList = Arrays.asList(fakeResponse1, fakeResponse2, fakeResponse3);

		Page<ReviewByUserResponse> responsePage = new PageImpl<>(responseList, pageable,
			responseList.size()); // Mocked page response

		when(reviewRepository.findReviewListByUser(eq(userSeq), any(Pageable.class))).thenReturn(responsePage);

		// When
		Page<ReviewByUserResponse> reviewByUserResponsePage = reviewRepository.findReviewListByUser(userSeq, pageable);

		// Then
		assertEquals(responseList.size(), reviewByUserResponsePage.getContent().size());
		assertEquals(responseList, reviewByUserResponsePage.getContent());
	}
}
