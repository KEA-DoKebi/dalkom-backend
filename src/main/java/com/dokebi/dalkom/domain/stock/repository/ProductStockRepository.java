package com.dokebi.dalkom.domain.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dokebi.dalkom.domain.stock.entity.ProductStock;

import io.lettuce.core.dynamic.annotation.Param;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
	ProductStock findByPrdtStockSeq(Long stockSeq);

	@Query("SELECT ps FROM ProductStock ps "
		+ "WHERE ps.product.productSeq = :productSeq AND ps.productOption.prdtOptionSeq = :prdtOptionSeq")
	ProductStock findPrdtStockByOptionSeq(@Param("productSeq") Long productSeq,
		@Param("prdtOptionSeq") Long prdtOptionSeq);
}
