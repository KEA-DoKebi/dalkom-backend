package com.dokebi.dalkom.domain.stock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.stock.dto.StockListDto;
import com.dokebi.dalkom.domain.stock.entity.ProductStock;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

	@Query("SELECT ps FROM ProductStock ps "
		+ "WHERE ps.product.productSeq = :productSeq "
		+ "AND ps.productOption.prdtOptionSeq = :prdtOptionSeq")
	Optional<ProductStock> findPrdtStockByPrdtSeqAndPrdtOptionSeq(@Param("productSeq") Long productSeq,
		@Param("prdtOptionSeq") Long prdtOptionSeq);

	@Query(
		"SELECT NEW com.dokebi.dalkom.domain.stock.dto.StockListDto(ps.prdtStockSeq, po.prdtOptionSeq, po.detail, ps.amount) "
			+ "FROM ProductStock ps "
			+ "INNER JOIN ProductOption po "
			+ "ON ps.productOption.prdtOptionSeq = po.prdtOptionSeq "
			+ "WHERE ps.product.productSeq = :productSeq ")
	List<StockListDto> findStockListDtoByProductSeq(@Param("productSeq") Long productSeq);

	@Query("SELECT ps FROM ProductStock ps "
		+ "WHERE ps.product.productSeq "
		+ "= (SELECT ps.product.productSeq FROM ProductStock ps "
		+ "WHERE ps.prdtStockSeq = :productStockSeq)")
	List<ProductStock> findProductStockListByStockSeq(@Param("productStockSeq") Long productStockSeq);
}
