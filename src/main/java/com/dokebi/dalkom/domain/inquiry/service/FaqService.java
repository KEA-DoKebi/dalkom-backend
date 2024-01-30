package com.dokebi.dalkom.domain.inquiry.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.magicnumber.InquiryAnswerState;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.service.AdminService;
import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.category.service.CategoryService;
import com.dokebi.dalkom.domain.inquiry.dto.FaqCreateRequest;
import com.dokebi.dalkom.domain.inquiry.dto.FaqReadListResponse;
import com.dokebi.dalkom.domain.inquiry.dto.FaqReadOneResponse;
import com.dokebi.dalkom.domain.inquiry.dto.FaqUpdateRequest;
import com.dokebi.dalkom.domain.inquiry.entity.Inquiry;
import com.dokebi.dalkom.domain.inquiry.exception.FaqNotFoundException;
import com.dokebi.dalkom.domain.inquiry.repository.FaqRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FaqService {
	private final AdminService adminService;
	private final CategoryService categoryService;
	private final FaqRepository faqRepository;
	private static final long faqCategorySeq = 38L;

	@Transactional
	public void createFaq(Long adminSeq, FaqCreateRequest request) {
		Admin admin = adminService.readAdminByAdminSeq(adminSeq);
		Category category = categoryService.readCategoryByCategorySeq(faqCategorySeq);
		Inquiry inquiry = new Inquiry(category, admin, request.getTitle(), request.getContent(), InquiryAnswerState.NO);
		faqRepository.save(inquiry);

	}

	public FaqReadOneResponse readFaqByInquirySeq(Long faqSeq) {
		return faqRepository.findFaqOne(faqSeq);
	}

	public Page<FaqReadListResponse> readFaqList(Pageable pageable) {
		return faqRepository.findFaqList(pageable);
	}

	@Transactional
	public void updateFaq(Long adminSeq, Long inquirySeq, FaqUpdateRequest request) {
		Admin admin = adminService.readAdminByAdminSeq(adminSeq);
		Optional<Inquiry> faq = faqRepository.findById(inquirySeq);
		if (faq.isPresent()) {
			Inquiry inquiry = faq.get();
			inquiry.setAdmin(admin);
			inquiry.setTitle(request.getTitle());
			inquiry.setContent(request.getContent());
		} else {
			throw new FaqNotFoundException();
		}

	}

}
