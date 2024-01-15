package com.dokebi.dalkom.domain.review.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.review.dto.ReviewByProductResponse;
import com.dokebi.dalkom.domain.review.dto.ReviewByUserResponse;
import com.dokebi.dalkom.domain.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j

public class ReviewService {

	private final ReviewRepository reviewRepository;

	@Transactional
	public List<ReviewByProductResponse> getReviewListByProduct(Long productSeq) {
		return reviewRepository.getReviewListByProduct(productSeq);
	}

	@Transactional
	public List<ReviewByUserResponse> getReviewListByUser(Long userSeq) {
		return reviewRepository.getReviewListByUser(userSeq);
	}
}
