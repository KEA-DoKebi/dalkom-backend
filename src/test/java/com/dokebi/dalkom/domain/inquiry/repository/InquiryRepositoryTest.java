package com.dokebi.dalkom.domain.inquiry.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.dokebi.dalkom.domain.inquiry.dto.InquiryListByUserResponse;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryListResponse;
import com.dokebi.dalkom.domain.inquiry.entity.Inquiry;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InquiryRepositoryTest {
	@Autowired
	private InquiryRepository inquiryRepository;

	@Test
	@DisplayName("findByInquirySeq 테스트")
	void findByInquirySeqTest() {
		// Given (반드시 DB에 존재하는 inquirySeq를 사용할 것)
		Long inquirySeq = 1L;

		// When
		Optional<Inquiry> inquiry = inquiryRepository.findByInquirySeq(inquirySeq);

		// Then
		assertTrue(inquiry.isPresent());
	}

	@Test
	@DisplayName("findInquiryListByUserSeq 테스트")
	void findInquiryListByUserSeqTest() {
		// Given
		Long userSeq = 1L;
		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<InquiryListByUserResponse> inquiryListPage
			= inquiryRepository.findInquiryListByUserSeq(userSeq, pageable);

		// Then
		assertNotNull(inquiryListPage);
		assertFalse(inquiryListPage.isEmpty());
		assertEquals(inquiryListPage.getSize(), pageable.getPageSize());
	}

	@Test
	@DisplayName("findInquiryListByCategorySeq 테스트")
	void findInquiryListByCategorySeqTest() {
		// Given
		Long categorySeq = 34L;
		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<InquiryListResponse> inquiryListPage
			= inquiryRepository.findInquiryListByCategorySeq(categorySeq, pageable);

		// Then
		assertNotNull(inquiryListPage);
		assertFalse(inquiryListPage.isEmpty());
		assertEquals(inquiryListPage.getSize(), pageable.getPageSize());
	}

	@Test
	@DisplayName("findInquiryListByCategorySearch 테스트")
	void findInquiryListByCategorySearchTest() {
		// Given 실제 존재하는 카테고리 번호와 이에 해당하는 title을 사용
		Long categorySeq = 34L;
		String title = "사이즈";
		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<InquiryListResponse> inquiryListPage
			= inquiryRepository.findInquiryListByCategorySearch(categorySeq, title, pageable);

		// Then
		assertNotNull(inquiryListPage);
		assertFalse(inquiryListPage.isEmpty());
		assertEquals(inquiryListPage.getSize(), pageable.getPageSize());
	}
}
