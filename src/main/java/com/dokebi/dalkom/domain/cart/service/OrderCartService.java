package com.dokebi.dalkom.domain.cart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.cart.dto.OrderCartCreateRequest;
import com.dokebi.dalkom.domain.cart.dto.OrderCartDeleteRequest;
import com.dokebi.dalkom.domain.cart.dto.OrderCartReadResponse;
import com.dokebi.dalkom.domain.cart.entity.OrderCart;
import com.dokebi.dalkom.domain.cart.exception.OrderCartNotFoundException;
import com.dokebi.dalkom.domain.cart.repository.OrderCartRepository;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.service.ProductService;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderCartService {
	private final OrderCartRepository orderCartRepository;
	private final UserService userService;
	private final ProductService productService;

	// CART-001 (특정 유저의 장바구니 리스트 조회)
	public Page<OrderCartReadResponse> readOrderCartList(Long userSeq, Pageable pageable) {
		return orderCartRepository.findOrderCartList(userSeq, pageable);
	}

	// CART-002 (특정 유저의 장바구니에 상품 담기)
	@Transactional
	public void createOrderCart(Long userSeq, OrderCartCreateRequest request) {
		User user = userService.readUserByUserSeq(userSeq);
		Product product = productService.readProductByProductSeq(request.getProductSeq());

		OrderCart orderCart = new OrderCart(user, product, request.getPrdtOptionSeq(), request.getAmount());
		orderCartRepository.save(orderCart);
	}

	// CART-003 (특정 유저의 장바구니에서 상품 삭제)
	@Transactional
	public void deleteOrderCart(OrderCartDeleteRequest request) {
		for (Long orderCartSeq : request.getOrderCartSeqList()) {
			// 테스트 구문에서 existsById 검사를 안하면 EmptyResultDataAccessException이 일어나지가 않아서 이렇게 다시 수정
			if (!orderCartRepository.existsById(orderCartSeq)) {
				throw new OrderCartNotFoundException();
			}
			orderCartRepository.deleteById(orderCartSeq);
		}
	}
}
