package com.dokebi.dalkom.domain.inquiry.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dokebi.dalkom.domain.inquiry.dto.InquiryListResponse;

public interface InquiryRepositoryCustom {

	Page<InquiryListResponse> findSearchInquiry(Long categorySeq, String title, Pageable pageable);
}
