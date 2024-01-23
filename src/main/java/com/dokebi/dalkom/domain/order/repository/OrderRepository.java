package com.dokebi.dalkom.domain.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dokebi.dalkom.domain.order.entity.Order;

import io.lettuce.core.dynamic.annotation.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

	// 유저별 주문조회
	@Query("SELECT o FROM Order o WHERE o.user.userSeq = :userSeq")
	Page<Order> findOrderListByUserSeq(@Param("userSeq") Long userSeq, Pageable pageable);

	//주문별 주문조회
	Order findByOrdrSeq(Long orderSeq);

	//전체 주문조회
	Page<Order> findAll(Pageable pageable);
}
