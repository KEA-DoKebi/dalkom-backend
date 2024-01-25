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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.dokebi.dalkom.domain.inquiry.dto.InquiryAnswerRequest;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryCreateRequest;
import com.dokebi.dalkom.domain.inquiry.factory.InquiryAnswerRequestFactory;
import com.dokebi.dalkom.domain.inquiry.factory.InquiryCreateRequestFactory;
import com.dokebi.dalkom.domain.inquiry.service.InquiryService;
import com.dokebi.dalkom.domain.user.config.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class InquiryControllerTest {
	@InjectMocks
	private InquiryController inquiryController;
	@Mock
	private InquiryService inquiryService;
	private MockMvc mockMvc;

	private String asJsonString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders
			.standaloneSetup(inquiryController)
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
						return 1L; // 여기서는 userSeq를 1L로 가정하고 직접 제공
					}
				}
			)
			.build();
	}

	@Test
	@DisplayName("문의 등록 테스트")
	void createInquiryTest() throws Exception {
		// Given
		InquiryCreateRequest request = InquiryCreateRequestFactory.createInquiryCreateRequest();

		// When, Then
		mockMvc.perform(post("/api/inquiry/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
			.andExpect(status().isOk());

		verify(inquiryService).createInquiry(1L, request);
	}

	@Test
	@DisplayName("특정 유저의 문의 조회")
	void readInquiryByUserTest() throws Exception {

		//Given
		Long userSeq = 1L;

		// When, Then
		mockMvc.perform(get("/api/inquiry/user")
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk());

		verify(inquiryService).readInquiryListByUser(eq(userSeq), any(Pageable.class));
	}

	@Test
	@DisplayName("카테고리 별 문의 조회")
	void readInquiryByCategoryTest() throws Exception {
		// Given
		Long categorySeq = 1L;

		// When, Then
		mockMvc.perform(get("/api/inquiry/category/{categorySeq}", categorySeq)
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk());

		verify(inquiryService).readInquiryListByCategory(eq(categorySeq), any(Pageable.class));
	}

	@Test
	@DisplayName("특정 문의 조회 테스트")
	void readInquiryOneTest() throws Exception {
		// Given
		Long inquirySeq = 1L;

		// When, Then
		mockMvc.perform(get("/api/inquiry/{inquirySeq}", inquirySeq))
			.andExpect(status().isOk());

		verify(inquiryService).readInquiryOne(inquirySeq);
	}

	@Test
	@DisplayName("문의 답변 테스트")
	void answerInquiryTest() throws Exception {
		// Given
		Long inquirySeq = 1L;
		Long adminSeq = 1L;
		InquiryAnswerRequest request = InquiryAnswerRequestFactory.createInquiryAnswerRequest();

		// When, Then
		mockMvc.perform(put("/api/inquiry/{inquirySeq}", inquirySeq)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
			.andExpect(status().isOk());

		verify(inquiryService).answerInquiry(inquirySeq, adminSeq, request);
	}
}
