package com.dokebi.dalkom.domain.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.cart.dto.OrderCartReadResponse;
import com.dokebi.dalkom.domain.cart.entity.OrderCart;

public interface OrderCartRepository extends JpaRepository<OrderCart, Long> {

	@Query("SELECT NEW com.dokebi.dalkom.domain.cart.dto.OrderCartReadResponse" +
		"(oc.ordrCartSeq, oc.product.productSeq, oc.product.name," +
		"oc.prdtOptionSeq, po.name, oc.product.imageUrl," +
		"oc.product.price, oc.amount) " +
		"FROM OrderCart oc " +
		"JOIN ProductOption po ON oc.prdtOptionSeq = po.prdtOptionSeq " +
		"WHERE oc.user.userSeq = :userSeq")
	List<OrderCartReadResponse> readOrderCartList(@Param("userSeq") Long userSeq);
}
