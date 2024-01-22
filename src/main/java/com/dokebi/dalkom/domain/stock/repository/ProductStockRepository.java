package com.dokebi.dalkom.domain.stock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.stock.entity.ProductStock;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

	@Query("SELECT ps "
		+ "FROM ProductStock ps "
		+ "WHERE ps.product.productSeq = :productSeq "
		+ "AND ps.productOption.prdtOptionSeq = :prdtOptionSeq")
	Optional<ProductStock> findPrdtStockByPrdtSeqAndPrdtOptionSeq(
		@Param("productSeq") Long productSeq,
		@Param("prdtOptionSeq") Long prdtOptionSeq);

}
