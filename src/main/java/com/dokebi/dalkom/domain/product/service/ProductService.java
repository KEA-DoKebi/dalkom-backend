package com.dokebi.dalkom.domain.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse;
import com.dokebi.dalkom.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	private final ProductRepository productRepository;

	@Transactional
	public List<ProductByCategoryResponse> getProductsByCategory(Long categorySeq) {
		return productRepository.getProductsByCategory(categorySeq);
	}
}
