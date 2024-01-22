package com.dokebi.dalkom.domain.cart.controller;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dokebi.dalkom.domain.cart.dto.OrderCartCreateRequest;
import com.dokebi.dalkom.domain.cart.dto.OrderCartDeleteRequest;
import com.dokebi.dalkom.domain.cart.factory.OrderCartCreateRequestFactory;
import com.dokebi.dalkom.domain.cart.factory.OrderCartDeleteRequestFactory;
import com.dokebi.dalkom.domain.cart.service.OrderCartService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class OrderCartControllerTest {
	@InjectMocks
	private OrderCartController orderCartController;
	@Mock
	private OrderCartService orderCartService;
	private MockMvc mockMvc;

	@BeforeEach
	void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(orderCartController).build();
	}

	private String asJsonString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("특정 유저의 장바구니 리스트 조회 테스트")
	void readOrderCartListTest() throws Exception {
		// Given
		Long userSeq = 1L;

		// When, Then
		mockMvc.perform(get("/api/cart/user/{userSeq}", userSeq))
			.andExpect(status().isOk());

		verify(orderCartService).readOrderCartList(userSeq);
	}

	@Test
	@DisplayName("특정 유저의 장바구니에 상품 담기 테스트")
	void createOrderCartTest() throws Exception {
		// Given
		Long userSeq = 1L;
		OrderCartCreateRequest request = OrderCartCreateRequestFactory.createOrderCartCreateRequest();

		// When, Then
		mockMvc.perform(
				post("/api/cart/user/{userSeq}", userSeq)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(request)))
			.andExpect(status().isOk());

		verify(orderCartService).createOrderCart(userSeq, request);
	}

	@Test
	@DisplayName("특정 유저의 장바구니에서 상품 삭제 테스트")
	void deleteOrderCartTest() throws Exception {
		// Given
		OrderCartDeleteRequest request = OrderCartDeleteRequestFactory.createOrderCartDeleteRequest();

		// When, Then
		mockMvc.perform(
				delete("/api/cart")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(request)))
			.andExpect(status().isOk());

		verify(orderCartService).deleteOrderCart(request);
	}
}
