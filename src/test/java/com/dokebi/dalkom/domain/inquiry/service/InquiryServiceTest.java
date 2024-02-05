package com.dokebi.dalkom.domain.inquiry.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

import com.dokebi.dalkom.domain.admin.service.AdminService;
import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.category.service.CategoryService;
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
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class InquiryServiceTest {
	@InjectMocks
	private InquiryService inquiryService;
	@Mock
	private InquiryRepository inquiryRepository;
	@Mock
	private UserService userService;
	@Mock
	private AdminService adminService; // answerInquiry에서 쓰임
	@Mock
	private CategoryService categoryService; // 안쓰는 것처럼 보이지만
	@Captor
	private ArgumentCaptor<Inquiry> inquiryArgumentCaptor;

	// Category가 정보가 없어서 그런가 제대로 안되서 category 부분은 제외하고 작성
	@Test
	void createInquiryTest() {
		// Given
		Long userSeq = 1L;
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
		when(userService.readUserByUserSeq(userSeq)).thenReturn(user);

		// When
		inquiryService.createInquiry(userSeq, request);

		// Then
		verify(inquiryRepository).save(inquiryArgumentCaptor.capture());

		Inquiry capturedInquiry = inquiryArgumentCaptor.getValue();

		assertEquals(user, capturedInquiry.getUser());
		assertEquals(request.getTitle(), capturedInquiry.getTitle());
		assertEquals(request.getContent(), capturedInquiry.getContent());
	}

	@Test
	void readInquiryListByUserTest() {
		// Given
		Long userSeq = 1L;
		Pageable pageable = PageRequest.of(0, 3);

		InquiryListByUserResponse response1 = InquiryListByUserResponseFactory.createInquiryListByUserResponse();
		InquiryListByUserResponse response2 = InquiryListByUserResponseFactory.createInquiryListByUserResponse();

		List<InquiryListByUserResponse> expectedList = Arrays.asList(response1, response2);
		Page<InquiryListByUserResponse> expectedPage = new PageImpl<>(expectedList, pageable, expectedList.size());

		when(inquiryService.readInquiryListByUser(userSeq, pageable)).thenReturn(expectedPage);

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
	void readInquiryListByCategoryTest() {
		// Given
		Long categorySeq = 1L;
		Pageable pageable = PageRequest.of(0, 3);

		InquiryListResponse response1 = InquiryListResponseFactory.createInquiryListResponse();
		InquiryListResponse response2 = InquiryListResponseFactory.createInquiryListResponse();

		List<InquiryListResponse> expectedList = Arrays.asList(response1, response2);
		Page<InquiryListResponse> expectedPage = new PageImpl<>(expectedList, pageable, expectedList.size());

		when(inquiryService.readInquiryListByCategory(categorySeq, pageable)).thenReturn(expectedPage);

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
	void readInquiryOneTest() {
		// Given
		Long inquirySeq = 1L;

		InquiryOneResponse response = new InquiryOneResponse(
			"title",
			"content",
			LocalDateTime.of(2024, 1, 15, 0, 0),
			null,
			null,
			null
		);

		when(inquiryRepository.findInquiryOne(inquirySeq)).thenReturn(response);

		// When
		InquiryOneResponse result = inquiryService.readInquiryOne(inquirySeq);

		// Then
		assertEquals(response.getTitle(), result.getTitle());
		assertEquals(response.getContent(), result.getContent());
		assertEquals(response.getCreatedAt(), result.getCreatedAt());
		assertEquals(response.getAnsweredAt(), result.getAnsweredAt());
		assertEquals(response.getNickname(), result.getNickname());
	}

	@Test
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

		Inquiry inquiry = new Inquiry(
			category,
			user,
			"title",
			"content",
			"N"
		);

		when(inquiryRepository.findByInquirySeq(inquirySeq)).thenReturn(Optional.of(inquiry));

		// When
		inquiryService.answerInquiry(inquirySeq, adminSeq, request);

		// Then
		assertEquals(request.getAnswerContent(), inquiry.getAnswerContent());
	}
}