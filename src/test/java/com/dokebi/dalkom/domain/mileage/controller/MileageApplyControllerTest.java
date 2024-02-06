package com.dokebi.dalkom.domain.mileage.controller;

import static com.dokebi.dalkom.domain.mileage.factory.MileageApplyRequestFactory.*;
import static com.dokebi.dalkom.domain.mileage.factory.MileageApplyUpdateRequestFactory.*;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.dokebi.dalkom.domain.mileage.dto.MileageApplyRequest;
import com.dokebi.dalkom.domain.mileage.dto.MileageStateRequest;
import com.dokebi.dalkom.domain.mileage.service.MileageApplyService;
import com.dokebi.dalkom.domain.user.config.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;

class MileageApplyControllerTest {
	@InjectMocks
	MileageApplyController mileageApplyController;
	@Mock
	MileageApplyService mileageApplyService;

	MockMvc mockMvc;

	@BeforeEach
	void beforeEach() {
		// 이 부분이 누락되었는지 확인
		MockitoAnnotations.initMocks(this);

		//page 을 위한 setting
		this.mockMvc = MockMvcBuilders.standaloneSetup(mileageApplyController)
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
	@DisplayName("마일리지 승인 여부 변경")
	void updateMileageAskStateTest() throws Exception {
		Long milgApplySeq = 1L;
		MileageStateRequest request = createMileageUpdateRequestFactory("W");

		mockMvc.perform(put("/api/mileage/apply/{milgApplySeq}", milgApplySeq)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request)))
			.andExpect(status().isOk());

		verify(mileageApplyService).updateMileageApply(eq(milgApplySeq), eq(request));
	}

	@Test
	@DisplayName("마일리지 신청 조회(관리자)")
	void readMileageAskTest() throws Exception {
		mockMvc.perform(get("/api/mileage/apply")
				.param("page", "0")
				.param("size", "5"))
			.andExpect(status().isOk());

		verify(mileageApplyService).readMileageApply(any(Pageable.class));
	}

	@Test
	@DisplayName("마일리지 충전 신청")
	void createMileageAsk() throws Exception {
		int amount = 5000;
		Long userSeq = 1L;
		MileageApplyRequest request = createMileageApplyRequestFactory(amount);
		mockMvc.perform(post("/api/mileage/apply/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request)))
			.andExpect(status().isOk());

		verify(mileageApplyService).createMileageApply(eq(userSeq), eq(request));
	}

}
