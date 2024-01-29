package com.dokebi.dalkom.domain.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dokebi.dalkom.domain.order.dto.OrderAdminReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderDetailReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderUserReadResponse;
import com.dokebi.dalkom.domain.order.entity.Order;

import io.lettuce.core.dynamic.annotation.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

	// 유저별 주문조회
	@Query("SELECT new com.dokebi.dalkom.domain.order.dto.OrderUserReadResponse("
		+ "o.ordrSeq, max(od.product.name), COUNT(*), o.totalPrice, o.orderState, o.createdAt) "
		+ "FROM Order o "
		+ "JOIN o.orderDetailList od "
		+ "WHERE o.user.userSeq = :userSeq "
		+ "GROUP BY o.ordrSeq")
	Page<OrderUserReadResponse> findOrderListByUserSeq(@Param("userSeq") Long userSeq, Pageable pageable);

	// 특정 주문 상세조회
	@Query("SELECT new com.dokebi.dalkom.domain.order.dto.OrderDetailReadResponse("
		+ "o.product.name, o.order.createdAt, o.order.ordrSeq, o.amount, o.amount*o.price, o.order.orderState, "
		+ "o.order.receiverName, o.order.receiverMobileNum, o.order.receiverAddress, o.order.receiverMemo) FROM OrderDetail o WHERE o.order.ordrSeq = :orderSeq")
	List<OrderDetailReadResponse> findOrderDetailByOrdrSeq(Long orderSeq);

	// 전체 주문조회
	@Query("SELECT new com.dokebi.dalkom.domain.order.dto.OrderAdminReadResponse("
		+ "o.ordrSeq, o.createdAt, COUNT(*), o.user.name ,o.receiverName,o.totalPrice, o.orderState) FROM Order o "
		+ "JOIN o.orderDetailList od "
		+ "GROUP BY o.ordrSeq")
	Page<OrderAdminReadResponse> findAllOrderList(Pageable pageable);

	//전체 주문조회 검색
	@Query("SELECT new com.dokebi.dalkom.domain.order.dto.OrderUserReadResponse("
		+ "o.ordrSeq, od.product.name, COUNT(*), o.totalPrice, o.orderState, o.createdAt) "
		+ "FROM Order o "
		+ "JOIN o.orderDetailList od "
		+ "WHERE o.receiverName LIKE CONCAT('%', :receiverName, '%')"
		+ "GROUP BY o.ordrSeq, od.product.productSeq")
	Page<OrderUserReadResponse> findAllOrderListByReceiverName(@Param("receiverName") String receiverName,
		Pageable pageable);

	Optional<Order> findOrderByOrdrSeq(Long orderSeq);
}
