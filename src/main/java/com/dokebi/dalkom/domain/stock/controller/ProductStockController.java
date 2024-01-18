package com.dokebi.dalkom.domain.stock.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.stock.dto.ProductStockEditRequest;
import com.dokebi.dalkom.domain.stock.service.ProductStockService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductStockController {
	private final ProductStockService productStockService;

	// STOCK-001(재고 변경)
	@PutMapping("api/stock/{stockSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readStockListByCategory(@PathVariable Long stockSeq,
		@RequestBody ProductStockEditRequest request) {
		productStockService.updateStock(stockSeq, request.getAmount());
		return Response.success();
	}

}
