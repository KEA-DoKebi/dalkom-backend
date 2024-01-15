package com.dokebi.dalkom.domain.category.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.category.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	// API 명세서 47번 (카테고리 목록 조회) - depth 0의 카테고리 리스트 반환
	@GetMapping("api/category")
	@ResponseStatus(HttpStatus.OK)
	public Response readCategories() {
		return Response.success(categoryService.getCategoryList());
	}

	// API 명세서 48번 (특정 카테고리의 서브 카테고리 목록 조회) - 입력받은 categorySeq의 자식 카테고리 리스트 반환
	@GetMapping("api/category/{categorySeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readSubCategories(@PathVariable Long categorySeq) {
		return Response.success(categoryService.getSubCategoryList(categorySeq));
	}
}
