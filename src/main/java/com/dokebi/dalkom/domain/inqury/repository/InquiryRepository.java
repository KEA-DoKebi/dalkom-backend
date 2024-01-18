package com.dokebi.dalkom.domain.inqury.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.inqury.dto.InquiryListResponse;
import com.dokebi.dalkom.domain.inqury.dto.InquiryOneResponse;
import com.dokebi.dalkom.domain.inqury.entity.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

	Inquiry findByInquirySeq(Long inquirySeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.inqury.dto.InquiryListResponse("
		+ "i.title, i.content, i.createdAt, i.answerState, i.answeredAt, i.answerContent) " +
		"FROM Inquiry i " +
		"WHERE i.user.userSeq = :userSeq")
	List<InquiryListResponse> getInquiryListByUser(@Param("userSeq") Long userSeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.inqury.dto.InquiryListResponse("
		+ "i.title, i.content, i.createdAt, i.answerState, i.answeredAt, i.answerContent) " +
		"FROM Inquiry i " +
		"WHERE i.category.categorySeq = :categorySeq")
	List<InquiryListResponse> getInquiryListByCategory(@Param("categorySeq") Long categorySeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.inqury.dto.InquiryOneResponse("
		+ "i.title, i.content, i.createdAt, i.answerContent, i.answeredAt, i.admin.nickname) " +
		"FROM Inquiry i " +
		"WHERE i.inquirySeq = :inquirySeq")
	InquiryOneResponse getInquiryOne(@Param("inquirySeq") Long inquirySeq);

}
