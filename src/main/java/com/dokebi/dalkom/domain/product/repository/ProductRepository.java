package com.dokebi.dalkom.domain.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse;
import com.dokebi.dalkom.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Product findByProductSeq(Long productSeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse(p.productSeq, "
		+ "p.name, p.price, p.state, p.imageUrl, p.company, ps.amount) FROM Product p "
		+ "INNER JOIN ProductStock ps"
		+ " ON p.productSeq = ps.product.productSeq AND p.category.categorySeq = :categorySeq")
	List<ProductByCategoryResponse> getProductsByCategory(@Param("categorySeq") Long categorySeq);
}
