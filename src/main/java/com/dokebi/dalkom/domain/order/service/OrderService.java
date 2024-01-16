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

	// db에서 빼올값
	// dto->entity
	// db 에저장

	// 장바구니 전체 주문
	// public static OrderDetail createOrderItem(int itemId, User user, CartItem cartItem, SaleItem saleItem) {
	// 	OrderDetail orderDetail = new OrderDetail();
	// 	orderDetail.setItemId(itemId);
	// 	orderDetail.setUser(user);
	// 	orderDetail.setItemName(cartItem.getItem().getName());
	// 	orderDetail.setItemPrice(cartItem.getItem().getPrice());
	// 	orderDetail.setItemCount(cartItem.getCount());
	// 	orderDetail.setItemTotalPrice(cartItem.getItem().getPrice()*cartItem.getCount());
	// 	orderDetail.setSaleItem(saleItem);
	// 	return orderDetail;
	// }

	// 상품 개별 주문
	// public static OrderDetail createOrderItem(User user, Item item, int count, Order order, Product product) {
	// 	 OrderDetail orderDetail = new OrderDetail();
	// 	 orderDetail.setOrder(order);
	// 	 orderDetail.setUser(user);
	//
	// 	return orderDetail;
	// }
	//
	// public static Order createOrder(User user,List<OrderDetail> orderDetailList){
	// 	Order order=new Order();
	// 	order.setUser(user);
	// 	for (OrderDetail orderDetail : orderDetailList) {
	// 		order.addOrderDetail(orderDetail);
	//
	// 	}
	// 	order.setCreateDate(order.createDate);
	// 	return order;
	// }
	//
	// public static Order createOrder(User user) {
	// 	Order order = new Order();
	// 	order.setUser(user);
	// 	order.setCreateDate(order.createDate);
	// 	return order;
	// }

	//주문 페이지 조회
	// public Order readOrderPageByProductSeq(Long productSeq){
	// 	List<OrderPageDto>
	//
	// }

	//주문 상품 조회
	// public List<OrderPageDetailDto> readProductBySeq(List<OrderPageDetailDto> orders) {
	// 	List<OrderPageDetailDto> result = new ArrayList<OrderPageDetailDto>();
	// 	result = orders.stream().map(ord -> {
	// 			// 사용자가 주문한 상품에 대한 정보 조회
	// 			ReadProductDetailResponse productInfo = productService.readProduct(ord.getProductSeq());
	//
	// 			// OrderPageItemDTO로 변환
	// 			OrderPageDetailDto orderPageItemDTO = new OrderPageDetailDto();
	// 			orderPageItemDTO.set(productInfo.getInfo());
	// 			OrderPageDetailDto.
	// 			OrderPageDetailDto.
	// 			return orderPageItemDTO;
	// 		})
	// 		.collect(Collectors.toList());
	// }

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


