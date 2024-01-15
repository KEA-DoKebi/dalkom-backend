package com.dokebi.dalkom.domain.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.product.dto.OptionListDTO;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailDTO;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.dto.StockListDTO;
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

	@Transactional
	public List<ProductByCategoryResponse> getProductsByCategory(Long categorySeq) {
		return productRepository.getProductsByCategory(categorySeq);
	}

	//쿼리 결과를 조합해서 return하는 메서드
	public ReadProductDetailResponse readProduct(Long productSeq) {
		ReadProductDetailDTO productDetailDTO = productRepository.getProductDetailBySeq(productSeq);
		List<StockListDTO> stockList = productRepository.getStockListBySeq(productSeq);
		List<OptionListDTO> optionList = productRepository.getOptionListBySeq(productSeq);
		List<String> productImageUrlList = productRepository.getProductImageBySeq(productSeq);
		return new ReadProductDetailResponse(productDetailDTO, optionList, stockList, productImageUrlList);
	}
}
