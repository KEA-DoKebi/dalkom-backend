package com.dokebi.dalkom.domain.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.product.dto.ProductCreateReqeustDto;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.service.ProductService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {
	private final ProductService productService;

	@GetMapping("api/products/{productSeq}")
	public Response readProduct(@PathVariable Long productSeq){
		return Response.success(productService.readProduct(productSeq));
	}
	@PostMapping("/api/products")
	public Response createProduct(@RequestBody ProductCreateReqeustDto productCreateReqeustDto){
		Product product=productCreateReqeustDto.getProduct();
		Integer initialStockAmount=productCreateReqeustDto.getInitialStockAmount();
		return Response.success(productService.createProduct(product,initialStockAmount));
	}

}
