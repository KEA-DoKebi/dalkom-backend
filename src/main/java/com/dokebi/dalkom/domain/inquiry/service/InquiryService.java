package com.dokebi.dalkom.domain.inquiry.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class InquiryService {
	private final InquiryRepository inquiryRepository;
	private final CategoryService categoryService;
	private final UserService userService;
	private final AdminService adminService;

	@Transactional
	public void createInquiry(Long userSeq, InquiryCreateRequest request) {
		User user = userService.readUserByUserSeq(userSeq);
		Category category = categoryService.readCategoryBySeq(request.getCategorySeq());
		Inquiry inquiry = new Inquiry(category, user, request.getTitle(), request.getContent(), "N");
		inquiryRepository.save(inquiry);
	}

	public Page<InquiryListByUserResponse> readInquiryListByUser(Long userSeq, Pageable pageable) {
		return inquiryRepository.findInquiryListByUser(userSeq, pageable);
	}

	public Page<InquiryListResponse> readInquiryListByCategory(Long categorySeq, Pageable pageable) {
		return inquiryRepository.findInquiryListByCategory(categorySeq, pageable);
	}

	public InquiryOneResponse readInquiryOne(Long inquirySeq) {
		return inquiryRepository.findInquiryOne(inquirySeq);
	}

	@Transactional
	public void answerInquiry(Long inquirySeq, Long adminSeq, InquiryAnswerRequest request) {
		Inquiry inquiry = inquiryRepository.findByInquirySeq(inquirySeq);
		Admin admin = adminService.readAdminByAdminSeq(adminSeq);
		inquiry.setAnswerContent(request.getAnswerContent());
		inquiry.setAdmin(admin);
		inquiry.setAnswerState("Y");
		inquiry.setAnsweredAt(LocalDateTime.now());
	}

	public Page<InquiryListResponse> readInquiryListByCategorySearch(Long categorySeq,String title, Pageable pageable) {
		return inquiryRepository.findInquiryListByCategorySearch(categorySeq,title,pageable);
	}
}
