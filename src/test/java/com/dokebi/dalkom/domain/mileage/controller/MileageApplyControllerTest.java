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

import com.dokebi.dalkom.domain.mileage.dto.MileageApplyRequest;
import com.dokebi.dalkom.domain.mileage.dto.MileageStateRequest;
import com.dokebi.dalkom.domain.mileage.service.MileageApplyService;
import com.dokebi.dalkom.domain.user.config.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;

// Note: Recompile with -Xlint:deprecation for details. 안뜨게 하는 Annotation
@SuppressWarnings("deprecation")
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
	void updateMileageApplyStateTest() throws Exception {
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
	void readMileageApplyTest() throws Exception {
		mockMvc.perform(get("/api/mileage/apply")
				.param("page", "0")
				.param("size", "5"))
			.andExpect(status().isOk());

		verify(mileageApplyService).readMileageApply(any(Pageable.class));
	}

	@Test
	@DisplayName("마일리지 충전 신청")
	void createMileageApplyByUserSeqTest() throws Exception {
		int amount = 5000;
		Long userSeq = 1L;
		MileageApplyRequest request = createMileageApplyRequestFactory(amount);
		mockMvc.perform(post("/api/mileage/apply/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request)))
			.andExpect(status().isOk());

		verify(mileageApplyService).createMileageApply(eq(userSeq), eq(request));
	}

	@Test
	@DisplayName("마일리지 신청 조회 내역 검색 (관리자)")
	void readMileageApplyHistorySearchTest() throws Exception {
		String email = "email";
		String nickname = "nickname";
		String name = "name";

		PageRequest pageable = PageRequest.of(0, 8);
		mockMvc.perform(get("/api/mileage/apply/search")
				.param("email", email)
				.param("nickname", nickname)
				.param("name", name)
				.param("page", "0")
				.param("size", "8"))
			.andExpect(status().isOk());
		verify(mileageApplyService).readMileageApplyHistoryListSearch(email, nickname, name, pageable);
	}

	@Test
	@DisplayName("마일리지 신청 조회 - 대기중(W)인 값 조회 (사용자)")
	void readMileageApplyByUserSeqTest() throws Exception {
		Long userSeq = 1L;
		PageRequest pageable = PageRequest.of(0, 8);
		mockMvc.perform(get("/api/mileage/apply/user")
				.param("page", "0")
				.param("size", "8"))
			.andExpect(status().isOk());

		verify(mileageApplyService).readMileageApplyListByUserSeq(userSeq, pageable);

	}

	@Test
	@DisplayName("마일리지 신청 조회 - 대기중(W)인 값 조회 (관리자)")
	void readMileageApplyWaitState() throws Exception {
		PageRequest pageable = PageRequest.of(0, 8);
		mockMvc.perform(get("/api/mileage/apply/wait")
				.param("page", "0")
				.param("size", "8"))
			.andExpect(status().isOk());
		verify(mileageApplyService).readMileageApplyWaitStateList(pageable);
	}

	@Test
	@DisplayName("마일리지 신청 검색 - 대기중(W)인 값 조회 (관리자)")
	void readMileageApplyWaitStateSearch() throws Exception {

		String email = "email";
		String nickname = "nickname";
		String name = "name";

		PageRequest pageable = PageRequest.of(0, 8);
		mockMvc.perform(get("/api/mileage/apply/wait/search")
				.param("email", email)
				.param("nickname", nickname)
				.param("name", name)
				.param("page", "0")
				.param("size", "8"))
			.andExpect(status().isOk());
		verify(mileageApplyService).readMileageApplyWaitStateSearch(email, nickname, name, pageable);

	}

}
