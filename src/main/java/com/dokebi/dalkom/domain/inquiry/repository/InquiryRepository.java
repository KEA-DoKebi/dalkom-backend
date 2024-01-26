package com.dokebi.dalkom.domain.inquiry.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.inquiry.dto.InquiryListByUserResponse;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryListResponse;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryOneResponse;
import com.dokebi.dalkom.domain.inquiry.entity.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

	Inquiry findByInquirySeq(Long inquirySeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.inquiry.dto.InquiryListByUserResponse("
		+ "i.inquirySeq, c.name, i.title, i.createdAt, i.answerState) "
		+ "FROM Inquiry i "
		+ "JOIN Category c ON i.category.categorySeq = c.categorySeq "
		+ "WHERE i.user.userSeq = :userSeq")
	Page<InquiryListByUserResponse> findInquiryListByUserSeq(@Param("userSeq") Long userSeq, Pageable pageable);

	@Query("SELECT NEW com.dokebi.dalkom.domain.inquiry.dto.InquiryListResponse("
		+ "i.inquirySeq, i.title, i.createdAt, i.answerState) "
		+ "FROM Inquiry i "
		+ "WHERE i.category.categorySeq = :categorySeq")
	Page<InquiryListResponse> findInquiryListByCategorySeq(@Param("categorySeq") Long categorySeq, Pageable pageable);

	@Query("SELECT NEW com.dokebi.dalkom.domain.inquiry.dto.InquiryOneResponse("
		+ "i.title, i.content, i.createdAt, i.answerContent, i.answeredAt, i.admin.nickname) "
		+ "FROM Inquiry i "
		+ "WHERE i.inquirySeq = :inquirySeq")
	InquiryOneResponse findInquiryOne(@Param("inquirySeq") Long inquirySeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.inquiry.dto.InquiryListResponse("
		+ "i.inquirySeq, i.title, i.createdAt, i.answerState) "
		+ "FROM Inquiry i "
		+ "WHERE i.category.categorySeq = :categorySeq "
		+ "AND i.title LIKE CONCAT('%', :title, '%')")
	Page<InquiryListResponse> findInquiryListByCategorySearch(@Param("categorySeq") Long categorySeq,
		@Param("title") String title, Pageable pageable);
}
