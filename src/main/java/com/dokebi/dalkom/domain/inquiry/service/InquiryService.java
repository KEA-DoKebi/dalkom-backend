package com.dokebi.dalkom.domain.inquiry.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.magicNumber.InquiryAnswerState;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.service.AdminService;
import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.category.service.CategoryService;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryAnswerRequest;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryCreateRequest;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryListByUserResponse;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryListResponse;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryOneResponse;
import com.dokebi.dalkom.domain.inquiry.entity.Inquiry;
import com.dokebi.dalkom.domain.inquiry.repository.InquiryRepository;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InquiryService {
	private final InquiryRepository inquiryRepository;
	private final CategoryService categoryService;
	private final UserService userService;
	private final AdminService adminService;

	@Transactional
	public void createInquiry(Long userSeq, InquiryCreateRequest request) {
		User user = userService.readUserByUserSeq(userSeq);
		Category category = categoryService.readCategoryByCategorySeq(request.getCategorySeq());
		Inquiry inquiry = new Inquiry(category, user, request.getTitle(), request.getContent(), InquiryAnswerState.NO);
		inquiryRepository.save(inquiry);
	}

	public Page<InquiryListByUserResponse> readInquiryListByUser(Long userSeq, Pageable pageable) {
		return inquiryRepository.findInquiryListByUserSeq(userSeq, pageable);
	}

	public Page<InquiryListResponse> readInquiryListByCategory(Long categorySeq, Pageable pageable) {
		return inquiryRepository.findInquiryListByCategorySeq(categorySeq, pageable);
	}

	public InquiryOneResponse readInquiryOne(Long inquirySeq) {
		return inquiryRepository.findInquiryByInquirySeq(inquirySeq);
	}

	@Transactional
	public void answerInquiry(Long inquirySeq, Long adminSeq, InquiryAnswerRequest request) {
		Inquiry inquiry = inquiryRepository.findByInquirySeq(inquirySeq);
		Admin admin = adminService.readAdminByAdminSeq(adminSeq);
		makeInquiryAnswer(request, inquiry, admin);
	}

	private void makeInquiryAnswer(InquiryAnswerRequest request, Inquiry inquiry, Admin admin) {
		inquiry.setAnswerContent(request.getAnswerContent());
		inquiry.setAdmin(admin);
		inquiry.setAnswerState(InquiryAnswerState.YES);
		inquiry.setAnsweredAt(LocalDateTime.now());
	}
}
