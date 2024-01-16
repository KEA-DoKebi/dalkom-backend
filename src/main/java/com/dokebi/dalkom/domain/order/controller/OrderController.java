package com.dokebi.dalkom.domain.order.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.order.dto.OrderCreateRequest;
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

	// 상세페이지에서 주문하기
	// @PostMapping("/api/order/{productSeq}")
	// public Response createOrder(
	// 	@PathVariable Long productSeq,
	// 	@RequestHeader(value = "userSeq") Long userSeq,
	// 	@RequestBody OrderCreateRequest req){
	// 	orderService.createOrder(req,productSeq,userSeq);
	// 	return Response.success();
	// }

	//주문 페이지 이동
	@GetMapping("/api/ordersPage/{userSeq}")
	public void getOrderPageByProductSeq(@PathVariable("userSeq") Long userSeq,
		@RequestBody OrderPageDto orderPageDto, Model model){
		// @RequestHeader(value = "userSeq") Long userSeq,
		// @RequestBody OrderPageDetailDto orderPageDetailDto){
		System.out.println("userSeq : " + userSeq);
		System.out.println("orders : " + orderPageDto.getOrders());
		// return Response.success(orderService.readOrderPageByProductSeq(productSeq));

	}

	//장바구니 주문

	//사용자별 주문 조회
	@GetMapping("/api/orders/users/{userSeq}")
	public Response getOrdersByUserSeq(@PathVariable("userSeq") Long userSeq) {

		return Response.success(orderService.readOrderByUserSeq(userSeq));
	}

	//특정 주문 조회
	@GetMapping("/api/orders/{orderSeq}")
	public Response getOrderByOrderSeq(@PathVariable("orderSeq") Long orderSeq) {
		return Response.success(orderService.readOrderByOrderSeq(orderSeq));
	}

	//전체 주문 조회
	@GetMapping("/api/orders")
	public Response getOrders() {
		return Response.success(orderService.readOrderByAll());
	}
}
