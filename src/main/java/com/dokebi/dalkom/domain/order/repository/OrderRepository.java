package com.dokebi.dalkom.domain.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dokebi.dalkom.domain.order.dto.OrderDetailReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderReadResponse;
import com.dokebi.dalkom.domain.order.entity.Order;

import io.lettuce.core.dynamic.annotation.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

	// 유저별 주문조회
	@Query("SELECT new com.dokebi.dalkom.domain.order.dto.OrderReadResponse("
		+ "o.ordrSeq, o.totalPrice,o.orderState,o.createdAt) "
		+ "FROM Order o WHERE o.user.userSeq = :userSeq")
	Page<OrderReadResponse> findOrderListByUserSeq(@Param("userSeq") Long userSeq, Pageable pageable);

	// 특정 주문 상세조회
	@Query("SELECT new com.dokebi.dalkom.domain.order.dto.OrderDetailReadResponse("
		+ "o.product.name, o.order.createdAt, o.order.ordrSeq, o.amount, o.amount*o.price, o.order.orderState"
		+ ",o.order.receiverName,o.order.receiverMobileNum,o.order.receiverAddress,o.order.receiverMemo) FROM OrderDetail o WHERE o.order.ordrSeq = :orderSeq")
	Page<OrderDetailReadResponse> findByOrdrSeq(Long orderSeq, Pageable pageable);

	// 전체 주문조회
	@Query("SELECT new com.dokebi.dalkom.domain.order.dto.OrderReadResponse("
		+ "o.ordrSeq,o.totalPrice,o.orderState,o.createdAt) FROM Order o")
	Page<OrderReadResponse> findAllOrders(Pageable pageable);

	//전체 주문조회 검색
	@Query("SELECT new com.dokebi.dalkom.domain.order.dto.OrderReadResponse("
		+ "o.ordrSeq, o.totalPrice,o.orderState,o.createdAt) "
		+ "FROM Order o WHERE o.receiverName LIKE CONCAT('%', :receiverName, '%')")
	Page<OrderReadResponse> findAllOrderListByReceiverName(@Param("receiverName") String receiverName,
		Pageable pageable);
}
