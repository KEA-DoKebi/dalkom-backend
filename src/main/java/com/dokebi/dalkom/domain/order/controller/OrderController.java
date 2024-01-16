package com.dokebi.dalkom.domain.order.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.order.dto.OrderPageDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderPageDto;
import com.dokebi.dalkom.domain.order.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {
	private final OrderService orderService;

	// 주문 페이지 이동
	@GetMapping("/api/ordersPage/{userSeq}")
	public Response getOrderPageByProductSeq(@PathVariable("userSeq") Long userSeq,
		@RequestBody OrderPageDto orderPageDto) {

		log.info("userSeq: " + userSeq);
		log.info("orders : " + orderPageDto.getOrders());

		return Response.success(orderService.readProductBySeq(orderPageDto.getOrders()));

	}

	// 사용자별 주문 조회
	@GetMapping("/api/orders/users/{userSeq}")
	public Response getOrdersByUserSeq(@PathVariable("userSeq") Long userSeq) {

		return Response.success(orderService.readOrderByUserSeq(userSeq));
	}

	// 특정 주문 조회
	@GetMapping("/api/orders/{orderSeq}")
	public Response getOrderByOrderSeq(@PathVariable("orderSeq") Long orderSeq) {
		return Response.success(orderService.readOrderByOrderSeq(orderSeq));
	}

	// 전체 주문 조회
	@GetMapping("/api/orders")
	public Response getOrders() {
		return Response.success(orderService.readOrderByAll());
	}
}
