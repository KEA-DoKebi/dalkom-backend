package com.dokebi.dalkom.domain.order.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.dokebi.dalkom.domain.order.dto.OrderCreateRequest;
import com.dokebi.dalkom.domain.order.factory.OrderCreateRequestFactory;
import com.dokebi.dalkom.domain.order.service.OrderService;
import com.dokebi.dalkom.domain.user.config.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
	@InjectMocks
	OrderController orderController;
	@Mock
	OrderService orderService;

	MockMvc mockMvc;

	@BeforeEach
	void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(orderController)
			.setCustomArgumentResolvers(new HandlerMethodArgumentResolver() {
				@Override
				public boolean supportsParameter(MethodParameter parameter) {
					return parameter.hasParameterAnnotation(LoginUser.class);
				}

				@Override
				public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
					NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
					return 1L; // 여기서는 userSeq를 1L로 가정
				}
			})
			.build();
	}

	// ORDER-001(사용자별 주문 조회) 테스트
	@Test
	void readOrdersByUserSeqTest() throws Exception {
		// Given (준비)

		// When (실행)
		mockMvc.perform(get("/api/order/user"))
			.andExpect(status().isOk());

		// 검증
		verify(orderService).readOrderByUserSeq(1L); // 여기서는 userSeq를 1L로 가정

	}

	// ORDER-003(특정 주문 조회) 테스트
	@Test
	void readOrderByOrderSeqTest() throws Exception {
		Long orderSeq = 1L;

		// when(실행)
		mockMvc.perform(
				get("/api/order/{orderSeq}", orderSeq))
			.andExpect(status().isOk());

		//검증
		verify(orderService).readOrderByOrderSeq(orderSeq);

	}

	// ORDER-004(전체 주문 조회) 테스트
	@Test
	void readOrdersTest() throws Exception {

		mockMvc.perform(
				get("/api/order"))
			.andExpect(status().isOk());
	}

	// // ORDER-005(결제 하기) 테스트
	@Test
	void createOrderTest() throws Exception {
		OrderCreateRequest orderCreateRequest = OrderCreateRequestFactory.createOrderCreateRequest();

		// API 호출 및 결과 검증
		mockMvc.perform(post("/api/order")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(orderCreateRequest)))
			.andExpect(status().isOk());

	}

}
