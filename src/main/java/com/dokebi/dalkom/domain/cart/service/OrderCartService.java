package com.dokebi.dalkom.domain.cart.service;

import org.springframework.dao.EmptyResultDataAccessException;
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

	@Transactional
	public void createOrderCart(Long userSeq, OrderCartCreateRequest request) {
		User user = userService.readUserByUserSeq(userSeq);
		Product product = productService.readProductByProductSeq(request.getProductSeq());

		OrderCart orderCart = new OrderCart(user, product, request.getPrdtOptionSeq(), request.getAmount());
		orderCartRepository.save(orderCart);
	}

	public Page<OrderCartReadResponse> readOrderCartList(Long userSeq, Pageable pageable) {
		return orderCartRepository.findOrderCartList(userSeq, pageable);
	}

	@Transactional
	public void deleteOrderCart(OrderCartDeleteRequest request) {
		try {
			for (Long orderCartSeq : request.getOrderCartSeqList()) {
				orderCartRepository.deleteById(orderCartSeq);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new OrderCartNotFoundException();
		}
	}
}
