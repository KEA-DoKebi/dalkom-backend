package com.dokebi.dalkom.domain.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.order.dto.OrderDto;
import com.dokebi.dalkom.domain.order.dto.OrderPageDetailDto;
import com.dokebi.dalkom.domain.order.entity.Order;
import com.dokebi.dalkom.domain.order.repository.OrderDetailRepository;
import com.dokebi.dalkom.domain.order.repository.OrderRepository;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.service.ProductService;
import com.dokebi.dalkom.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
	private final OrderRepository orderRepository;
	private final ProductService productService;

	// 주문서 내역 조회
	public List<OrderPageDetailDto> readProductBySeq(List<OrderPageDetailDto> orders) {
		List<OrderPageDetailDto> result = new ArrayList<>();
		orders.forEach(ord -> {
			// 사용자가 주문한 상품에 대한 정보 조회
			ReadProductDetailResponse productInfo = productService.readProduct(ord.getProductSeq());

			// OrderPageDetailDto로 변환
			OrderPageDetailDto orderPageDetailDTO = new OrderPageDetailDto();
			// db에서 받은 값
			orderPageDetailDTO.setProductSeq(ord.getProductSeq());
			orderPageDetailDTO.setProductName(productInfo.getName());
			orderPageDetailDTO.setProductOptionSeq(ord.getProductSeq()); // 선택한 옵션 받아오기
			orderPageDetailDTO.setProductPrice(productInfo.getPrice());
			orderPageDetailDTO.setProductAmount(ord.getProductAmount()); // 개수 받아오기
			orderPageDetailDTO.setTotalPrice(productInfo.getPrice()*ord.getProductAmount());
			result.add(orderPageDetailDTO);
		});
		return result;
	}

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


