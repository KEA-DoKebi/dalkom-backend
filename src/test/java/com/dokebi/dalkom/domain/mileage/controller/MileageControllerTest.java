package com.dokebi.dalkom.domain.mileage.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.dokebi.dalkom.domain.mileage.service.MileageService;
import com.dokebi.dalkom.domain.user.config.LoginUser;

public class MileageControllerTest {
	@InjectMocks
	MileageController mileageController;
	@Mock
	MileageService mileageService;

	MockMvc mockMvc;

	@BeforeEach
	void beforeEach() {
		MockitoAnnotations.initMocks(this); // 이 부분이 누락되었는지 확인

		this.mockMvc = MockMvcBuilders.standaloneSetup(mileageController)
			.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver(),
				new HandlerMethodArgumentResolver() {
					@Override
					public boolean supportsParameter(MethodParameter parameter) {
						return parameter.getParameterType().equals(Long.class) && parameter.hasParameterAnnotation(
							LoginUser.class);
					}

					@Override
					public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
						NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
						return 1L; // 여기서는 userSeq를 1L로 가정하고 직접 제공
					}
				})
			.build();
	}

	@Test
	@DisplayName("특정 유저 보유 마일리지 조회")
	void readMileageByUserSeqTest() throws Exception {
		Long userSeq = 1L;

		mockMvc.perform(get("/api/mileage/user"))
			.andExpect(status().isOk());

		verify(mileageService).readMileageByUserSeq(userSeq);
	}

	@Test
	@DisplayName("특정 유저 마일리지 내역 조회")
	void readMileageHistoryByUserSeq() throws Exception {
		Long userSeq = 1L;

		mockMvc.perform(get("/api/mileage/history/user")
				.param("page", "0")
				.param("size", "5"))
			.andExpect(status().isOk());

		verify(mileageService).readMileageHistoryByUserSeq(eq(userSeq), any(Pageable.class));
	}

}
