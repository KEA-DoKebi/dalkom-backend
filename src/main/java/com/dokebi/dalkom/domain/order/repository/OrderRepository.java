package com.dokebi.dalkom.domain.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dokebi.dalkom.domain.order.entity.Order;

import io.lettuce.core.dynamic.annotation.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

	// 유저별 주문조회
	@Query("SELECT o FROM Order o WHERE o.user.userSeq = :userSeq")
	List<Order> findOrderListByUserSeq(@Param("userSeq") Long userSeq);

	//주문별 주문조회
	Order findByOrdrSeq(Long orderSeq);

	//전체 주문조회
	List<Order> findAll();
}
