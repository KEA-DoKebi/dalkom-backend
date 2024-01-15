package com.dokebi.dalkom.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dokebi.dalkom.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	//상품정보 조회
}
