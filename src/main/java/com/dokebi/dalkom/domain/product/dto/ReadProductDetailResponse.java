package com.dokebi.dalkom.domain.product.dto;

import java.util.List;

import com.dokebi.dalkom.domain.stock.dto.StockListDto;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 상품 상세 responseBody
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Generated
public class ReadProductDetailResponse {
	private Long categorySeq;
	private String name;
	private Integer price;
	private String info;
	private String imageUrl;
	private String company;
	private List<StockListDto> stockList;
	private List<String> productImageUrlList;

	@Generated
	public ReadProductDetailResponse(ReadProductDetailDto productDetailDTO,
		List<StockListDto> stockList, List<String> productImageUrlList) {
		this.categorySeq = productDetailDTO.getCategorySeq();
		this.name = productDetailDTO.getName();
		this.price = productDetailDTO.getPrice();
		this.info = productDetailDTO.getInfo();
		this.imageUrl = productDetailDTO.getImageUrl();
		this.company = productDetailDTO.getCompany();
		this.stockList = stockList;
		this.productImageUrlList = productImageUrlList;
	}

	@Generated
	public ReadProductDetailResponse(String name, Integer price) {
		this.name = name;
		this.price = price;
	}
}
