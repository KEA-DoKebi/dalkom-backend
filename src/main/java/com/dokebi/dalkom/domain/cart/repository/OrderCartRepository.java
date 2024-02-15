package com.dokebi.dalkom.domain.cart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.cart.dto.OrderCartReadResponse;
import com.dokebi.dalkom.domain.cart.entity.OrderCart;

public interface OrderCartRepository extends JpaRepository<OrderCart, Long> {
	@Query("SELECT NEW com.dokebi.dalkom.domain.cart.dto.OrderCartReadResponse( "
		+ "oc.ordrCartSeq, oc.product.productSeq, oc.prdtOptionSeq, "
		+ "oc.product.name, ps.productOption.detail, oc.product.imageUrl, "
		+ "oc.product.price, oc.amount, ps.amount) "
		+ "FROM OrderCart oc "
		+ "JOIN ProductStock ps "
		+ "ON oc.prdtOptionSeq = ps.productOption.prdtOptionSeq "
		+ "AND oc.product.productSeq = ps.product.productSeq "
		+ "WHERE oc.user.userSeq = :userSeq")
	Page<OrderCartReadResponse> findOrderCartList(@Param("userSeq") Long userSeq, Pageable pageable);
}
