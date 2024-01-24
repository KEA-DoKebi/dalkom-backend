package com.dokebi.dalkom.domain.notice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

import com.dokebi.dalkom.domain.notice.dto.NoticeCreateRequest;
import com.dokebi.dalkom.domain.notice.dto.NoticeUpdateRequest;
import com.dokebi.dalkom.domain.notice.factory.NoticeCreateRequestFactory;
import com.dokebi.dalkom.domain.notice.factory.NoticeUpdateRequestFactory;
import com.dokebi.dalkom.domain.notice.service.NoticeService;
import com.dokebi.dalkom.domain.user.config.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class NoticeControllerTest {

	@InjectMocks
	private NoticeController noticeController;
	@Mock
	private NoticeService noticeService;
	private MockMvc mockMvc;
	@Captor
	private ArgumentCaptor<Pageable> pageableCaptor;

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders
			.standaloneSetup(noticeController)
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

	private String asJsonString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("특정 공지 조회 테스트")
	void readNotice() throws Exception {
		// Given
		Long noticeSeq = 1L;

		// When, Then
		mockMvc.perform(get("/api/notice/{noticeSeq}", noticeSeq))
			.andExpect(status().isOk());

		verify(noticeService).readNotice(noticeSeq);
	}

	@Test
	@DisplayName("공지 리스트 조회 테스트")
	void readNoticeList() throws Exception {
		// Given
		int page = 0; // 페이지 번호
		int size = 10; // 페이지 크기

		// When, Then
		mockMvc.perform(get("/api/notice")
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk());

		verify(noticeService).readNoticeList(pageableCaptor.capture());

		Pageable pageable = pageableCaptor.getValue();
		assertEquals(page, pageable.getPageNumber());
		assertEquals(size, pageable.getPageSize());
	}

	@Test
	@DisplayName("공지 작성 테스트")
	void createNotice() throws Exception {
		// Given
		NoticeCreateRequest request = NoticeCreateRequestFactory.createNoticeCreateRequest();

		// When, Then
		mockMvc.perform(post("/api/notice")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
			.andExpect(status().isOk());

		// 서비스 메소드 호출 검증
		verify(noticeService).createNotice(any(Long.class), any(NoticeCreateRequest.class));
	}

	@Test
	@DisplayName("공지 수정 테스트")
	void updateNotice() throws Exception {
		// Given
		Long noticeSeq = 1L;
		NoticeUpdateRequest updateRequest = NoticeUpdateRequestFactory.createNoticeUpdateRequest();

		// When, Then
		mockMvc.perform(put("/api/notice/{noticeSeq}", noticeSeq)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(updateRequest)))
			.andExpect(status().isOk());

		verify(noticeService).updateNotice(noticeSeq, updateRequest);
	}

	@Test
	@DisplayName("공지 삭제 테스트")
	void deleteNotice() throws Exception {
		// Given
		Long noticeSeq = 1L;

		// When
		doNothing().when(noticeService).deleteNotice(noticeSeq);

		// Then
		mockMvc.perform(delete("/api/notice/{noticeSeq}", noticeSeq))
			.andExpect(status().isOk());

		verify(noticeService).deleteNotice(noticeSeq);
	}
}
