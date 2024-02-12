package com.dokebi.dalkom.domain.user.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageImpl;
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

import com.dokebi.dalkom.domain.user.config.LoginUser;
import com.dokebi.dalkom.domain.user.dto.ReadUserSelfDetailResponse;
import com.dokebi.dalkom.domain.user.dto.UserUpdateRequest;
import com.dokebi.dalkom.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	private final ObjectMapper objectMapper = new ObjectMapper();
	@InjectMocks
	private UserController userController;
	@Mock
	private UserService userService;
	private MockMvc mockMvc;

	@BeforeEach
	void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController)
			.setCustomArgumentResolvers(
				new PageableHandlerMethodArgumentResolver(), // Pageable 처리를 위한 리졸버 추가
				new HandlerMethodArgumentResolver() { // @LoginUser 처리를 위한 커스텀 리졸버
					@Override
					public boolean supportsParameter(MethodParameter parameter) {
						return parameter.getParameterType().equals(Long.class) &&
							parameter.hasParameterAnnotation(LoginUser.class);
					}

					@Override
					public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
						NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
						return 1L; // 테스트에 사용할 로그인 사용자 ID를 1L로 가정
					}
				}
			)
			.build();
	}

	@Test
	@DisplayName("USER-003 (사용자 정보 수정)")
	void updateUser() throws Exception {
		// Given
		Long userSeq = 1L;
		UserUpdateRequest updateRequest = new UserUpdateRequest("newEmail@example.com", "New Name", "New Nickname");
		// doNothing().when(userService).updateUserByUserSeq(anyLong(), any(UserUpdateRequest.class),
		// 	any(HttpServletRequest.class));

		// When & Then
		mockMvc.perform(put("/api/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateRequest)))
			.andExpect(status().isOk());

		// userService의 updateUser가 호출되었는지 확인
		verify(userService).updateUserByUserSeq(eq(userSeq), eq(updateRequest), any(HttpServletRequest.class));
	}

	@Test
	@DisplayName("USER-004 (사용자 정보 목록 조회)")
	void readUserList() throws Exception {
		// Given
		Pageable pageable = PageRequest.of(0, 5);
		when(userService.readUserList(pageable)).thenReturn(
			new PageImpl<>(java.util.Collections.emptyList()));

		// When & Then
		mockMvc.perform(get("/api/user").param("page", "0").param("size", "5"))
			.andExpect(status().isOk());

		verify(userService).readUserList(pageable);
	}

	@Test
	@DisplayName("USER-006 (사용자 정보 목록 조회 검색)")
	void readUserListSearch() throws Exception {
		// Given
		String email = "user@example.com";
		String nickname = "UserNickname";
		Pageable pageable = PageRequest.of(0, 5);
		when(userService.readUserListSearch(email, nickname, pageable)).thenReturn(
			new PageImpl<>(java.util.Collections.emptyList()));

		// When & Then
		mockMvc.perform(get("/api/user/search")
				.param("email", email)
				.param("nickname", nickname)
				.param("page", "0")
				.param("size", "5"))
			.andExpect(status().isOk());

		verify(userService).readUserListSearch(email, nickname, pageable);
	}

	@Test
	@DisplayName("USER-007 (사용자 본인 정보 조회)")
	void readUserSelfDetail() throws Exception {
		// Given
		Long userSeq = 1L; // @LoginUser에 의해 제공될 사용자 식별자 가정
		ReadUserSelfDetailResponse response = new ReadUserSelfDetailResponse(
			"user@example.com", "User Name", "UserNickname", "User Address");
		given(userService.readUserSelfDetail(eq(userSeq), any(HttpServletRequest.class)))
			.willReturn(response);

		// When & Then
		mockMvc.perform(get("/api/user/self")
				.param("userSeq", String.valueOf(userSeq)))
			.andExpect(status().isOk());

		verify(userService).readUserSelfDetail(eq(userSeq), any(HttpServletRequest.class));
	}
}
