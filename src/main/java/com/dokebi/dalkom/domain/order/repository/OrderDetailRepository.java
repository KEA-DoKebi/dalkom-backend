package com.dokebi.dalkom.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dokebi.dalkom.domain.order.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	OrderDetail findByOrdrDetailSeq(Long ordrDetailSeq);
}
