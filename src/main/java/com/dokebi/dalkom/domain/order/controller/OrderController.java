package com.dokebi.dalkom.domain.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.domain.order.dto.OrderPageDto;
import com.dokebi.dalkom.domain.order.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {
	private final OrderService orderService;

	//직접 주문
	// @PostMapping("/api/order/{productSeq}")
	// @ResponseStatus(HttpStatus.CREATED)
	// public Response createOrder(
	// 	@PathVariable Long productSeq,
	// 	@RequestHeader(value = "userSeq") Long userSeq,
	// 	@RequestBody OrderCreateRequestDto req){
	// 	orderService.createOrder(req,productSeq,userSeq);
	// 	return Response.success();
	// }
	// @PostMapping("/api/order/{orderCartSeq}")

	//장바구니 주문

	//주문 페이지로의 이동
	@GetMapping("/api/order/user/{userSeq}")
	public void orderPageGet(@PathVariable("userSeq") String userSeq, OrderPageDto opd) {
		System.out.println("userSeq" + userSeq);
		System.out.println("orders" + opd.getOrders());
	}
}
