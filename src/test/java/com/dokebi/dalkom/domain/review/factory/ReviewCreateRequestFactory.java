package com.dokebi.dalkom.domain.review.factory;

import com.dokebi.dalkom.domain.review.dto.ReviewCreateRequest;

public class ReviewCreateRequestFactory {

	public static ReviewCreateRequest createReviewCreateRequest() {
		return new ReviewCreateRequest(
			"content",
			4
		);
	}

	public static ReviewCreateRequest createReviewCreateRequest(
		Long orderDetailSeq, String content, Integer rating) {
		return new ReviewCreateRequest(content, rating);
	}
}
