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

import com.dokebi.dalkom.domain.inquiry.dto.FaqReadListResponse;
import com.dokebi.dalkom.domain.inquiry.dto.FaqReadOneResponse;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FaqRepositoryTest {
	@Autowired
	private FaqRepository faqRepository;
	private static final Long FAQ_CATEGORY_SEQ = 38L;

	@Test
	@DisplayName("findFaqOne 테스트")
	void findFaqOneTest() {
		// Given
		Long faqSeq = 112L;
		// When
		Optional<FaqReadOneResponse> response = faqRepository.findFaqOne(faqSeq);

		// Then
		assertTrue(response.isPresent());
	}

	@Test
	@DisplayName("findFaqList 테스트")
	void findFaqListTest() {
		// Given
		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<FaqReadListResponse> faqListPage = faqRepository.findFaqList(pageable);

		// Then
		assertNotNull(faqListPage);
		assertFalse(faqListPage.isEmpty());
		assertEquals(faqListPage.getSize(), pageable.getPageSize());
	}

	@Test
	@DisplayName("findFaqListSearch 테스트")
	void findFaqListSearchTest() {
		// Given
		String title = "FAQ";
		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<FaqReadListResponse> faqListPage = faqRepository.findFaqListSearch(title, pageable);

		// Then
		assertNotNull(faqListPage);
		assertFalse(faqListPage.isEmpty());
		assertEquals(faqListPage.getSize(), pageable.getPageSize());
	}
}
