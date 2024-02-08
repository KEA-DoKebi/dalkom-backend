package com.dokebi.dalkom.domain.order.controller;

import static com.dokebi.dalkom.domain.order.factory.AuthorizeOrderRequestFactory.*;
import static com.dokebi.dalkom.domain.order.factory.OrderCreateRequestFactory.*;
import static com.dokebi.dalkom.domain.order.factory.OrderPageDtoFactory.*;
import static com.dokebi.dalkom.domain.order.factory.OrderStateUpdateRequestFactory.*;
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

import com.dokebi.dalkom.domain.order.dto.AuthorizeOrderRequest;
import com.dokebi.dalkom.domain.order.dto.OrderCreateRequest;
import com.dokebi.dalkom.domain.order.dto.OrderPageDto;
import com.dokebi.dalkom.domain.order.dto.OrderStateUpdateRequest;
import com.dokebi.dalkom.domain.order.service.OrderDetailService;
import com.dokebi.dalkom.domain.order.service.OrderService;
import com.dokebi.dalkom.domain.user.config.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
	@InjectMocks
	OrderController orderController;
	@Mock
	OrderService orderService;
	@Mock
	OrderDetailService orderDetailService;

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
	@DisplayName("ORDER-001 (사용자별 전체 주문 조회)")
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
	@DisplayName("ORDER-002 (주문 확인하기")
	void readOrderPageByProductSeqTest() throws Exception {
		OrderPageDto orderPageDto = createOrderPageDto();
		// API 호출 및 결과 검증
		mockMvc.perform(post("/api/order").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(orderPageDto)))
			.andExpect(status().isOk());

	}

	@Test
	@DisplayName("ORDER-003 (특정 주문 조회)")
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
	@DisplayName("ORDER-004 (관리자 전체 주문 조회)")
	void readOrdersTest() throws Exception {
		mockMvc.perform(get("/api/order")
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk());
		verify(orderService).readOrderByAll(any(Pageable.class));
	}

	@Test
	@DisplayName("ORDER-005 (결제 하기)")
	void createOrderTest() throws Exception {
		OrderCreateRequest orderCreateRequest = createOrderCreateRequest();
		Long userSeq = 1L;

		// API 호출 및 결과 검증
		mockMvc.perform(post("/api/order").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(orderCreateRequest)))
			.andExpect(status().isOk());

		verify(orderService).createOrder(eq(userSeq), any(OrderCreateRequest.class));

	}

	@Test
	@DisplayName("ORDER-006 (특정 주문 상태 수정)")
	void updateOrderStateTest() throws Exception {
		String orderState = "51";
		Long orderSeq = 13L;
		OrderStateUpdateRequest orderStateUpdateRequest = createOrderStateUpdateRequest(orderState);

		mockMvc.perform(put("/api/order/{orderSeq}/state", orderSeq)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(orderStateUpdateRequest)))
			.andExpect(status().isOk());

		verify(orderService).updateOrderState(anyLong(), any());
	}

	@Test
	@DisplayName("ORDER-008 (주문 취소)")
	void cancelOrderByOrderSeqTest() throws Exception {
		Long orderSeq = 1L;

		mockMvc.perform(delete("/api/order/cancel/{orderSeq}", orderSeq))
			.andExpect(status().isOk());

		verify(orderService).deleteOrderByOrderSeq(orderSeq);
	}

	@Test
	@DisplayName("ORDER-009 (환불 확인)")
	void refundOrderByOrderSeqTest() throws Exception {
		Long orderSeq = 1L;

		mockMvc.perform(delete("/api/order/refund/{orderSeq}", orderSeq))
			.andExpect(status().isOk());

		verify(orderService).confirmRefundByOrderSeq(orderSeq);

	}

	@Test
	@DisplayName("ORDER-010 (결제 비밀번호 인증)")
	void authorizeOrderByPasswordTest() throws Exception {
		Long userSeq = 1L;
		AuthorizeOrderRequest authorizeOrderRequest = createAuthorizeOrderRequest();
		mockMvc.perform(post("/api/order/authorize")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(authorizeOrderRequest)))
			.andExpect(status().isOk());

		verify(orderService).authorizeOrderByPassword(eq(userSeq), eq(authorizeOrderRequest));
	}

	@Test
	@DisplayName("ORDER-011 (리뷰용 단일 주문상세 조회)")
	void readOrderDetailByOrderDetailSeqTest() throws Exception {
		Long orderDetailSeq = 1L;
		mockMvc.perform(get("/api/order/detail/{orderDetailSeq}", orderDetailSeq))
			.andExpect(status().isOk());

		verify(orderDetailService).readOrderDetailSimpleResponseByOrderDetailSeq(eq(orderDetailSeq));
	}

	@Test
	@DisplayName("ORDER-012 (취소/환불 목록 조회)")
	void readOrderCancelListByUserSeqTest() throws Exception {
		Long userSeq = 1L;
		PageRequest pageable = PageRequest.of(0, 8);
		mockMvc.perform(get("/api/order/cancelrefundlist")
				.param("page", "0")
				.param("size", "8"))
			.andExpect(status().isOk());

		verify(orderService).readOrderCancelListByUserSeq(userSeq, pageable);
	}

	@Test
	@DisplayName("ORDER-013 (관리자 주문 목록 검색 )")
	void readOrderListByAdminSearchTest() throws Exception {
		String receiveName = "receiverName";
		String name = "name";
		PageRequest pageable = PageRequest.of(0, 8);
		mockMvc.perform(get("/api/order/admin/search")
				.param("receiverName", receiveName)
				.param("name", name)
				.param("page", "0")
				.param("size", "8"))
			.andExpect(status().isOk());
		verify(orderService).readOrderListByAdminSearch(receiveName, name, pageable);
	}

}
