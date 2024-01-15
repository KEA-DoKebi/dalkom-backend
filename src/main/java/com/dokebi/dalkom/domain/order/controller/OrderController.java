package com.dokebi.dalkom.domain.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.order.dto.OrderCreateRequestDto;
import com.dokebi.dalkom.domain.order.dto.OrderPageDto;
import com.dokebi.dalkom.domain.order.service.OrderService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
	public void orderPageGet(@PathVariable("userSeq") String userSeq, OrderPageDto opd){
		System.out.println("userSeq" + userSeq);
		System.out.println("orders"+opd.getOrders());
	}


}
