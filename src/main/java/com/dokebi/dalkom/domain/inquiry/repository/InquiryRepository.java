package com.dokebi.dalkom.domain.inquiry.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.inquiry.dto.InquiryListResponse;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryOneResponse;
import com.dokebi.dalkom.domain.inquiry.entity.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

	Inquiry findByInquirySeq(Long inquirySeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.inquiry.dto.InquiryListResponse("
		+ "i.title, i.content, i.createdAt, i.answerState, i.answeredAt, i.answerContent) " +
		"FROM Inquiry i " +
		"WHERE i.user.userSeq = :userSeq")
	List<InquiryListResponse> findInquiryListByUser(@Param("userSeq") Long userSeq, Pageable pageable);

	@Query("SELECT NEW com.dokebi.dalkom.domain.inquiry.dto.InquiryListResponse("
		+ "i.title, i.content, i.createdAt, i.answerState, i.answeredAt, i.answerContent) " +
		"FROM Inquiry i " +
		"WHERE i.category.categorySeq = :categorySeq")
	Page<InquiryListResponse> findInquiryListByCategory(@Param("categorySeq") Long categorySeq, Pageable pageable);

	@Query("SELECT NEW com.dokebi.dalkom.domain.inquiry.dto.InquiryOneResponse("
		+ "i.title, i.content, i.createdAt, i.answerContent, i.answeredAt, i.admin.nickname) " +
		"FROM Inquiry i " +
		"WHERE i.inquirySeq = :inquirySeq")
	InquiryOneResponse findInquiryOne(@Param("inquirySeq") Long inquirySeq);

}
