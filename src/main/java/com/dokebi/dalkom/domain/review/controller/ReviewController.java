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

	// 5번 api (사용자별 리뷰 조회) - 입력받은 productSeq의 리뷰 리스트 반환
	@GetMapping("api/reviews/product/{productSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response getReviewsByProduct(@PathVariable Long productSeq) {
		return Response.success(reviewService.getReviewListByProduct(productSeq));
	}
}
