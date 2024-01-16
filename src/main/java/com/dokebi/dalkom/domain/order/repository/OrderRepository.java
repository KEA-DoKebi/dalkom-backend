package com.dokebi.dalkom.domain.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dokebi.dalkom.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	//상품정보 조회

	// 유저별 주문조회
	List<Order> findByUser_UserSeq(Long userSeq);

	//주문별 주문조회
	Order findByOrdrSeq(Long orderSeq);

	//전체 주문조회
	List<Order> findAll();
}
