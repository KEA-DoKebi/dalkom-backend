package com.dokebi.dalkom.domain.order.controller;

import static com.dokebi.dalkom.domain.inquiry.factory.OrderStateUpdateRequestFactory.*;
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

import com.dokebi.dalkom.domain.order.dto.OrderStateUpdateRequest;
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
		this.mockMvc = MockMvcBuilders.standaloneSetup(orderController)
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
	@DisplayName("사용자별 주문 조회 테스트")
	void readOrdersByUserSeqTest() throws Exception {
		// Given (준비)
		Long userSeq = 1L;

		// 수정: Pageable을 직접 생성하지 않고, MockMvcRequestBuilders의 requestParam 메서드를 사용하여 생성

		mockMvc.perform(get("/api/order/user")
				.param("page", "0")
				.param("size", "5"))
			.andExpect(status().isOk());

		// then(검증)
		// 수정: Pageable을 직접 생성하지 않고, Mockito의 any 메서드로 대체
		verify(orderService).readOrderByUserSeq(eq(userSeq), any(Pageable.class));
	}

	@Test
	@DisplayName("특정 주문 조회 테스트")
	void readOrderByOrderSeqTest() throws Exception {
		// given
		Long orderSeq = 1L;

		// when(실행)
		mockMvc.perform(get("/api/order/{orderSeq}", orderSeq)
				.param("page", "0")
				.param("size", "5"))

			.andExpect(status().isOk());
		// then(검증)
		verify(orderService).readOrderByOrderSeq(orderSeq);
	}

	@Test
	@DisplayName("전체 주문 조회 테스트")
	void readOrdersTest() throws Exception {
		mockMvc.perform(get("/api/order").param("page", "0").param("size", "10")).andExpect(status().isOk());
		verify(orderService).readOrderByAll(any(Pageable.class));
	}

	// @Test
	// @DisplayName("결제 하기 테스트")
	// void createOrderTest() throws Exception {
	// 	OrderCreateRequest orderCreateRequest = OrderCreateRequestFactory.createOrderCreateRequest();
	//
	// 	// API 호출 및 결과 검증
	// 	mockMvc.perform(post("/api/order").contentType(MediaType.APPLICATION_JSON)
	// 		.content(new ObjectMapper().writeValueAsString(orderCreateRequest))).andExpect(status().isOk());
	//
	// 	verify(orderService).createOrder(eq(orderCreateRequest));
	//
	// }

	@Test
	@DisplayName("주문 상태 수정 테스트")
	void updateOrderStateTest() throws Exception {
		String orderState = "51";
		Long orderSeq = 13L;
		OrderStateUpdateRequest orderStateUpdateRequest = createOrderStateUpdateRequestFactory(orderState);

		mockMvc.perform(put("/api/order/{orderSeq}/state", orderSeq)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(orderStateUpdateRequest)))
			.andExpect(status().isOk());

		verify(orderService).updateOrderState(anyLong(), any());
	}
}
