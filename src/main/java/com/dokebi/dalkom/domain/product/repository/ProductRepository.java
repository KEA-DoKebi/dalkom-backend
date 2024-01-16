package com.dokebi.dalkom.domain.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.product.dto.OptionListDTO;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailDTO;
import com.dokebi.dalkom.domain.product.dto.ReadProductResponse;
import com.dokebi.dalkom.domain.product.dto.StockListDTO;
import com.dokebi.dalkom.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findByProductSeq(Long productSeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse" +
		"(p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company, CAST(SUM(ps.amount) AS int)) " +
		"FROM Product p " +
		"INNER JOIN ProductStock ps " +
		"ON p.productSeq = ps.product.productSeq AND p.category.categorySeq = :categorySeq " +
		"GROUP BY p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company")
	List<ProductByCategoryResponse> getProductsByCategory(@Param("categorySeq") Long categorySeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.ReadProductDetailDTO(p.category.categorySeq, "
		+ "p.name, p.price, p.info, p.imageUrl, p.company) FROM Product p "
		+ "WHERE p.productSeq = :productSeq")
	ReadProductDetailDTO getProductDetailBySeq(@Param("productSeq") Long productSeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.StockListDTO(ps.prdtStockSeq, "
		+ "ps.amount) FROM ProductStock ps "
		+ "WHERE ps.product.productSeq = :productSeq")
	List<StockListDTO> getStockListBySeq(@Param("productSeq") Long productSeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.OptionListDTO(po.prdtOptionSeq, "
		+ "po.detail) FROM ProductOption po "
		+ "INNER JOIN ProductStock ps "
		+ "ON po.prdtOptionSeq = ps.productOption.prdtOptionSeq AND ps.product.productSeq = :productSeq")
	List<OptionListDTO> getOptionListBySeq(@Param("productSeq") Long productSeq);

	@Query("SELECT pi.imageUrl FROM ProductImage pi "
		+ "WHERE pi.product.productSeq = :productSeq")
	List<String> getProductImageBySeq(@Param("productSeq") Long productSeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.ReadProductResponse("
		+ "p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company) FROM Product p")
	List<ReadProductResponse> getProductList();
}
