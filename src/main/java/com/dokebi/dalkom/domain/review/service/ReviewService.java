package com.dokebi.dalkom.domain.review.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.order.repository.OrderDetailRepository;
import com.dokebi.dalkom.domain.review.dto.ReviewByProductResponse;
import com.dokebi.dalkom.domain.review.dto.ReviewByUserResponse;
import com.dokebi.dalkom.domain.review.dto.ReviewCreateRequest;
import com.dokebi.dalkom.domain.review.dto.ReviewModifyRequest;
import com.dokebi.dalkom.domain.review.entity.Review;
import com.dokebi.dalkom.domain.review.repository.ReviewRepository;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j

public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final OrderDetailRepository orderDetailRepository;

	@Transactional
	public List<ReviewByProductResponse> getReviewListByProduct(Long productSeq) {

		return reviewRepository.getReviewListByProduct(productSeq);
	}

	@Transactional
	public List<ReviewByUserResponse> getReviewListByUser(Long userSeq) {

		return reviewRepository.getReviewListByUser(userSeq);
	}

	@Transactional
	public void createReview(Long userSeq, ReviewCreateRequest request) {

		User user = userRepository.findByUserSeq(userSeq);
		OrderDetail orderDetail = orderDetailRepository.findByOrdrDetailSeq(request.getOrderDetailSeq());
		Review review = new Review(user, orderDetail, request.getContent(), request.getRating());
		reviewRepository.save(review);
	}

	@Transactional
	public void modifyReview(Long reviewSeq, ReviewModifyRequest request) {

		Review review = reviewRepository.findByReviewSeq(reviewSeq);
		review.setContent(request.getContent());
		review.setRating(review.getRating());
		reviewRepository.save(review);
	}

	@Transactional
	public void deleteReview(Long reviewSeq) {

		Review review = reviewRepository.findByReviewSeq(reviewSeq);
		reviewRepository.delete(review);
	}
}
