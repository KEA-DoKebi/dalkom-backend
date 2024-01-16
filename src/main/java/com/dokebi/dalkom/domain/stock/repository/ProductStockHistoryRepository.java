package com.dokebi.dalkom.domain.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dokebi.dalkom.domain.stock.entity.ProductStockHistory;

public interface ProductStockHistoryRepository extends JpaRepository<ProductStockHistory, Long> {
}
