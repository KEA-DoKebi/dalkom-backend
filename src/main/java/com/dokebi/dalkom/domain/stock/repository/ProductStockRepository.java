package com.dokebi.dalkom.domain.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dokebi.dalkom.domain.stock.entity.ProductStock;

public interface ProductStockRepository extends JpaRepository<ProductStock,Long> {
}
