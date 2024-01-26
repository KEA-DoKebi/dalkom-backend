package com.dokebi.dalkom.domain.product.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.product.dto.ProductCreateRequest;
import com.dokebi.dalkom.domain.product.dto.ProductMainResponse;
import com.dokebi.dalkom.domain.product.dto.ProductUpdateRequest;
import com.dokebi.dalkom.domain.product.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {
	private final ProductService productService;

	// PRODUCT-001 (상위 카테고리 별 상품 목록 조회)
	@GetMapping("/api/product/category/{categorySeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readProductListByCategory(@PathVariable Long categorySeq, Pageable pageable) {
		return Response.success(productService.readProductListByCategory(categorySeq, pageable));
	}

	// PRODUCT-002 (상품 상세 정보 조회)
	@GetMapping("/api/product/{productSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readProduct(@PathVariable Long productSeq) {
		return Response.success(productService.readProduct(productSeq));
	}

	// PRODUCT-003 (상품 정보 추가)
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

	// PRODUCT-005 (하위 카테고리 별 상품 목록 조회)
	@GetMapping("/api/product/category/detail/{categorySeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readProductListByDetailCategory(@PathVariable Long categorySeq, Pageable pageable) {
		return Response.success(productService.readProductListByDetailCategory(categorySeq, pageable));
	}

	// PRODUCT-006 (전체 카테고리 별 상품 목록 조회 - 메인 화면)
	@GetMapping("/api/product/category/main")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Map<String, List<ProductMainResponse>>> readProductListByCategoryAll(
		@PageableDefault(size = 8) Pageable pageable) {
		Map<String, List<ProductMainResponse>> categoryProducts
			= productService.readProductListByCategoryAll(pageable);
		return ResponseEntity.ok(categoryProducts);
	}

	
	// PRODUCT-007 (특정 상품 정보 수정)
	@PutMapping("/api/product/{productSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response updateProduct(@PathVariable Long productSeq, @Valid @RequestBody ProductUpdateRequest request) {
		productService.updateProduct(productSeq, request);
		return Response.success();
	}

	// PRODUCT-008 (상품 리스트 검색)
	@GetMapping("/api/product/search")
	@ResponseStatus(HttpStatus.OK)
	public Response readProductList(@RequestParam(required = false) String name,@RequestParam(required = false) String company, Pageable pageable) {
		return Response.success(productService.readProductListSearch(name,company,pageable));
	}

	// // PRODUCT-008 (특정 상품 정보 삭제)
	// @DeleteMapping("/api/product/{productSeq}")
	// @ResponseStatus(HttpStatus.OK)
	// public Response deleteProduct(@PathVariable Long productSeq) {
	// 	productService.deleteProduct(productSeq);
	// 	return Response.success();
	// }

}
