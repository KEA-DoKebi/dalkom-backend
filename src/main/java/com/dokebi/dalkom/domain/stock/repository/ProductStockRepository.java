package com.dokebi.dalkom.domain.stock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dokebi.dalkom.domain.stock.entity.ProductStock;

import io.lettuce.core.dynamic.annotation.Param;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
	ProductStock findByPrdtStockSeq(Long productSeq);

	@Query("SELECT ps FROM ProductStock ps "
		+ "WHERE ps.product.productSeq = :productSeq AND ps.productOption.prdtOptionSeq = :prdtOptionSeq")
	Optional<ProductStock> findPrdtStockByOptionSeq(@Param("productSeq") Long productSeq,
		@Param("prdtOptionSeq") Long prdtOptionSeq);

}
