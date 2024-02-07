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
	// ORDER-001 (사용자별 전체 주문 조회)에서 사용하는 SQL
	@Query("SELECT NEW com.dokebi.dalkom.domain.order.dto.OrderUserReadResponse("
		+ "o.ordrSeq, max(od.product.name), COUNT(*), o.totalPrice, o.orderState, o.createdAt) "
		+ "FROM Order o "
		+ "JOIN o.orderDetailList od "
		+ "WHERE o.user.userSeq = :userSeq "
		+ "GROUP BY o.ordrSeq "
		+ "ORDER BY o.createdAt DESC ")
	Page<OrderUserReadResponse> findOrderListByUserSeq(@Param("userSeq") Long userSeq, Pageable pageable);

	// ORDER-002 (주문 확인하기)는 orderRepository 사용 안함

	// ORDER-003 (특정 주문 조회)에서 사용하는 SQL
	@Query("SELECT NEW com.dokebi.dalkom.domain.order.dto.ReceiverDetailDto( "
		+ "o.receiverName, o.receiverMobileNum, o.receiverAddress, o.receiverMemo) "
		+ "FROM Order o "
		+ "WHERE o.ordrSeq = :ordrSeq ")
	Optional<ReceiverDetailDto> findReceiverDetailDtoByOrdrSeq(@Param("ordrSeq") Long ordrSeq);

	// ORDER-004 (관리자 전체 주문 조회), ORDER-013 (관리자 주문 목록 검색)에서 사용하는 SQL
	@Query("SELECT new com.dokebi.dalkom.domain.order.dto.OrderAdminReadResponse("
		+ "o.ordrSeq, o.createdAt, COUNT(*), o.user.name ,o.receiverName,o.totalPrice, o.orderState) FROM Order o "
		+ "JOIN o.orderDetailList od "
		+ "GROUP BY o.ordrSeq "
		+ "ORDER BY o.createdAt DESC ")
	Page<OrderAdminReadResponse> findAllOrderList(Pageable pageable);

	// ORDER-005 (결제 하기) 에서는 orderRepository.save()를 사용

	// ORDER-006 (특정 주문 상태 수정) 에서는 orderRepository.findById()를 사용

	// ORDER-007 (사용자 주문 검색) 구현 안하기로 함

	// ORDER-008 (주문 취소), ORDER-009 (환불 확인)에서 사용하는 SQL
	Optional<Order> findOrderByOrdrSeq(Long orderSeq);

	// ORDER-010 (결제 비밀번호 인증)는 orderRepository 사용 안함

	// ORDER-011 (리뷰용 단일 주문상세 조회)는 OrderDetailRepository에 구현

	// ORDER-012 (취소/환불 목록 조회)에서 사용하는 SQL
	@Query("SELECT o FROM Order o "
		+ "WHERE o.user.userSeq = :userSeq "
		+ "AND (o.orderState = '21' OR o.orderState = '31' OR o.orderState = '32' OR o.orderState = '33') "
		+ "ORDER BY o.createdAt DESC ")
	Page<Order> findCancelRefundListByUserSeq(@Param("userSeq") Long userSeq, Pageable pageable);

	// ORDER-013 (관리자 주문 목록 검색) - 수령자 이름 에서 사용하는 SQL
	@Query("SELECT new com.dokebi.dalkom.domain.order.dto.OrderAdminReadResponse( "
		+ "o.ordrSeq, o.createdAt, COUNT(*), o.user.name ,o.receiverName,o.totalPrice, o.orderState) "
		+ "FROM Order o JOIN o.orderDetailList od "
		+ "WHERE o.receiverName LIKE CONCAT('%', :receiverName, '%')"
		+ "GROUP BY o.ordrSeq, od.product.productSeq "
		+ "ORDER BY o.createdAt DESC ")
	Page<OrderAdminReadResponse> findOrderListByAdminWithReceiverName(@Param("receiverName") String receiverName,
		Pageable pageable);

	// ORDER-013 (관리자 주문 목록 검색) - 주문자 이름 에서 사용하는 SQL
	@Query("SELECT new com.dokebi.dalkom.domain.order.dto.OrderAdminReadResponse( "
		+ "o.ordrSeq, o.createdAt, COUNT(*), o.user.name ,o.receiverName,o.totalPrice, o.orderState) "
		+ "FROM Order o JOIN o.orderDetailList od "
		+ "WHERE o.user.name LIKE CONCAT('%', :name, '%')"
		+ "GROUP BY o.ordrSeq, od.product.productSeq "
		+ "ORDER BY o.createdAt DESC ")
	Page<OrderAdminReadResponse> findOrderListByAdminWithName(@Param("name") String name,
		Pageable pageable);
}
