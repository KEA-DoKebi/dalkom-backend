package com.dokebi.dalkom.domain.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.review.dto.ReviewByProductResponse;
import com.dokebi.dalkom.domain.review.dto.ReviewByUserResponse;
import com.dokebi.dalkom.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	Review findByReviewSeq(Long reviewSeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.review.dto.ReviewByProductResponse("
		+ "u.nickname, r.content, r.createdAt, r.modifiedAt, r.rating) " +
		"FROM Review r " +
		"LEFT JOIN OrderDetail od ON r.orderDetail.ordrDetailSeq = od.ordrDetailSeq " +
		"JOIN Product p ON od.product.productSeq = p.productSeq " +
		"JOIN User u ON r.user.userSeq = u.userSeq " +
		"WHERE p.productSeq = :productSeq")
	List<ReviewByProductResponse> getReviewListByProduct(@Param("productSeq") Long productSeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.review.dto.ReviewByUserResponse("
		+ "r.content, r.rating, r.createdAt, r.modifiedAt, p.name, p.imageUrl, o.detail) " +
		"FROM Review r " +
		"LEFT JOIN OrderDetail od ON r.orderDetail.ordrDetailSeq = od.ordrDetailSeq " +
		"JOIN ProductOption o ON od.productOption.prdtOptionSeq = o.prdtOptionSeq " +
		"JOIN Product p ON od.product.productSeq = p.productSeq " +
		"WHERE r.user.userSeq = :userSeq")
	List<ReviewByUserResponse> getReviewListByUser(@Param("userSeq") Long userSeq);

}
