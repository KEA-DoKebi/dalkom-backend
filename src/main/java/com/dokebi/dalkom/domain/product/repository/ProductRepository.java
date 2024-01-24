package com.dokebi.dalkom.domain.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.product.dto.OptionListDTO;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryDetailResponse;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailDTO;
import com.dokebi.dalkom.domain.product.dto.ReadProductResponse;
import com.dokebi.dalkom.domain.product.dto.StockListDTO;
import com.dokebi.dalkom.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findByProductSeq(Long productSeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.ProductByCategoryDetailResponse" +
		"(p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company, AVG(r.rating), COUNT(r) ) " +
		"FROM Product p " +
		"INNER JOIN Review r " +
		"ON p.productSeq = r.orderDetail.product.productSeq AND p.category.categorySeq = :categorySeq " +
		"GROUP BY p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company")
	Page<ProductByCategoryDetailResponse> findProductListByCategoryDetail(@Param("categorySeq") Long categorySeq,
		Pageable pageable);

	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse("
		+ "p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company, "
		+ "AVG(r.rating), COUNT(r.reviewSeq)) "
		+ "FROM Product p "
		+ "JOIN p.category c "
		+ "LEFT JOIN OrderDetail od ON p.productSeq = od.product.productSeq "
		+ "LEFT JOIN Review r ON od.ordrDetailSeq = r.orderDetail.ordrDetailSeq "
		+ "WHERE c.parentSeq = :categorySeq "
		+ "GROUP BY p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company")
	Page<ProductByCategoryResponse> findProductListByCategory(@Param("categorySeq") Long categorySeq,
		Pageable pageable);

	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.ReadProductDetailDTO(p.category.categorySeq, "
		+ "p.name, p.price, p.info, p.imageUrl, p.company) "
		+ "FROM Product p "
		+ "WHERE p.productSeq = :productSeq ")
	ReadProductDetailDTO findProductDetailBySeq(@Param("productSeq") Long productSeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.StockListDTO(ps.prdtStockSeq, ps.amount) "
		+ "FROM ProductStock ps "
		+ "WHERE ps.product.productSeq = :productSeq ")
	List<StockListDTO> findStockListBySeq(@Param("productSeq") Long productSeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.OptionListDTO( "
		+ "po.prdtOptionSeq, po.detail) "
		+ "FROM ProductOption po "
		+ "INNER JOIN ProductStock ps "
		+ "ON po.prdtOptionSeq = ps.productOption.prdtOptionSeq "
		+ "AND ps.product.productSeq = :productSeq ")
	List<OptionListDTO> findOptionListBySeq(@Param("productSeq") Long productSeq);

	@Query("SELECT pi.imageUrl "
		+ "FROM ProductImage pi "
		+ "WHERE pi.product.productSeq = :productSeq ")
	List<String> findProductImageBySeq(@Param("productSeq") Long productSeq);

	@Query(value = "SELECT NEW com.dokebi.dalkom.domain.product.dto.ReadProductResponse( "
		+ "p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company, ps.productOption.detail, ps.amount)"
		+ "FROM Product p "
		+ "INNER JOIN ProductStock ps "
		+ "ON p.productSeq = ps.product.productSeq "
		+ "ORDER BY p.productSeq ASC, ps.productOption.prdtOptionSeq ASC ",
		countQuery = "SELECT COUNT(p) FROM Product p ")
	Page<ReadProductResponse> findProductList(Pageable pageable);
}
