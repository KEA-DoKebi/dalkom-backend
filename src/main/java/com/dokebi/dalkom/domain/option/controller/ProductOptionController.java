package com.dokebi.dalkom.domain.option.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.option.service.ProductOptionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductOptionController {
	private final ProductOptionService productOptionService;

	// OPTION-001(옵션 목록 조회)
	@GetMapping("api/option")
	@ResponseStatus(HttpStatus.OK)
	public Response readOptionListByCategory() {
		return Response.success(productOptionService.getOptionList());
	}

	// OPTION-002(옵션 상세 조회)
	@GetMapping("api/option/{optionCode}")
	@ResponseStatus(HttpStatus.OK)
	public Response readOptionListByCategory(@PathVariable String optionCode) {
		return Response.success(productOptionService.getOptionDetailList(optionCode));
	}
}
