package com.dokebi.dalkom.domain.order.service;

import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.order.repository.OrderDetailRepository;
import com.dokebi.dalkom.domain.order.repository.OrderRepository;
import com.dokebi.dalkom.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final OrderDetailRepository orderDetailRepository;

	//db에서 빼올값
	//dto->entity
	//db 에저장

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

}


