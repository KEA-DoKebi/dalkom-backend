package com.dokebi.dalkom.domain.cart.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.cart.dto.OrderCartCreateRequest;
import com.dokebi.dalkom.domain.cart.dto.OrderCartDeleteRequest;
import com.dokebi.dalkom.domain.cart.dto.OrderCartReadResponse;
import com.dokebi.dalkom.domain.cart.entity.OrderCart;
import com.dokebi.dalkom.domain.cart.repository.OrderCartRepository;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.repository.ProductRepository;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderCartService {

	private final OrderCartRepository orderCartRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	@Transactional
	public List<OrderCartReadResponse> getOrderCartList(Long userSeq) {
		return orderCartRepository.getOrderCartList(userSeq);
	}

	@Transactional
	public void createOrderCart(Long userSeq, OrderCartCreateRequest request) {
		User user = userRepository.findByUserSeq(userSeq);
		Product product = productRepository.findByProductSeq(request.getProductSeq());

		OrderCart orderCart = new OrderCart(user, product, request.getPrdtOptionSeq(), request.getAmount());
		orderCartRepository.save(orderCart);
	}

	@Transactional
	public void deleteOrderCart(OrderCartDeleteRequest request) {
		for (Long orderCartSeq : request.getOrderCartSeqList()) {
			orderCartRepository.deleteById(orderCartSeq);
		}
	}
}
