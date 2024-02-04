package com.dokebi.dalkom.domain.inquiry.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.inquiry.dto.FaqReadListResponse;
import com.dokebi.dalkom.domain.inquiry.dto.FaqReadOneResponse;
import com.dokebi.dalkom.domain.inquiry.entity.Inquiry;

public interface FaqRepository extends JpaRepository<Inquiry, Long> {
	@Query("SELECT NEW com.dokebi.dalkom.domain.inquiry.dto.FaqReadOneResponse("
		+ "i.title,i.createdAt,i.admin.nickname,i.category.name, i.content) "
		+ "FROM Inquiry i "
		+ "WHERE i.inquirySeq = :inquirySeq")
	FaqReadOneResponse findFaqOne(@Param("inquirySeq") Long inquirySeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.inquiry.dto.FaqReadListResponse("
		+ "i.inquirySeq,i.createdAt, i.title) "
		+ "FROM Inquiry i "
		+ "WHERE i.category.name = 'FAQ'")
	Page<FaqReadListResponse> findFaqList(Pageable pageable);

	@Query("SELECT NEW com.dokebi.dalkom.domain.inquiry.dto.FaqReadListResponse("
		+ "i.inquirySeq, i.createdAt, i.title) "
		+ "FROM Inquiry i "
		+ "WHERE i.category.name = 'FAQ' "
		+ "AND i.title LIKE CONCAT('%', :title, '%')")
	Page<FaqReadListResponse> findFaqListSearch(@Param("title") String title, Pageable pageable);

}
