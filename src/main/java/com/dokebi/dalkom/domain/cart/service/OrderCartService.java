package com.dokebi.dalkom.domain.cart.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.cart.dto.OrderCartCreateRequest;
import com.dokebi.dalkom.domain.cart.dto.OrderCartDeleteRequest;
import com.dokebi.dalkom.domain.cart.dto.OrderCartReadResponse;
import com.dokebi.dalkom.domain.cart.entity.OrderCart;
import com.dokebi.dalkom.domain.cart.exception.OrderCartEmptyResultDataAccessException;
import com.dokebi.dalkom.domain.cart.repository.OrderCartRepository;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.service.ProductService;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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

	public List<OrderCartReadResponse> readOrderCartList(Long userSeq) {
		return orderCartRepository.findOrderCartList(userSeq);
	}

	@Transactional
	public void deleteOrderCart(OrderCartDeleteRequest request) {
		for (Long orderCartSeq : request.getOrderCartSeqList()) {
			if (orderCartRepository.existsById(orderCartSeq)) {
				orderCartRepository.deleteById(orderCartSeq);
			} else {
				throw new OrderCartEmptyResultDataAccessException(1);
			}
		}
	}
}
