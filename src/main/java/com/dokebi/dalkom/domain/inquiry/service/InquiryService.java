package com.dokebi.dalkom.domain.inquiry.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.magicnumber.InquiryAnswerState;
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
import com.dokebi.dalkom.domain.inquiry.exception.InquiryNotFoundException;
import com.dokebi.dalkom.domain.inquiry.repository.InquiryRepository;
import com.dokebi.dalkom.domain.jira.dto.JiraInquiryRequest;
import com.dokebi.dalkom.domain.jira.service.JiraService;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class InquiryService {
	private final InquiryRepository inquiryRepository;
	private final CategoryService categoryService;
	private final UserService userService;
	private final AdminService adminService;
	private final JiraService jiraService;

	@Transactional
	public void createInquiry(Long userSeq, InquiryCreateRequest request) {
		User user = userService.readUserByUserSeq(userSeq);
		Category category = categoryService.readCategoryByCategorySeq(request.getCategorySeq());
		Inquiry inquiry = new Inquiry(category, user, request.getTitle(), request.getContent(),
			InquiryAnswerState.NO.getState());
		inquiry = inquiryRepository.save(inquiry);
		// 문의 내용 Jira로 보내기
		try {
			JiraInquiryRequest jiraInquiryRequest = new JiraInquiryRequest(request.getTitle(), request.getContent(),
				user.getNickname(), inquiry.getInquirySeq());
			jiraService.createIssueInquiry(jiraInquiryRequest);
		} catch (Exception e) {
			// Jira 연동 실패 로그 기록
			log.error("Jira 이슈 생성 중 오류 발생", e);
		}
	}

	public Page<InquiryListByUserResponse> readInquiryListByUser(Long userSeq, Pageable pageable) {
		Page<InquiryListByUserResponse> page = inquiryRepository.findInquiryListByUserSeq(userSeq, pageable);

		if (page.isEmpty()) {
			throw new InquiryNotFoundException();
		}

		return page;
	}

	public Page<InquiryListResponse> readInquiryListByCategory(Long categorySeq, Pageable pageable) {
		Page<InquiryListResponse> page = inquiryRepository.findInquiryListByCategorySeq(categorySeq, pageable);

		if (page.isEmpty()) {
			throw new InquiryNotFoundException();
		}

		return page;
	}

	public InquiryOneResponse readInquiryOne(Long inquirySeq) {
		Inquiry inquiry = inquiryRepository.findByInquirySeq(inquirySeq).orElseThrow(InquiryNotFoundException::new);
		InquiryOneResponse inquiryOneResponse;

		if (inquiry.getAnswerState().equals(InquiryAnswerState.YES)) {
			inquiryOneResponse = new InquiryOneResponse(inquiry.getTitle(), inquiry.getContent(),
				inquiry.getCreatedAt(), inquiry.getAnswerContent(), inquiry.getAnsweredAt(),
				inquiry.getAdmin().getNickname());
		} else {
			inquiryOneResponse = new InquiryOneResponse(inquiry.getTitle(), inquiry.getContent(),
				inquiry.getCreatedAt());
		}

		return inquiryOneResponse;
	}

	@Transactional
	public void answerInquiry(Long inquirySeq, Long adminSeq, InquiryAnswerRequest request) {
		Inquiry inquiry = inquiryRepository.findByInquirySeq(inquirySeq).orElseThrow(InquiryNotFoundException::new);
		Admin admin = adminService.readAdminByAdminSeq(adminSeq);
		makeInquiryAnswer(request, inquiry, admin);
	}

	private void makeInquiryAnswer(InquiryAnswerRequest request, Inquiry inquiry, Admin admin) {
		inquiry.setAnswerContent(request.getAnswerContent());
		inquiry.setAdmin(admin);
		inquiry.setAnswerState(InquiryAnswerState.YES.getState());
		inquiry.setAnsweredAt(LocalDateTime.now());
	}

	public Page<InquiryListResponse> readInquiryListByCategorySearch(
		Long categorySeq, String title, Pageable pageable) {
		Page<InquiryListResponse> page = inquiryRepository.findInquiryListByCategorySearch(
			categorySeq, title, pageable);

		if (page.isEmpty()) {
			throw new InquiryNotFoundException();
		}

		return page;
	}
}
