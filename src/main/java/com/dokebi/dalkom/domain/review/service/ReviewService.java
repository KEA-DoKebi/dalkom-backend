package com.dokebi.dalkom.domain.review.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.order.service.OrderDetailService;
import com.dokebi.dalkom.domain.review.dto.ReviewByProductResponse;
import com.dokebi.dalkom.domain.review.dto.ReviewByUserResponse;
import com.dokebi.dalkom.domain.review.dto.ReviewCreateRequest;
import com.dokebi.dalkom.domain.review.dto.ReviewUpdateRequest;
import com.dokebi.dalkom.domain.review.entity.Review;
import com.dokebi.dalkom.domain.review.exception.ReviewNotFoundException;
import com.dokebi.dalkom.domain.review.repository.ReviewRepository;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final UserService userService;
	private final OrderDetailService orderDetailService;

	@Transactional
	public Page<ReviewByProductResponse> readReviewListByProduct(Long productSeq, Pageable pageable) {

		return reviewRepository.findReviewListByProduct(productSeq, pageable);
	}

	@Transactional
	public Page<ReviewByUserResponse> readReviewListByUser(Long userSeq, Pageable pageable) {

		return reviewRepository.findReviewListByUser(userSeq, pageable);
	}

	@Transactional
	public void createReview(Long userSeq, ReviewCreateRequest request) {

		User user = userService.readUserByUserSeq(userSeq);
		OrderDetail orderDetail = orderDetailService.readOrderDetailByOrderDetailSeq(request.getOrderDetailSeq());
		Review review = new Review(user, orderDetail, request.getContent(), request.getRating());
		reviewRepository.save(review);
	}

	@Transactional
	public void updateReview(Long reviewSeq, ReviewUpdateRequest request) {

		Review review = reviewRepository.findByReviewSeq(reviewSeq);
		review.setContent(request.getContent());
		review.setRating(request.getRating());
		reviewRepository.save(review);
	}

	@Transactional
	public void deleteReview(Long reviewSeq) {

		Optional<Review> review = reviewRepository.findById(reviewSeq);
		if (review.isPresent()) {
			reviewRepository.deleteById(reviewSeq);
		} else {
			throw new ReviewNotFoundException();
		}
	}
}
