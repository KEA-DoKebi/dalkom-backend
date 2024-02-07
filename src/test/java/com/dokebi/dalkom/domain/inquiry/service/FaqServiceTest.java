package com.dokebi.dalkom.domain.inquiry.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.dokebi.dalkom.common.magicnumber.InquiryAnswerState;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.repository.AdminRepository;
import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.category.repository.CategoryRepository;
import com.dokebi.dalkom.domain.inquiry.dto.FaqCreateRequest;
import com.dokebi.dalkom.domain.inquiry.dto.FaqReadListResponse;
import com.dokebi.dalkom.domain.inquiry.dto.FaqReadOneResponse;
import com.dokebi.dalkom.domain.inquiry.dto.FaqUpdateRequest;
import com.dokebi.dalkom.domain.inquiry.entity.Inquiry;
import com.dokebi.dalkom.domain.inquiry.factory.FaqCreateRequestFactory;
import com.dokebi.dalkom.domain.inquiry.factory.FaqUpdateRequestFactory;
import com.dokebi.dalkom.domain.inquiry.repository.FaqRepository;
import com.dokebi.dalkom.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
public class FaqServiceTest {
	private static final Long FAQ_CATEGORY_SEQ = 38L;
	@InjectMocks
	private FaqService faqService;
	@Mock
	private FaqRepository faqRepository;
	@Mock
	private AdminRepository adminRepository;
	@Mock
	private CategoryRepository categoryRepository;

	@Test
	@DisplayName("FAQ-001 (FAQ 상세 조회)")
	void readFaqByInquirySeq() {
		// Given
		Long faqSeq = 1L;
		FaqReadOneResponse response = new FaqReadOneResponse(
			"Title", LocalDateTime.now(), "Name", "CategoryName", "Content");

		when(faqRepository.findFaqOne(faqSeq)).thenReturn(Optional.of(response));

		// When
		faqService.readFaqByInquirySeq(faqSeq);

		// Then
		verify(faqRepository).findFaqOne(faqSeq);
	}

	@Test
	@DisplayName("FAQ-002 (FAQ 전체 조회)")
	void readFaqListTest() {
		// Given
		Pageable pageable = PageRequest.of(0, 10);

		FaqReadListResponse response1 = new FaqReadListResponse(1L, LocalDateTime.now(), "Title1");
		FaqReadListResponse response2 = new FaqReadListResponse(2L, LocalDateTime.now(), "Title2");

		List<FaqReadListResponse> faqList = List.of(response1, response2);

		Page<FaqReadListResponse> faqPage = new PageImpl<>(faqList);

		when(faqRepository.findFaqList(pageable)).thenReturn(faqPage);

		// When
		faqService.readFaqList(pageable);

		// Then
		verify(faqRepository).findFaqList(pageable);
	}

	@Test
	@DisplayName("FAQ-003 (FAQ 등록)")
	void createFaqTest() {
		// Given
		Long adminSeq = 1L;
		FaqCreateRequest request = FaqCreateRequestFactory.createFaqCreateRequest();

		Admin admin = new Admin("administrator", "123456a!", "admin", "사장", "IT");
		Category category = new Category("홍길동", 2L, "이미지주소");

		when(adminRepository.findByAdminSeq(adminSeq)).thenReturn(Optional.of(admin));
		when(categoryRepository.findCategoryByCategorySeq(FAQ_CATEGORY_SEQ)).thenReturn(Optional.of(category));

		// When
		faqService.createFaq(adminSeq, request);

		// Then
		verify(faqRepository).save(any());
	}

	@Test
	@DisplayName("FAQ-004 (FAQ 수정)")
	void updateFaqTest() {
		// Given
		Long adminSeq = 1L;
		Long inquirySeq = 1L;
		FaqUpdateRequest request = FaqUpdateRequestFactory.createFaqUpdateRequest();

		Admin admin = new Admin("administrator", "123456a!", "admin", "사장", "IT");
		admin.setAdminSeq(adminSeq);

		LocalDate joinedAt = LocalDate.now();
		User user = new User("empId", "password", "name", "email@email.com",
			"address", joinedAt, "nickname", 1200000
		);
		Category category = new Category("홍길동", 2L, "이미지주소");
		Inquiry faq = new Inquiry(category, user, request.getTitle(), request.getContent(),
			InquiryAnswerState.NO.getState());
		faq.setInquirySeq(inquirySeq);

		when(adminRepository.findByAdminSeq(adminSeq)).thenReturn(Optional.of(admin));
		when(faqRepository.findById(inquirySeq)).thenReturn(Optional.of(faq));

		// When
		faqService.updateFaq(adminSeq, inquirySeq, request);

		// Then
		assertEquals(admin, faq.getAdmin());
		assertEquals(request.getTitle(), faq.getTitle());
		assertEquals(request.getContent(), faq.getContent());

		verify(adminRepository).findByAdminSeq(adminSeq);
		verify(faqRepository).findById(inquirySeq);
	}

	@Test
	@DisplayName("FAQ-005 (FAQ 삭제)")
	void deleteFaqTest() {
		// Given
		Long inquirySeq = 1L;

		LocalDate joinedAt = LocalDate.now();
		User user = new User("empId", "password", "name", "email@email.com",
			"address", joinedAt, "nickname", 1200000
		);
		Category category = new Category("홍길동", 2L, "이미지주소");
		Inquiry faq = new Inquiry(category, user, "Title", "Content", InquiryAnswerState.NO.getState());
		faq.setInquirySeq(inquirySeq);

		when(faqRepository.findById(inquirySeq)).thenReturn(Optional.of(faq));

		// When
		faqService.deleteFaq(inquirySeq);

		// Then
		verify(faqRepository).delete(faq);
	}

	@Test
	@DisplayName("FAQ-006 (FAQ 검색)")
	void readFaqListBySearchTest() {
		// Given
		String title = "검색어";
		Pageable pageable = PageRequest.of(0, 10);

		FaqReadListResponse response1 = new FaqReadListResponse(1L, LocalDateTime.now(), "Title1");
		FaqReadListResponse response2 = new FaqReadListResponse(2L, LocalDateTime.now(), "Title2");

		List<FaqReadListResponse> faqList = List.of(response1, response2);

		Page<FaqReadListResponse> faqPage = new PageImpl<>(faqList);

		when(faqRepository.findFaqListSearch(title, pageable)).thenReturn(faqPage);

		// When
		faqService.readFaqListBySearch(title, pageable);

		// Then
		verify(faqRepository).findFaqListSearch(title, pageable);
	}
}
