package com.dokebi.dalkom.domain.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.order.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	Optional<OrderDetail> findOrderDetailByOrdrDetailSeq(Long ordrDetailSeq);

	@Query("SELECT od FROM OrderDetail od "
		+ "WHERE od.order.ordrSeq =:orderSeq")
	List<OrderDetail> findOrderDetailListByOrderSeq(@Param("orderSeq") Long orderSeq);
}
