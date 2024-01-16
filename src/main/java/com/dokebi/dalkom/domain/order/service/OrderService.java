package com.dokebi.dalkom.domain.order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.order.dto.OrderDto;
import com.dokebi.dalkom.domain.order.entity.Order;
import com.dokebi.dalkom.domain.order.repository.OrderDetailRepository;
import com.dokebi.dalkom.domain.order.repository.OrderRepository;
import com.dokebi.dalkom.domain.product.service.ProductService;
import com.dokebi.dalkom.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final ProductService productService;
	private final UserRepository userRepository;
	private final OrderDetailRepository orderDetailRepository;



	//사용자별 상품 조회
	public List<OrderDto> readOrderByUserSeq(Long userSeq) {
		List<Order> orders = orderRepository.findByUser_UserSeq(userSeq);

		return orders.stream()
			.map(order ->
				new OrderDto(order.getOrdrSeq(),
					order.getReceiverName(),
					order.getReceiverAddress(),
					order.getReceiverMobileNum(),
					order.getReceiverMemo(),
					order.getTotalPrice()))
			.collect(Collectors.toList());

	}

	//주문별 주문 조회
	public OrderDto readOrderByOrderSeq(Long orderSeq) {
		Order order = orderRepository.findByOrdrSeq(orderSeq);
		return new OrderDto(order.getOrdrSeq(),
			order.getReceiverName(),
			order.getReceiverAddress(),
			order.getReceiverMobileNum(),
			order.getReceiverMemo(),
			order.getTotalPrice());
	}
	//주문 전체 조회

	public List<OrderDto> readOrderByAll() {
		List<Order> orders = orderRepository.findAll();
		return orders.stream()
			.map(order ->
				new OrderDto(order.getOrdrSeq(),
					order.getReceiverName(),
					order.getReceiverAddress(),
					order.getReceiverMobileNum(),
					order.getReceiverMemo(),
					order.getTotalPrice()))
			.collect(Collectors.toList());
	}
}


