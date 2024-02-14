package com.dokebi.dalkom.domain.inquiry.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.inquiry.dto.InquiryListByUserResponse;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryListResponse;
import com.dokebi.dalkom.domain.inquiry.entity.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>, InquiryRepositoryCustom {
	Optional<Inquiry> findByInquirySeq(Long inquirySeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.inquiry.dto.InquiryListByUserResponse("
		+ "i.inquirySeq, c.name, i.title, i.createdAt, i.answerState) "
		+ "FROM Inquiry i "
		+ "JOIN Category c ON i.category.categorySeq = c.categorySeq "
		+ "WHERE i.user.userSeq = :userSeq "
		+ "ORDER BY i.inquirySeq DESC ")
	Page<InquiryListByUserResponse> findInquiryListByUserSeq(@Param("userSeq") Long userSeq, Pageable pageable);

	@Query("SELECT NEW com.dokebi.dalkom.domain.inquiry.dto.InquiryListResponse("
		+ "i.inquirySeq, i.title, i.createdAt, i.answerState) "
		+ "FROM Inquiry i "
		+ "WHERE i.category.categorySeq = :categorySeq "
		+ "ORDER BY i.inquirySeq DESC ")
	Page<InquiryListResponse> findInquiryListByCategorySeq(@Param("categorySeq") Long categorySeq, Pageable pageable);

	@Query("SELECT NEW com.dokebi.dalkom.domain.inquiry.dto.InquiryListResponse("
		+ "i.inquirySeq, i.title, i.createdAt, i.answerState) "
		+ "FROM Inquiry i "
		+ "WHERE i.category.categorySeq = :categorySeq "
		+ "AND i.title LIKE CONCAT('%', :title, '%')"
		+ "ORDER BY i.inquirySeq DESC ")
	Page<InquiryListResponse> findInquiryListByCategorySearch(@Param("categorySeq") Long categorySeq,
		@Param("title") String title, Pageable pageable);
}
