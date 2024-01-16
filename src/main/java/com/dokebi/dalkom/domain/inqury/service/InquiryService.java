package com.dokebi.dalkom.domain.inqury.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.inqury.dto.InquiryListResponse;
import com.dokebi.dalkom.domain.inqury.dto.InquiryOneResponse;
import com.dokebi.dalkom.domain.inqury.repository.InquiryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InquiryService {

	private final InquiryRepository inquiryRepository;

	public List<InquiryListResponse> getInquiryListByUser(Long userSeq) {

		return inquiryRepository.getInquiryListByUser(userSeq);
	}

	public List<InquiryListResponse> getInquiryListByCategory(Long categorySeq) {

		return inquiryRepository.getInquiryListByCategory(categorySeq);
	}

	public InquiryOneResponse getInquiryOne(Long inquirySeq) {

		return inquiryRepository.getInquiryOne(inquirySeq);
	}
}
