package com.dokebi.dalkom.domain.inquiry.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.dokebi.dalkom.domain.inquiry.dto.FaqCreateRequest;
import com.dokebi.dalkom.domain.inquiry.dto.FaqUpdateRequest;
import com.dokebi.dalkom.domain.inquiry.factory.FaqCreateRequestFactory;
import com.dokebi.dalkom.domain.inquiry.factory.FaqUpdateRequestFactory;
import com.dokebi.dalkom.domain.inquiry.service.FaqService;
import com.dokebi.dalkom.domain.user.config.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class FaqControllerTest {
	@InjectMocks
	private FaqController faqController;
	@Mock
	private FaqService faqService;
	private MockMvc mockMvc;

	private String asJsonString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@BeforeEach
	void beforeEach() {
		this.mockMvc = MockMvcBuilders
			.standaloneSetup(faqController)
			.setCustomArgumentResolvers(
				new PageableHandlerMethodArgumentResolver(),
				new HandlerMethodArgumentResolver() {
					@Override
					public boolean supportsParameter(MethodParameter parameter) {
						return parameter.getParameterType().equals(Long.class) &&
							parameter.hasParameterAnnotation(LoginUser.class);
					}

					@Override
					public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
						NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
						return 1L;
					}
				}
			)
			.build();
	}

	@Test
	@DisplayName("FAQ-001 (FAQ 상세 조회)")
	void readFaqByInquirySeqTest() throws Exception {
		// Given
		Long inquirySeq = 1L;

		// When, Then
		mockMvc.perform(get("/api/faq/{inquirySeq}", inquirySeq))
			.andExpect(status().isOk());

		verify(faqService).readFaqByInquirySeq(inquirySeq);
	}

	@Test
	@DisplayName("FAQ-002 (FAQ 전체 조회 )")
	void readFaqListTest() throws Exception {
		// Given
		int page = 0; // 페이지 번호
		int size = 10; // 페이지 크기
		Pageable pageable = PageRequest.of(0, 10);

		// When, Then
		mockMvc.perform(get("/api/faq")
				.param("page", String.valueOf(page))
				.param("size", String.valueOf(size)))
			.andExpect(status().isOk());

		verify(faqService).readFaqList(pageable);
	}

	@Test
	@DisplayName("FAQ-003 (FAQ 등록)")
	void createFaqTest() throws Exception {
		// Given
		Long adminSeq = 1L;
		FaqCreateRequest request = FaqCreateRequestFactory.createFaqCreateRequest();

		// When, Then
		mockMvc.perform(post("/api/faq")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
			.andExpect(status().isOk());

		verify(faqService).createFaq(adminSeq, request);
	}

	@Test
	@DisplayName("FAQ-004 (FAQ 수정)")
	void updateFaqTest() throws Exception {
		// Given
		Long adminSeq = 1L;
		Long inquirySeq = 1L;
		FaqUpdateRequest request = FaqUpdateRequestFactory.createFaqUpdateRequest();

		// When, Then
		mockMvc.perform(put("/api/faq/{inquirySeq}", inquirySeq)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
			.andExpect(status().isOk());

		verify(faqService).updateFaq(adminSeq, inquirySeq, request);
	}

	@Test
	@DisplayName("FAQ-005 (FAQ 삭제)")
	void deleteFaqTest() throws Exception {
		// Given
		Long inquirySeq = 1L;

		// When, Then
		mockMvc.perform(delete("/api/faq/{inquirySeq}", inquirySeq))
			.andExpect(status().isOk());

		verify(faqService).deleteFaq(inquirySeq);
	}

	@Test
	@DisplayName("FAQ-006 (FAQ 검색)")
	void readFaqListBySearchTest() throws Exception {
		// Given
		String title = "title";
		int page = 0; // 페이지 번호
		int size = 10; // 페이지 크기
		Pageable pageable = PageRequest.of(0, 10);

		// When, Then
		mockMvc.perform(get("/api/faq/search")
				.param("page", String.valueOf(page))
				.param("size", String.valueOf(size))
				.param("title", title))
			.andExpect(status().isOk());

		verify(faqService).readFaqListBySearch(title, pageable);
	}
}
