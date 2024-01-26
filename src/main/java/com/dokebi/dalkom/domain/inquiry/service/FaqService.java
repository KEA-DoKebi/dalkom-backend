package com.dokebi.dalkom.domain.inquiry.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.magicnumber.InquiryAnswerState;
import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryCreateRequest;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryListByUserResponse;
import com.dokebi.dalkom.domain.inquiry.entity.Inquiry;
import com.dokebi.dalkom.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FaqService {
	@Transactional
	public void createFaq(Long userSeq, InquiryCreateRequest request) {
		User user = userService.readUserByUserSeq(userSeq);
		Category category = categoryService.readCategoryByCategorySeq(request.getCategorySeq());
		Inquiry inquiry = new Inquiry(category, user, request.getTitle(), request.getContent(), InquiryAnswerState.NO);
		inquiryRepository.save(inquiry);
	}

	public Page<InquiryListByUserResponse> readFaq(Pageable pageable) {
		return inquiryRepository.findInquiryListByUserSeq(userSeq, pageable);
	}
}
