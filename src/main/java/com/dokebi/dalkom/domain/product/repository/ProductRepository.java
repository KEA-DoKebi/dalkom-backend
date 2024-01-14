package com.dokebi.dalkom.domain.product.repository;

import java.util.List;

import com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse;
import com.dokebi.dalkom.domain.product.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("select p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company, ps.amount AS stock FROM product p "
		+ "LEFT JOIN prdtStock ps ON p.productSeq = ps.productSeq WHERE p.categorySeq = :categorySeq")
	List<ProductByCategoryResponse> getProductsByCategory(@Param("categorySeq") Long categorySeq);
}
