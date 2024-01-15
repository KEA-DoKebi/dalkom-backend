package com.dokebi.dalkom.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dokebi.dalkom.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Product findByProductSeq(Long productSeq);
}
