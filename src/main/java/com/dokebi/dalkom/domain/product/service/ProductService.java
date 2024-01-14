package com.dokebi.dalkom.domain.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;

	@Transactional
	public Product createProduct(Product product){
		return productRepository.save(product);
	}
}
