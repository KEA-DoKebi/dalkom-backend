package com.dokebi.dalkom.domain.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping("api/products/categories/{categorySeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response getProductsByCategory(@PathVariable Long categorySeq) {
		return Response.success(productService.getProductsByCategory(categorySeq));
	}
}