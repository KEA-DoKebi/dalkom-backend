package com.dokebi.dalkom.domain.inqury.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.category.service.CategoryService;
import com.dokebi.dalkom.domain.inqury.dto.InquiryAnswerRequest;
import com.dokebi.dalkom.domain.inqury.dto.InquiryCreateRequest;
import com.dokebi.dalkom.domain.inqury.dto.InquiryListResponse;
import com.dokebi.dalkom.domain.inqury.dto.InquiryOneResponse;
import com.dokebi.dalkom.domain.inqury.entity.Inquiry;
import com.dokebi.dalkom.domain.inqury.repository.InquiryRepository;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InquiryService {

	private final InquiryRepository inquiryRepository;
	private final CategoryService categoryService;
	private final UserService userService;

	@Transactional
	public List<InquiryListResponse> readInquiryListByUser(Long userSeq) {

		return inquiryRepository.findInquiryListByUser(userSeq);
	}

	@Transactional
	public List<InquiryListResponse> readInquiryListByCategory(Long categorySeq) {

		return inquiryRepository.findInquiryListByCategory(categorySeq);
	}

	@Transactional
	public InquiryOneResponse readInquiryOne(Long inquirySeq) {

		return inquiryRepository.findInquiryOne(inquirySeq);
	}

	@Transactional
	public void createInquiry(Long userSeq, InquiryCreateRequest request) {

		User user = userService.readUserByUserSeq(userSeq);
		Category category = categoryService.readCategoryByCategorySeq(request.getCategorySeq());
		Inquiry inquiry = new Inquiry(category, user, request.getTitle(), request.getContent(), "N");
		inquiryRepository.save(inquiry);
	}

	@Transactional
	public void answerInquiry(Long inquirySeq, InquiryAnswerRequest request) {

		Inquiry inquiry = inquiryRepository.findByInquirySeq(inquirySeq);
		inquiry.setAnswerContent(request.getAnswerContent());
		inquiry.setAnswerState(request.getAnswerState());
		inquiry.setAnsweredAt(LocalDateTime.now());
		inquiryRepository.save(inquiry);
	}
}
