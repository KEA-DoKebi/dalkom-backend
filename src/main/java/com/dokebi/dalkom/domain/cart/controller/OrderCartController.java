package com.dokebi.dalkom.domain.cart.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.cart.dto.OrderCartCreateRequest;
import com.dokebi.dalkom.domain.cart.dto.OrderCartDeleteRequest;
import com.dokebi.dalkom.domain.cart.service.OrderCartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderCartController {

	private final OrderCartService orderCartService;

	// CART-001 (특정 유저의 장바구니 조회)
	@GetMapping("api/cart/user/{userSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readOrderCart(@PathVariable Long userSeq) {
		return Response.success(orderCartService.readOrderCartList(userSeq));
	}

	// CART-002 (특정 유저의 장바구니에 상품 담기)
	@PostMapping("api/cart/user/{userSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response createOrderCart(@PathVariable Long userSeq, @Valid @RequestBody OrderCartCreateRequest request) {
		orderCartService.createOrderCart(userSeq, request);
		return Response.success();
	}

	// CART-003 (특정 유저의 장바구니에서 상품 삭제)
	@DeleteMapping("api/cart")
	@ResponseStatus(HttpStatus.OK)
	public Response deleteOrderCart(@Valid @RequestBody OrderCartDeleteRequest request) {
		orderCartService.deleteOrderCart(request);
		return Response.success();
	}
}
