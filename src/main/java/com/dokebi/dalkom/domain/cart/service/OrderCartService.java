package com.dokebi.dalkom.domain.cart.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.cart.dto.OrderCartReadResponse;
import com.dokebi.dalkom.domain.cart.repository.OrderCartRepository;
import com.dokebi.dalkom.domain.product.repository.ProductRepository;
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
}
