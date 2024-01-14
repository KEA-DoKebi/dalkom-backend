package com.dokebi.dalkom.domain.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

	// @GetMapping("api/products"){
	//
	// }
	@PostMapping("/api/products")
	public void createProduct(@RequestBody Product product){
		System.out.println(product);
		productService.createProduct(product);
	}

}
