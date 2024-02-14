package com.dokebi.dalkom.domain.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.order.dto.OrderDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderDetailSimpleResponse;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	// ORDER-001 (사용자별 전체 주문 조회)는 OrderService에서 구현

	// ORDER-002 (주문 확인하기)는 OrderService에서 구현

	// ORDER-003 (특정 주문 조회)에서 사용되는 SQL
	@Query("SELECT NEW com.dokebi.dalkom.domain.order.dto.OrderDetailDto( "
		+ "od.ordrDetailSeq, od.product.name, od.product.imageUrl, od.productOption.prdtOptionSeq, "
		+ "od.productOption.detail, od.order.createdAt, od.order.ordrSeq, od.amount, od.price, od.order.orderState) "
		+ "FROM  OrderDetail od WHERE od.order.ordrSeq =:orderSeq")
	List<OrderDetailDto> findOrderDetailDtoListByOrderSeq(@Param("orderSeq") Long orderSeq);

	// ORDER-004 (관리자 전체 주문 조회)는 OrderService에서 구현

	// ORDER-005 (결제 하기)에서 사용되는 함수는 orderDetailService.save()로 사용

	// ORDER-006 (특정 주문 상태 수정) 에서는 orderRepository.findById()를 사용

	// ORDER-007 (사용자 주문 검색) 구현 안하기로 함

	// ORDER-008 (주문 취소), ORDER-009 (환불 확인)는 OrderService에서 구현

	// ORDER-010 (결제 비밀번호 인증)는 orderDetailRepository 사용 안함

	// ORDER-011 (리뷰용 단일 주문상세 조회)에서 사용되는 SQL
	@Query("SELECT NEW com.dokebi.dalkom.domain.order.dto.OrderDetailSimpleResponse( "
		+ "od.product.name, od.product.imageUrl, od.productOption.prdtOptionSeq, od.productOption.detail) "
		+ "FROM  OrderDetail od WHERE od.ordrDetailSeq =:ordrDetailSeq")
	Optional<OrderDetailSimpleResponse> findOrderDetailSimpleResponseByOrdrDetailSeq(
		@Param("ordrDetailSeq") Long ordrDetailSeq);

	// ORDER-012 (취소/환불 목록 조회)에서 사용되는 SQL
	Optional<OrderDetail> findFirstByOrder_OrdrSeq(Long ordrSeq);

	// ORDER-013 (관리자 주문 목록 검색)는 OrderService에서 구현

	// REVIEW-003 (주문 상품에 대한 리뷰 작성)에서 사용되는 SQL
	Optional<OrderDetail> findOrderDetailByOrdrDetailSeq(Long ordrDetailSeq);
}
