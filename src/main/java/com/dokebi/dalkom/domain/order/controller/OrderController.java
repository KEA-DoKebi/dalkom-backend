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
import com.dokebi.dalkom.domain.order.dto.OrderDirectCreateRequest;
import com.dokebi.dalkom.domain.order.dto.OrderPageDto;
import com.dokebi.dalkom.domain.order.dto.OrderStateUpdateRequest;
import com.dokebi.dalkom.domain.order.service.OrderDetailService;
import com.dokebi.dalkom.domain.order.service.OrderService;
import com.dokebi.dalkom.domain.user.config.LoginUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {
	private final OrderService orderService;
	private final OrderDetailService orderDetailService;

	// ORDER-001 (사용자별 전체 주문 조회)
	@GetMapping("/api/order/user")
	@ResponseStatus(HttpStatus.OK)
	public Response readOrdersByUserSeq(@LoginUser Long userSeq, Pageable pageable) {
		return Response.success(orderService.readOrderByUserSeq(userSeq, pageable));
	}

	// ORDER-002 (주문 확인하기)
	@PostMapping("/api/order/orderListPage")
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
	public Response createOrder(@LoginUser Long userSeq, @Valid @RequestBody OrderCreateRequest request) {
		return Response.success(orderService.createOrder(userSeq, request));
	}

	// ORDER-006 (특정 주문 상태 수정)
	@PutMapping("/api/order/{orderSeq}/state")
	@ResponseStatus(HttpStatus.OK)
	public Response updateOrderState(@PathVariable("orderSeq") Long orderSeq,
		@Valid @RequestBody OrderStateUpdateRequest request) {
		orderService.updateOrderState(orderSeq, request);
		return Response.success();
	}

	// ORDER-007 (사용자 주문 검색) 구현 안하기로 함

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

	// ORDER-010 (결제 비밀번호 인증)
	@PostMapping("/api/order/authorize")
	@ResponseStatus(HttpStatus.OK)
	public Response authorizeOrderByPassword(@LoginUser Long userSeq,
		@Valid @RequestBody AuthorizeOrderRequest request) {
		orderService.authorizeOrderByPassword(userSeq, request);
		return Response.success();
	}

	// ORDER-011 (리뷰용 단일 주문상세 조회)
	@GetMapping("/api/order/detail/{orderDetailSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readOrderDetailByOrderDetailSeq(@PathVariable Long orderDetailSeq) {
		return Response.success(orderDetailService.readOrderDetailSimpleResponseByOrderDetailSeq(orderDetailSeq));
	}

	// ORDER-012 (취소/환불 목록 조회)
	@GetMapping("/api/order/cancelrefundlist")
	@ResponseStatus(HttpStatus.OK)
	public Response readOrderCancelListByUserSeq(@LoginUser Long userSeq, Pageable pageable) {
		return Response.success(orderService.readOrderCancelListByUserSeq(userSeq, pageable));
	}

	// ORDER-013 (관리자 주문 목록 검색)
	@GetMapping("/api/order/admin/search")
	@ResponseStatus(HttpStatus.OK)
	public Response readOrderListByAdminSearch(@RequestParam(required = false) String receiverName,
		@RequestParam(required = false) String name, Pageable pageable) {
		return Response.success(orderService.readOrderListByAdminSearch(receiverName, name, pageable));
	}

	// ORDER-014 (직접결제 하기)
	@PostMapping("/api/order/direct")
	@ResponseStatus(HttpStatus.OK)
	public Response createDirectOrder(@LoginUser Long userSeq, @Valid @RequestBody OrderDirectCreateRequest request) {
		return Response.success(orderService.createDirectOrder(userSeq, request));
	}
}
