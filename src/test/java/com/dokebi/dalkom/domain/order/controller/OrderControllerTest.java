package com.dokebi.dalkom.domain.order.controller;

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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
		int page = 0; // 페이지 번호
		int size = 10; // 페이지 크기
		String sort = "orderSeq,desc"; // 정렬 방식

		// when(실행)
		mockMvc.perform(
				get("/api/order/user/{userSeq}", userSeq)
					.param("page", String.valueOf(page))
					.param("size", String.valueOf(size))
					.param("sort", sort))
			.andExpect(status().isOk());

		//검증
		verify(orderService).readOrderByUserSeq(userSeq, any(Pageable.class));
	}

	// ORDER-003(특정 주문 조회) 테스트
	@Test
	@DisplayName("특정 주문 조회 테스트")
	void readOrderByOrderSeqTest() throws Exception {
		Long orderSeq = 1L;

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
