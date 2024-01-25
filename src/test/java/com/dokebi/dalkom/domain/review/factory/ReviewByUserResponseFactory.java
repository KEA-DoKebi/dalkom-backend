package com.dokebi.dalkom.domain.review.factory;

import java.time.LocalDateTime;

import com.dokebi.dalkom.domain.review.dto.ReviewByUserResponse;

public class ReviewByUserResponseFactory {

	public static ReviewByUserResponse createReviewByUserResponse() {
		return new ReviewByUserResponse(
			1L,
			"content",
			5,
			LocalDateTime.of(2024, 1, 15, 0, 22, 32),
			LocalDateTime.of(2024, 1, 15, 0, 22, 32),
			"name",
			"imageUrl",
			"detail"
		);
	}

	public static ReviewByUserResponse createReviewByUserResponse(
		Long reviewSeq, String content, Integer rating, LocalDateTime createdAt, LocalDateTime modifiedAt, String name,
		String imageUrl,
		String detail) {
		return new ReviewByUserResponse(reviewSeq, content, rating, createdAt, modifiedAt, name, imageUrl, detail);
	}
}
