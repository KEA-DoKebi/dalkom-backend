package com.dokebi.dalkom.domain.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.product.dto.ProductCreateRequest;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {
	private final ProductService productService;

	// PRODUCTS-001(카테고리 별 상품 목록 조회)
	@GetMapping("api/products/categories/{categorySeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response getProductsByCategory(@PathVariable Long categorySeq) {
		return Response.success(productService.getProductsByCategory(categorySeq));
	}

	// PRODUCTS-002(상품 상세 정보 조회)
	@GetMapping("api/products/{productSeq}")
	public Response readProduct(@PathVariable Long productSeq) {
		return Response.success(productService.readProduct(productSeq));
	}

	// PRODUCTS-003(상품 정보 추가)
	@PostMapping("/api/products")
	public Response createProduct(@RequestBody ProductCreateRequest productCreateRequestDto) {
		Product product = productCreateRequestDto.getProduct();
		Integer initialStockAmount = productCreateRequestDto.getInitialStockAmount();
		return Response.success(productService.createProduct(product, initialStockAmount));
	}
}
