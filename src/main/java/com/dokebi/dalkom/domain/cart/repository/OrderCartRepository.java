package com.dokebi.dalkom.domain.cart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.cart.dto.OrderCartReadResponse;
import com.dokebi.dalkom.domain.cart.entity.OrderCart;

public interface OrderCartRepository extends JpaRepository<OrderCart, Long> {

	@Query("SELECT NEW com.dokebi.dalkom.domain.cart.dto.OrderCartReadResponse" +
		"(oc.ordrCartSeq, oc.product.productSeq, oc.prdtOptionSeq, " +
		"oc.product.name, po.name, oc.product.imageUrl," +
		"oc.product.price, oc.amount) " +
		"FROM OrderCart oc " +
		"JOIN ProductOption po ON oc.prdtOptionSeq = po.prdtOptionSeq " +
		"WHERE oc.user.userSeq = :userSeq")
	Page<OrderCartReadResponse> findOrderCartList(@Param("userSeq") Long userSeq, Pageable pageable);
}
