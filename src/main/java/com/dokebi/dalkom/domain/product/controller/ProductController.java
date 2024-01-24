package com.dokebi.dalkom.domain.product.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.product.dto.ProductCreateRequest;
import com.dokebi.dalkom.domain.product.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {
	private final ProductService productService;

	// PRODUCTS-001 (카테고리 별 상품 목록 조회 - 사용자 메인 화면에서, 사용자가 Depth가 0인 카테고리 상품을 조회 할 때 )
	@GetMapping("/api/products/category/{categorySeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readProductListByCategory(@PathVariable Long categorySeq, Pageable pageable) {
		return Response.success(productService.readProductListByCategory(categorySeq, pageable));
	}

	// PRODUCTS-002 (상품 상세 정보 조회)
	@GetMapping("/api/product/{productSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readProduct(@PathVariable Long productSeq) {
		return Response.success(productService.readProduct(productSeq));
	}

	// PRODUCTS-003 (상품 정보 추가)
	@PostMapping("/api/product")
	@ResponseStatus(HttpStatus.OK)
	public Response createProduct(@Valid @RequestBody ProductCreateRequest productCreateRequest) {
		productService.createProduct(productCreateRequest);
		return Response.success();
	}

	// PRODUCT-004 (상품 리스트 조회 - 관리자 화면에서 전체 상품을 보여주는 것)
	@GetMapping("/api/product")
	@ResponseStatus(HttpStatus.OK)
	public Response readAdminPageProductList(Pageable pageable) {
		return Response.success(productService.readAdminPageProductList(pageable));
	}

	// PRODUCTS-005 (서브 카테고리 별 상품 목록 조회 - Depth가 1인 카테고리에 속한 상품들을 조회 할 때)
	@GetMapping("/api/products/category/detail/{categorySeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readProductListByCategoryDetail(@PathVariable Long categorySeq, Pageable pageable) {
		return Response.success(productService.readProductListByCategoryDetail(categorySeq, pageable));
	}
}
