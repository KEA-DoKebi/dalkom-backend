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

import com.dokebi.dalkom.domain.cart.dto.OrderCartCreateRequest;
import com.dokebi.dalkom.domain.cart.dto.OrderCartDeleteRequest;
import com.dokebi.dalkom.domain.cart.factory.OrderCartCreateRequestFactory;
import com.dokebi.dalkom.domain.cart.factory.OrderCartDeleteRequestFactory;
import com.dokebi.dalkom.domain.cart.service.OrderCartService;
import com.dokebi.dalkom.domain.user.config.LoginUser;
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
		this.mockMvc = MockMvcBuilders
			.standaloneSetup(orderCartController)
			.setCustomArgumentResolvers(
				new PageableHandlerMethodArgumentResolver(),
				new HandlerMethodArgumentResolver() {
					@Override
					public boolean supportsParameter(MethodParameter parameter) {
						return parameter.getParameterType().equals(Long.class) &&
							parameter.hasParameterAnnotation(LoginUser.class);
					}

					@Override
					public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
						NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
						return 1L; // 여기서는 userSeq를 1L로 가정하고 직접 제공
					}
				}
			)
			.build();
	}

	private String asJsonString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@DisplayName("CART-001 (특정 유저의 장바구니 리스트 조회) 테스트")
	void readOrderCartListTest() throws Exception {
		// Given
		Long userSeq = 1L;
		Pageable pageable = PageRequest.of(0, 10);

		// When, Then
		mockMvc.perform(get("/api/cart/user", userSeq)
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk());

		verify(orderCartService).readOrderCartList(userSeq, pageable);
	}

	@Test
	@DisplayName("CART-002 (특정 유저의 장바구니에 상품 담기) 테스트")
	void createOrderCartTest() throws Exception {
		// Given
		Long userSeq = 1L;
		OrderCartCreateRequest request = OrderCartCreateRequestFactory.createOrderCartCreateRequest();

		// When, Then
		mockMvc.perform(post("/api/cart/user", userSeq)
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
			.andExpect(status().isOk());

		verify(orderCartService).createOrderCart(userSeq, request);
	}

	@Test
	@DisplayName("CART-003 (특정 유저의 장바구니에서 상품 삭제) 테스트")
	void deleteOrderCartTest() throws Exception {
		// Given
		OrderCartDeleteRequest request = OrderCartDeleteRequestFactory.createOrderCartDeleteRequest();

		// When, Then
		mockMvc.perform(delete("/api/cart")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(request)))
			.andExpect(status().isOk());

		verify(orderCartService).deleteOrderCart(request);
	}
}
