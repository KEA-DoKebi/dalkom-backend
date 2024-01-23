package com.dokebi.dalkom.domain.review.factory;

import com.dokebi.dalkom.domain.review.dto.ReviewUpdateRequest;

public class ReviewUpdateRequestFactory {

	public static ReviewUpdateRequest createReviewUpdateRequest() {
		return new ReviewUpdateRequest(
			"굿굿",
			5
		);
	}

	public static ReviewUpdateRequest createReviewUpdateRequest(
		String content, Integer rating) {
		return new ReviewUpdateRequest(content, rating);
	}
}
