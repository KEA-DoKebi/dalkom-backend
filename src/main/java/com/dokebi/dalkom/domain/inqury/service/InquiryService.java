package com.dokebi.dalkom.domain.inqury.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.category.repository.CategoryRepository;
import com.dokebi.dalkom.domain.inqury.dto.InquiryAnswerRequest;
import com.dokebi.dalkom.domain.inqury.dto.InquiryCreateRequest;
import com.dokebi.dalkom.domain.inqury.dto.InquiryListResponse;
import com.dokebi.dalkom.domain.inqury.dto.InquiryOneResponse;
import com.dokebi.dalkom.domain.inqury.entity.Inquiry;
import com.dokebi.dalkom.domain.inqury.repository.InquiryRepository;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InquiryService {

	private final InquiryRepository inquiryRepository;
	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;

	@Transactional
	public List<InquiryListResponse> getInquiryListByUser(Long userSeq) {

		return inquiryRepository.getInquiryListByUser(userSeq);
	}

	@Transactional
	public List<InquiryListResponse> getInquiryListByCategory(Long categorySeq) {

		return inquiryRepository.getInquiryListByCategory(categorySeq);
	}

	@Transactional
	public InquiryOneResponse getInquiryOne(Long inquirySeq) {

		return inquiryRepository.getInquiryOne(inquirySeq);
	}

	@Transactional
	public void createInquiry(Long userSeq, InquiryCreateRequest request) {

		Category category = categoryRepository.findByCategorySeq(request.getCategorySeq());
		User user = userRepository.findByUserSeq(userSeq);
		String answerState = "N";
		Inquiry inquiry = new Inquiry(category, user, request.getTitle(), request.getContent(), answerState);
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
