package com.dokebi.dalkom.domain.order.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.order.dto.OrderAdminReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderUserReadResponse;
import com.dokebi.dalkom.domain.order.dto.ReceiverDetailDto;
import com.dokebi.dalkom.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	// 유저별 주문조회
	@Query("SELECT NEW com.dokebi.dalkom.domain.order.dto.OrderUserReadResponse("
		+ "o.ordrSeq, max(od.product.name), COUNT(*), o.totalPrice, o.orderState, o.createdAt) "
		+ "FROM Order o "
		+ "JOIN o.orderDetailList od "
		+ "WHERE o.user.userSeq = :userSeq "
		+ "GROUP BY o.ordrSeq "
		+ "ORDER BY o.createdAt DESC ")
	Page<OrderUserReadResponse> findOrderListByUserSeq(@Param("userSeq") Long userSeq, Pageable pageable);

	// 특정 주문 상세조회
	@Query("SELECT NEW com.dokebi.dalkom.domain.order.dto.ReceiverDetailDto( "
		+ "o.receiverName, o.receiverMobileNum, o.receiverAddress, o.receiverMemo) "
		+ "FROM Order o "
		+ "WHERE o.ordrSeq = :ordrSeq ")
	Optional<ReceiverDetailDto> findReceiverDetailDtoByOrdrSeq(@Param("ordrSeq") Long ordrSeq);

	// 관리자 전체 주문조회
	@Query("SELECT new com.dokebi.dalkom.domain.order.dto.OrderAdminReadResponse("
		+ "o.ordrSeq, o.createdAt, COUNT(*), o.user.name ,o.receiverName,o.totalPrice, o.orderState) FROM Order o "
		+ "JOIN o.orderDetailList od "
		+ "GROUP BY o.ordrSeq "
		+ "ORDER BY o.createdAt DESC ")
	Page<OrderAdminReadResponse> findAllOrderList(Pageable pageable);

	//사용자 주문조회 검색
	@Query("SELECT new com.dokebi.dalkom.domain.order.dto.OrderUserReadResponse( "
		+ "o.ordrSeq, od.product.name, COUNT(*), o.totalPrice, o.orderState, o.createdAt) "
		+ "FROM Order o "
		+ "JOIN o.orderDetailList od "
		+ "WHERE o.receiverName LIKE CONCAT('%', :receiverName, '%')"
		+ "GROUP BY o.ordrSeq, od.product.productSeq "
		+ "ORDER BY o.createdAt DESC ")
	Page<OrderUserReadResponse> findAllOrderListByReceiverName(@Param("receiverName") String receiverName,
		Pageable pageable);

	//관리자 주문조회 검색 이름
	@Query("SELECT new com.dokebi.dalkom.domain.order.dto.OrderAdminReadResponse( "
		+ "o.ordrSeq, o.createdAt, COUNT(*), o.user.name ,o.receiverName,o.totalPrice, o.orderState) "
		+ "FROM Order o "
		+ "JOIN o.orderDetailList od "
		+ "WHERE o.receiverName LIKE CONCAT('%', :receiverName, '%')"
		+ "GROUP BY o.ordrSeq, od.product.productSeq "
		+ "ORDER BY o.createdAt DESC ")
	Page<OrderAdminReadResponse> findOrderListByAdminWithReceiverName(@Param("receiverName") String receiverName,
		Pageable pageable);

	//관리자 주문조회 검색 수령인
	@Query("SELECT new com.dokebi.dalkom.domain.order.dto.OrderAdminReadResponse( "
		+ "o.ordrSeq, o.createdAt, COUNT(*), o.user.name ,o.receiverName,o.totalPrice, o.orderState) "
		+ "FROM Order o "
		+ "JOIN o.orderDetailList od "
		+ "WHERE o.user.name LIKE CONCAT('%', :name, '%')"
		+ "GROUP BY o.ordrSeq, od.product.productSeq "
		+ "ORDER BY o.createdAt DESC ")
	Page<OrderAdminReadResponse> findOrderListByAdminWithName(@Param("name") String name,
		Pageable pageable);

	Optional<Order> findOrderByOrdrSeq(Long orderSeq);

	@Query("SELECT o FROM Order o "
		+ "WHERE o.user.userSeq = :userSeq "
		+ "AND (o.orderState = '21' OR o.orderState = '31' OR o.orderState = '32' OR o.orderState = '33') "
		+ "ORDER BY o.createdAt DESC ")
	Page<Order> findCancelRefundListByUserSeq(@Param("userSeq") Long userSeq, Pageable pageable);
}
