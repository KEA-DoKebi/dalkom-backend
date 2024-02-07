package com.dokebi.dalkom.domain.inquiry.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.magicnumber.InquiryAnswerState;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.exception.AdminNotFoundException;
import com.dokebi.dalkom.domain.admin.repository.AdminRepository;
import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.category.exception.CategoryNotFoundException;
import com.dokebi.dalkom.domain.category.repository.CategoryRepository;
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
	private static final Long FAQ_CATEGORY_SEQ = 38L;
	private final FaqRepository faqRepository;
	private final AdminRepository adminRepository;
	private final CategoryRepository categoryRepository;

	// FAQ-001 (FAQ 상세 조회)
	public FaqReadOneResponse readFaqByInquirySeq(Long faqSeq) {
		return faqRepository.findFaqOne(faqSeq).orElseThrow(FaqNotFoundException::new);
	}

	// FAQ-002 (FAQ 전체 조회)
	public Page<FaqReadListResponse> readFaqList(Pageable pageable) {
		return faqRepository.findFaqList(pageable);
	}

	// FAQ-003 (FAQ 등록)
	@Transactional
	public void createFaq(Long adminSeq, FaqCreateRequest request) {
		Admin admin = adminRepository.findByAdminSeq(adminSeq).orElseThrow(AdminNotFoundException::new);
		Category category = categoryRepository.findCategoryByCategorySeq(FAQ_CATEGORY_SEQ)
			.orElseThrow(CategoryNotFoundException::new);

		Inquiry inquiry = new Inquiry(category, admin, request.getTitle(), request.getContent(),
			InquiryAnswerState.NO.getState());
		faqRepository.save(inquiry);
	}

	// FAQ-004 (FAQ 수정)
	@Transactional
	public void updateFaq(Long adminSeq, Long inquirySeq, FaqUpdateRequest request) {
		Admin admin = adminRepository.findByAdminSeq(adminSeq).orElseThrow(AdminNotFoundException::new);
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

	// FAQ-005 (FAQ 삭제)
	@Transactional
	public void deleteFaq(Long inquirySeq) {
		Inquiry faq = faqRepository.findById(inquirySeq).orElseThrow(FaqNotFoundException::new);
		faqRepository.delete(faq);
	}

	// FAQ-006 (FAQ 검색)
	public Page<FaqReadListResponse> readFaqListBySearch(String title, Pageable pageable) {
		return faqRepository.findFaqListSearch(title, pageable);
	}
}
