package com.dokebi.dalkom.domain.product.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.dokebi.dalkom.domain.stock.dto.StockListDto;

public class ReadProductDetailResponseTest {

	@Test
	public void testReadProductDetailResponseConstructor() {
		// Given
		Long categorySeq = 1L;
		String name = "Test Product";
		Integer price = 10000;
		String info = "Test Product Info";
		String imageUrl = "http://testimageurl.com";
		String company = "Test Company";

		List<StockListDto> stockList = List.of(new StockListDto(1L, 1L, "M", 100));
		List<String> productImageUrlList = Arrays.asList("http://testimageurl1.com", "http://testimageurl2.com");

		ReadProductDetailDto productDetailDTO = new ReadProductDetailDto(categorySeq, name, price, info, imageUrl,
			company);

		// When
		ReadProductDetailResponse response = new ReadProductDetailResponse(productDetailDTO, stockList,
			productImageUrlList);

		// Then
		assertNotNull(response);
		assertEquals(categorySeq, response.getCategorySeq());
		assertEquals(name, response.getName());
		assertEquals(price, response.getPrice());
		assertEquals(info, response.getInfo());
		assertEquals(imageUrl, response.getImageUrl());
		assertEquals(company, response.getCompany());
		assertEquals(stockList, response.getStockList());
		assertEquals(productImageUrlList, response.getProductImageUrlList());
	}

	@Test
	public void testReadProductDetailDTOSimpleConstructor() {
		// Given
		String name = "Simple Test Product";
		Integer price = 5000;

		// When
		ReadProductDetailResponse response = new ReadProductDetailResponse(name, price);

		// Then
		assertNotNull(response);
		assertEquals(name, response.getName());
		assertEquals(price, response.getPrice());
	}

	@Test
	public void testReadProductDetailDTOConstructor() {
		// Given
		Long categorySeq = 2L;
		String name = "Another Test Product";
		Integer price = 20000;
		String info = "Another Test Product Info";
		String imageUrl = "http://anothertestimageurl.com";
		String company = "Another Test Company";

		// When
		ReadProductDetailDto dto = new ReadProductDetailDto(categorySeq, name, price, info, imageUrl, company);

		// Then
		assertNotNull(dto);
		assertEquals(categorySeq, dto.getCategorySeq());
		assertEquals(name, dto.getName());
		assertEquals(price, dto.getPrice());
		assertEquals(info, dto.getInfo());
		assertEquals(imageUrl, dto.getImageUrl());
		assertEquals(company, dto.getCompany());
	}

	@Test
	public void testStockListDTOConstructor() {
		// Given
		Long productStockSeq = 1L;
		Long productOptionSeq = 2L;
		Integer amount = 150;

		// When
		StockListDto stockListDTO = new StockListDto(productStockSeq, productOptionSeq, "M", amount);

		// Then
		assertNotNull(stockListDTO);
		assertEquals(productStockSeq, stockListDTO.getProductStockSeq());
		assertEquals(amount, stockListDTO.getAmount());
	}
}
