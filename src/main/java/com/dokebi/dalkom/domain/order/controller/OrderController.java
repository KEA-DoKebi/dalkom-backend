package com.dokebi.dalkom.domain.order.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.order.dto.AuthorizeOrderRequest;
import com.dokebi.dalkom.domain.order.dto.OrderCreateRequest;
import com.dokebi.dalkom.domain.order.dto.OrderPageDto;
import com.dokebi.dalkom.domain.order.dto.OrderStateUpdateRequest;
import com.dokebi.dalkom.domain.order.service.OrderService;
import com.dokebi.dalkom.domain.user.config.LoginUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {
	private final OrderService orderService;

	// ORDER-001 (사용자별 전체 주문 조회)
	@GetMapping("/api/order/user")
	@ResponseStatus(HttpStatus.OK)
	public Response readOrdersByUserSeq(@LoginUser Long userSeq, Pageable pageable) {
		return Response.success(orderService.readOrderByUserSeq(userSeq, pageable));
	}

	// ORDER-002 (주문 확인하기)
	@GetMapping("/api/order/orderListPage")
	@ResponseStatus(HttpStatus.OK)
	public Response readOrderPageByProductSeq(@Valid @RequestBody OrderPageDto orderPageDto) {
		return Response.success(orderService.readProductDetail(orderPageDto.getOrderList()));
	}

	// ORDER-003 (특정 주문 조회)
	@GetMapping("/api/order/{orderSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readOrderByOrderSeq(@PathVariable("orderSeq") Long orderSeq) {
		return Response.success(orderService.readOrderByOrderSeq(orderSeq));
	}

	// ORDER-004 (관리자 전체 주문 조회)
	@GetMapping("/api/order")
	@ResponseStatus(HttpStatus.OK)
	public Response readOrders(Pageable pageable) {
		return Response.success(orderService.readOrderByAll(pageable));
	}

	// ORDER-005 (결제 하기)
	@PostMapping("/api/order")
	@ResponseStatus(HttpStatus.OK)
	public Response createOrder(@Valid @RequestBody OrderCreateRequest request) {
		orderService.createOrder(request);
		return Response.success();
	}

	//  ORDER-006 (특정 주문 상태 수정)
	@PutMapping("/api/order/{orderSeq}/state")
	@ResponseStatus(HttpStatus.OK)
	public Response updateOrderState(@PathVariable("orderSeq") Long orderSeq,
		@Valid @RequestBody OrderStateUpdateRequest request) {
		orderService.updateOrderState(orderSeq, request);
		return Response.success();
	}

	// ORDER-007 (주문 검색)
	@GetMapping("/api/order/search")
	@ResponseStatus(HttpStatus.OK)
	public Response readOrderListBySearch(@RequestParam String receiverName, Pageable pageable) {
		return Response.success(orderService.readOrderListBySearch(receiverName, pageable));
	}

	// ORDER-008 (주문 취소)
	@DeleteMapping("/api/order/cancel/{orderSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response cancelOrderByOrderSeq(@PathVariable Long orderSeq) {
		orderService.deleteOrderByOrderSeq(orderSeq);
		return Response.success();
	}

	// ORDER-009 (환불 확인)
	@DeleteMapping("/api/order/refund/{orderSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response refundOrderByOrderSeq(@PathVariable Long orderSeq) {
		orderService.confirmRefundByOrderSeq(orderSeq);
		return Response.success();
	}

	//ORDER-010 (결제 비밀번호 인증)
	@GetMapping("/api/order/authorize")
	@ResponseStatus(HttpStatus.OK)
	public Response authorizeOrderByPassword(@LoginUser Long userSeq, @RequestParam AuthorizeOrderRequest request) {
		orderService.authorizeOrderByPassword(userSeq, request);
		return Response.success();
	}
}
