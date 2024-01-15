package com.dokebi.dalkom.domain.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.repository.ProductRepository;
import com.dokebi.dalkom.domain.stock.entity.ProductStock;
import com.dokebi.dalkom.domain.stock.repository.ProductStockRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final ProductStockRepository productStockRepository;

	@Transactional
	public Product createProduct(Product product, Integer initialStockAmount) {
		Product savedProduct = productRepository.save(product);

		// 초기 재고 등록
		ProductStock initialStock = new ProductStock();
		initialStock.setProduct(savedProduct);
		initialStock.setAmount(initialStockAmount);
		productStockRepository.save(initialStock);
		return savedProduct;

	}

	public Product readProduct(Long productSeq) {
		return productRepository.findByProductSeq(productSeq);

	}
}
