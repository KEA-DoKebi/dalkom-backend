package com.dokebi.dalkom.domain.review.factory;

import java.time.LocalDateTime;

import com.dokebi.dalkom.domain.review.dto.ReviewByProductResponse;

public class ReviewByProductResponseFactory {

	public static ReviewByProductResponse createReviewByProductResponse() {
		return new ReviewByProductResponse(
			"nickname",
			"content",
			LocalDateTime.of(2024, 1, 15, 0, 22, 32),
			LocalDateTime.of(2024, 1, 15, 0, 22, 32),
			5
		);
	}

	public static ReviewByProductResponse createReviewByProductResponse(
		String nickname, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, Integer rating) {
		return new ReviewByProductResponse(nickname, content, createdAt, modifiedAt, rating);
	}
}
