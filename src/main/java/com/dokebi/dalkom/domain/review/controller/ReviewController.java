package com.dokebi.dalkom.domain.review.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.review.dto.ReviewCreateRequest;
import com.dokebi.dalkom.domain.review.dto.ReviewUpdateRequest;
import com.dokebi.dalkom.domain.review.service.ReviewService;
import com.dokebi.dalkom.domain.user.config.LoginUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	// REVIEW-001 (상품별 리뷰 조회) - 입력받은 productSeq의 리뷰 목록 반환
	@GetMapping("/api/review/product/{productSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readReviewByProduct(@PathVariable Long productSeq, Pageable pageable) {

		return Response.success(reviewService.readReviewListByProduct(productSeq, pageable));
	}

	// REVIEW-002 (사용자별 리뷰 목록 조회) - 입력받은 userSeq의 리뷰 목록 반환
	@GetMapping("/api/review/user")
	@ResponseStatus(HttpStatus.OK)
	public Response readReviewByUser(@LoginUser Long userSeq, Pageable pageable) {
		return Response.success(reviewService.readReviewListByUser(userSeq, pageable));
	}

	// REVIEW-003 (주문 상품에 대한 리뷰 작성)
	@PostMapping("/api/review/{orderDetailSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response createReview(@LoginUser Long userSeq, @PathVariable Long orderDetailSeq,
		@Valid @RequestBody ReviewCreateRequest request) {

		reviewService.createReview(userSeq, orderDetailSeq, request);
		return Response.success();
	}

	// REVIEW-004 (리뷰 수정)
	@PutMapping("/api/review/{reviewSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response updateReview(@PathVariable Long reviewSeq,
		@Valid @RequestBody ReviewUpdateRequest request) {

		reviewService.updateReview(reviewSeq, request);
		return Response.success();
	}

	//REVIEW-005 (리뷰 삭제)
	@DeleteMapping("/api/review/{reviewSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response deleteReview(@PathVariable Long reviewSeq) {
		reviewService.deleteReview(reviewSeq);
		return Response.success();
	}

	// REVIEW-006 (단일 리뷰 조회)
	@GetMapping("/api/review/{reviewSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readReview(@PathVariable Long reviewSeq) {
		return Response.success(reviewService.readReviewByReviewSeq(reviewSeq));
	}
}
