package com.dokebi.dalkom.domain.review.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	// REVIEWS-001 (상품별 리뷰 조회) - 입력받은 productSeq의 리뷰 목록 반환
	@GetMapping("api/reviews/product/{productSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readReviewByProduct(@PathVariable Long productSeq) {
		return Response.success(reviewService.getReviewListByProduct(productSeq));
	}

	// REVIEWS-002 (사용자별 리뷰 조회) - 입력받은 userSeq의 리뷰 목록 반환
	@GetMapping("api/reviews/users/{userSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readReviewByUser(@PathVariable Long userSeq) {
		return Response.success(reviewService.getReviewListByUser(userSeq));
	}
}
