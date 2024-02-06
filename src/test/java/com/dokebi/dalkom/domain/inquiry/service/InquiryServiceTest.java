package com.dokebi.dalkom.domain.inquiry.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
import com.dokebi.dalkom.domain.inquiry.dto.InquiryAnswerRequest;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryCreateRequest;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryListByUserResponse;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryListResponse;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryOneResponse;
import com.dokebi.dalkom.domain.inquiry.entity.Inquiry;
import com.dokebi.dalkom.domain.inquiry.factory.InquiryAnswerRequestFactory;
import com.dokebi.dalkom.domain.inquiry.factory.InquiryCreateRequestFactory;
import com.dokebi.dalkom.domain.inquiry.factory.InquiryListByUserResponseFactory;
import com.dokebi.dalkom.domain.inquiry.factory.InquiryListResponseFactory;
import com.dokebi.dalkom.domain.inquiry.repository.InquiryRepository;
import com.dokebi.dalkom.domain.jira.dto.JiraInquiryRequest;
import com.dokebi.dalkom.domain.jira.service.JiraService;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class InquiryServiceTest {
	@InjectMocks
	private InquiryService inquiryService;
	@Mock
	private InquiryRepository inquiryRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private CategoryRepository categoryRepository;
	@Mock
	private AdminRepository adminRepository;
	@Mock
	private JiraService jiraService;
	@Captor
	private ArgumentCaptor<Inquiry> inquiryArgumentCaptor;

	@Test
	@DisplayName("INQUIRY-001 (문의 등록)")
	void createInquiryTest() {
		// Given
		Long userSeq = 1L;
		Long categorySeq = 1L;
		InquiryCreateRequest request = InquiryCreateRequestFactory.createInquiryCreateRequest();
		LocalDate joinedAt = LocalDate.now();

		User user = new User(
			"empId",
			"password",
			"name",
			"email@email.com",
			"address",
			joinedAt,
			"nickname",
			1200000
		);
		Category category = new Category(
			"홍길동",
			2L,
			"이미지주소"
		);
		Inquiry inquiry = new Inquiry(category, user, request.getTitle(), request.getContent(),
			InquiryAnswerState.NO.getState());

		when(userRepository.findByUserSeq(userSeq)).thenReturn(Optional.of(user));
		when(categoryRepository.findCategoryByCategorySeq(categorySeq)).thenReturn(Optional.of(category));
		when(inquiryRepository.save(any())).thenReturn(inquiry);

		// When
		inquiryService.createInquiry(userSeq, request);

		// Then
		verify(userRepository).findByUserSeq(userSeq);
		verify(categoryRepository).findCategoryByCategorySeq(categorySeq);
		verify(inquiryRepository).save(inquiryArgumentCaptor.capture());
		verify(jiraService).createIssueInquiry(any(JiraInquiryRequest.class));
	}

	@Test
	@DisplayName("INQUIRY-002 (유저별 문의 조회)")
	void readInquiryListByUserTest() {
		// Given
		Long userSeq = 1L;
		Pageable pageable = PageRequest.of(0, 3);

		InquiryListByUserResponse response1 = InquiryListByUserResponseFactory.createInquiryListByUserResponse();
		InquiryListByUserResponse response2 = InquiryListByUserResponseFactory.createInquiryListByUserResponse();

		List<InquiryListByUserResponse> expectedList = Arrays.asList(response1, response2);
		Page<InquiryListByUserResponse> expectedPage = new PageImpl<>(expectedList, pageable, expectedList.size());

		when(inquiryRepository.findInquiryListByUserSeq(userSeq, pageable)).thenReturn(expectedPage);

		// When
		Page<InquiryListByUserResponse> result = inquiryService.readInquiryListByUser(userSeq, pageable);

		// Then
		for (int i = 0; i < expectedList.size(); i++) {
			InquiryListByUserResponse expect = expectedList.get(i);
			InquiryListByUserResponse actual = result.getContent().get(i);

			assertEquals(expect.getInquirySeq(), actual.getInquirySeq());
			assertEquals(expect.getCategory(), actual.getCategory());
			assertEquals(expect.getTitle(), actual.getTitle());
			assertEquals(expect.getCreatedAt(), actual.getCreatedAt());
			assertEquals(expect.getAnswerState(), actual.getAnswerState());
		}
	}

	@Test
	@DisplayName("INQUIRY-003 (문의 카테고리 별 문의 조회)")
	void readInquiryListByCategoryTest() {
		// Given
		Long categorySeq = 1L;
		Pageable pageable = PageRequest.of(0, 3);

		InquiryListResponse response1 = InquiryListResponseFactory.createInquiryListResponse();
		InquiryListResponse response2 = InquiryListResponseFactory.createInquiryListResponse();

		List<InquiryListResponse> expectedList = Arrays.asList(response1, response2);
		Page<InquiryListResponse> expectedPage = new PageImpl<>(expectedList, pageable, expectedList.size());

		when(inquiryRepository.findInquiryListByCategorySeq(categorySeq, pageable)).thenReturn(expectedPage);

		// When
		Page<InquiryListResponse> result = inquiryService.readInquiryListByCategory(categorySeq, pageable);

		// Then
		for (int i = 0; i < result.getContent().size(); i++) {
			InquiryListResponse expect = expectedList.get(i);
			InquiryListResponse actual = result.getContent().get(i);

			assertEquals(expect.getInquirySeq(), actual.getInquirySeq());
			assertEquals(expect.getTitle(), actual.getTitle());
			assertEquals(expect.getCreatedAt(), actual.getCreatedAt());
		}
	}

	@Test
	@DisplayName("INQUIRY-005 (특정 문의 조회)")
	void readInquiryOneTest() {
		// Given
		Long inquirySeq = 1L;
		LocalDate joinedAt = LocalDate.now();
		User user = new User(
			"empId",
			"password",
			"name",
			"email@email.com",
			"address",
			joinedAt,
			"nickname",
			1200000
		);
		Category category = new Category(
			"홍길동",
			2L,
			"이미지주소"
		);
		Inquiry inquiry = new Inquiry(category, user, "InquiryTitle", "InquiryContent",
			InquiryAnswerState.NO.getState());

		when(inquiryRepository.findByInquirySeq(inquirySeq)).thenReturn(Optional.of(inquiry));

		// When
		InquiryOneResponse result = inquiryService.readInquiryOne(inquirySeq);

		// Then
		assertNotNull(result);
		assertEquals("InquiryTitle", result.getTitle());
		assertEquals("InquiryContent", result.getContent());
	}

	@Test
	@DisplayName("INQUIRY-006 (문의 답변)")
	void answerInquiryTest() {
		// Given
		Long inquirySeq = 1L;
		Long adminSeq = 1L;
		LocalDate joinedAt = LocalDate.now();
		InquiryAnswerRequest request = InquiryAnswerRequestFactory.createInquiryAnswerRequest();

		Category category = new Category("name", 1L, "imageUrl");
		User user = new User("empId", "pw", "name",
			"email@email.com", "address", joinedAt,
			"nickname", 1200000);
		Admin admin = new Admin("administrator", "123456a!", "admin", "사장님", "CEO");

		Inquiry inquiry = new Inquiry(
			category,
			user,
			"title",
			"content",
			"N"
		);

		when(inquiryRepository.findByInquirySeq(inquirySeq)).thenReturn(Optional.of(inquiry));
		when(adminRepository.findByAdminSeq(adminSeq)).thenReturn(Optional.of(admin));

		// When
		inquiryService.answerInquiry(inquirySeq, adminSeq, request);

		// Then
		assertEquals(request.getAnswerContent(), inquiry.getAnswerContent());
	}

	@Test
	@DisplayName("INQUIRY-007 (문의 카테고리 별 문의 검색)")
	void readInquiryListByCategorySearchTest() {
		// Given
		Long categorySeq = 1L;
		String title = "검색어";
		Pageable pageable = PageRequest.of(0, 10);

		InquiryListResponse inquiryListResponse = new InquiryListResponse(1L, "title1",
			LocalDateTime.of(2024, 2, 6, 0, 0, 0),
			"Y", "답변완료");
		Page<InquiryListResponse> page = new PageImpl<>(Collections.singletonList(inquiryListResponse));

		when(inquiryRepository.findInquiryListByCategorySearch(categorySeq, title, pageable)).thenReturn(page);

		// When
		Page<InquiryListResponse> result = inquiryService.readInquiryListByCategorySearch(categorySeq, title, pageable);

		// Then
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(1, result.getContent().size());
		assertEquals(inquiryListResponse, result.getContent().get(0));
	}

	@Test
	@DisplayName("INQUIRY-008 (문의 삭제)")
	void deleteInquiryTest() {
		// Given
		Long inquirySeq = 1L;
		LocalDate joinedAt = LocalDate.now();

		Category category = new Category("name", 1L, "imageUrl");
		User user = new User("empId", "pw", "name",
			"email@email.com", "address", joinedAt,
			"nickname", 1200000);

		Inquiry inquiry = new Inquiry(
			category,
			user,
			"title",
			"content",
			"N"
		);

		when(inquiryRepository.findByInquirySeq(inquirySeq)).thenReturn(Optional.of(inquiry));

		// When
		inquiryService.deleteInquiry(inquirySeq);

		// Then
		verify(inquiryRepository).delete(inquiry);
	}
}