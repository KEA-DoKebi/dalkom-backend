package com.dokebi.dalkom.domain.inquiry.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.email.dto.EmailMessage;
import com.dokebi.dalkom.common.email.service.EmailService;
import com.dokebi.dalkom.common.magicnumber.InquiryAnswerState;
import com.dokebi.dalkom.common.magicnumber.InquiryCategory;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.exception.AdminNotFoundException;
import com.dokebi.dalkom.domain.admin.repository.AdminRepository;
import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.category.exception.CategoryNotFoundException;
import com.dokebi.dalkom.domain.category.repository.CategoryRepository;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryAnswerRequest;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryCreateRequest;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryListByUserResponse;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryListResponse;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryOneResponse;
import com.dokebi.dalkom.domain.inquiry.entity.Inquiry;
import com.dokebi.dalkom.domain.inquiry.exception.InquiryNotFoundException;
import com.dokebi.dalkom.domain.inquiry.repository.InquiryRepository;
import com.dokebi.dalkom.domain.jira.dto.JiraInquiryAnswerRequest;
import com.dokebi.dalkom.domain.jira.dto.JiraInquiryRequest;
import com.dokebi.dalkom.domain.jira.service.JiraService;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.exception.UserNotFoundException;
import com.dokebi.dalkom.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class InquiryService {
	private final InquiryRepository inquiryRepository;
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final AdminRepository adminRepository;
	private final JiraService jiraService;
	private final EmailService emailService;

	// INQUIRY-001 (문의 등록)
	@Transactional
	public void createInquiry(Long userSeq, InquiryCreateRequest request) {
		User user = userRepository.findByUserSeq(userSeq).orElseThrow(UserNotFoundException::new);
		Category category = categoryRepository.findCategoryByCategorySeq(request.getCategorySeq())
			.orElseThrow(CategoryNotFoundException::new);
		Inquiry inquiry = new Inquiry(category, user, request.getTitle(), request.getContent(),
			InquiryAnswerState.NO.getState());
		inquiry = inquiryRepository.save(inquiry);

		// 문의 내용 Jira로 보내기
		try {
			System.out.println(InquiryCategory.getNameByState(request.getCategorySeq()));
			JiraInquiryRequest jiraInquiryRequest = new JiraInquiryRequest(request.getTitle(), request.getContent(),
				user.getNickname(), inquiry.getInquirySeq(), InquiryCategory.getNameByState(request.getCategorySeq()));
			String jiraToken = jiraService.createIssueInquiry(jiraInquiryRequest);
			inquiry.setJiraToken(jiraToken);
			System.out.println(inquiry.getJiraToken());
		} catch (Exception e) {
			// Jira 연동 실패 로그 기록
			log.error("Jira 이슈 생성 중 오류 발생", e);
		}
	}

	// INQUIRY-002 (유저별 문의 조회)
	public Page<InquiryListByUserResponse> readInquiryListByUser(Long userSeq, Pageable pageable) {
		Page<InquiryListByUserResponse> page = inquiryRepository.findInquiryListByUserSeq(userSeq, pageable);

		if (page.isEmpty()) {
			throw new InquiryNotFoundException();
		}

		return page;
	}

	// INQUIRY-003 (문의 카테고리 별 문의 조회)
	public Page<InquiryListResponse> readInquiryListByCategory(Long categorySeq, Pageable pageable) {
		Page<InquiryListResponse> page = inquiryRepository.findInquiryListByCategorySeq(categorySeq, pageable);

		if (page.isEmpty()) {
			throw new InquiryNotFoundException();
		}

		return page;
	}

	// INQUIRY-004 미구현 상태

	// INQUIRY-005 (특정 문의 조회)
	public InquiryOneResponse readInquiryOne(Long inquirySeq) {
		Inquiry inquiry = inquiryRepository.findByInquirySeq(inquirySeq).orElseThrow(InquiryNotFoundException::new);
		InquiryOneResponse inquiryOneResponse;

		if (inquiry.getAnswerState().equals(InquiryAnswerState.YES.getState())) {
			inquiryOneResponse = new InquiryOneResponse(inquiry.getTitle(), inquiry.getContent(),
				inquiry.getCreatedAt(), inquiry.getAnswerContent(), inquiry.getAnsweredAt(),
				inquiry.getAdmin().getNickname());
		} else {
			inquiryOneResponse = new InquiryOneResponse(inquiry.getTitle(), inquiry.getContent(),
				inquiry.getCreatedAt());
		}

		return inquiryOneResponse;
	}

	// INQUIRY-006 (문의 답변)
	@Transactional
	public void answerInquiry(Long inquirySeq, Long adminSeq, InquiryAnswerRequest request) throws Exception {
		Inquiry inquiry = inquiryRepository.findByInquirySeq(inquirySeq).orElseThrow(InquiryNotFoundException::new);
		Admin admin = adminRepository.findByAdminSeq(adminSeq).orElseThrow(AdminNotFoundException::new);

		// 이 부분이 따로 함수로 구현되어 있으면 테스트 코드 작성하기가 애매해서 그냥 합쳤습니다.
		inquiry.setAnswerContent(request.getAnswerContent());
		inquiry.setAdmin(admin);
		inquiry.setAnswerState(InquiryAnswerState.YES.getState());
		inquiry.setAnsweredAt(LocalDateTime.now());

		// 메일 전송
		EmailMessage emailMessage = new EmailMessage(inquiry.getUser().getEmail(), "문의에 답변이 등록되었습니다.");
		emailService.sendMailInquiry(emailMessage, inquiry.getCreatedAt());

		// 답변 내용 Jira로 보내기
		try {
			if (inquiry.getJiraToken() != null && admin.getNickname() != null && request.getAnswerContent() != null) {
				JiraInquiryAnswerRequest jiraAnswerRequest = new JiraInquiryAnswerRequest(request.getAnswerContent(),
					admin.getNickname(), inquiry.getJiraToken());
				jiraService.updateIssueInquiry(jiraAnswerRequest);
			}
		} catch (Exception e) {
			// Jira 연동 실패 로그 기록
			log.error("Jira 이슈 수정 중 오류 발생", e);
		}
	}

	// INQUIRY-007 (문의 카테고리 별 문의 검색)
	public Page<InquiryListResponse> readInquiryListByCategorySearch(Long categorySeq, String title,
		Pageable pageable) {
		Page<InquiryListResponse> page = inquiryRepository.findInquiryListByCategorySearch(
			categorySeq, title, pageable);
		if (page.isEmpty()) {
			throw new InquiryNotFoundException();
		}

		return page;
	}

	// INQUIRY-008 (문의 삭제)
	@Transactional
	public void deleteInquiry(Long inquirySeq) {
		Inquiry inquiry = inquiryRepository.findByInquirySeq(inquirySeq).orElseThrow(InquiryNotFoundException::new);
		inquiryRepository.delete(inquiry);
	}
}
