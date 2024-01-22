package com.dokebi.dalkom.domain.order.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dokebi.dalkom.domain.order.dto.OrderCreateRequest;
import com.dokebi.dalkom.domain.order.factory.OrderCreateRequestFactory;
import com.dokebi.dalkom.domain.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
	@InjectMocks
	OrderController orderController;
	@Mock
	OrderService orderService;

	MockMvc mockMvc;
	@Mock
	private ObjectMapper objectMapper;


	@BeforeEach
	void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
	}

	@Test
	@DisplayName("사용자별 주문 조회 테스트")
	void readOrdersByUserSeqTest() throws Exception {
		// given(준비)
		Long userSeq = 1L;

		// when(실행)
		mockMvc.perform(
				get("/api/order/user/{userSeq}", userSeq))
			.andExpect(status().isOk());

		//검증
		verify(orderService).readOrderByUserSeq(userSeq);

	}

	@Test
	@DisplayName("특정 주문 조회 테스트")
	void readOrderByOrderSeqTest() throws Exception {
		Long orderSeq = 1L ;

		// when(실행)
		mockMvc.perform(
				get("/api/order/{orderSeq}", orderSeq))
			.andExpect(status().isOk());

		//검증
		verify(orderService).readOrderByOrderSeq(orderSeq);

	}

	@Test
	@DisplayName("전체 주문 조회 테스트")
	void readOrdersTest() throws Exception {

		mockMvc.perform(
			get("/api/order"))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("결제 하기 테스트")
	void createOrderTest() throws Exception {
		OrderCreateRequest orderCreateRequest = OrderCreateRequestFactory.createOrderCreateRequest();

		// API 호출 및 결과 검증
		mockMvc.perform(post("/api/order")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(orderCreateRequest)))
			.andExpect(status().isOk());

	}

}
