package com.dokebi.dalkom.domain.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dokebi.dalkom.domain.product.dto.ProductSearchCondition;
import com.dokebi.dalkom.domain.product.dto.ReadProductResponse;

public interface ProductRepositoryCustom {

	Page<ReadProductResponse> searchProduct(ProductSearchCondition condition, Pageable pageable);

}
