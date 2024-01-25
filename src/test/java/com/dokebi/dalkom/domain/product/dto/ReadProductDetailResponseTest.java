package com.dokebi.dalkom.domain.product.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

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

		List<OptionListDTO> optionList = List.of(new OptionListDTO(1L, "Option 1"));
		List<StockListDTO> stockList = List.of(new StockListDTO(1L, 100));
		List<String> productImageUrlList = Arrays.asList("http://testimageurl1.com", "http://testimageurl2.com");

		ReadProductDetailDTO productDetailDTO = new ReadProductDetailDTO(categorySeq, name, price, info, imageUrl,
			company);

		// When
		ReadProductDetailResponse response = new ReadProductDetailResponse(productDetailDTO, optionList, stockList,
			productImageUrlList);

		// Then
		assertNotNull(response);
		assertEquals(categorySeq, response.getCategorySeq());
		assertEquals(name, response.getName());
		assertEquals(price, response.getPrice());
		assertEquals(info, response.getInfo());
		assertEquals(imageUrl, response.getImageUrl());
		assertEquals(company, response.getCompany());
		assertEquals(optionList, response.getOptionList());
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
		ReadProductDetailDTO dto = new ReadProductDetailDTO(categorySeq, name, price, info, imageUrl, company);

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
		Integer amount = 150;

		// When
		StockListDTO stockListDTO = new StockListDTO(productStockSeq, amount);

		// Then
		assertNotNull(stockListDTO);
		assertEquals(productStockSeq, stockListDTO.getProductStockSeq());
		assertEquals(amount, stockListDTO.getAmount());
	}
}
