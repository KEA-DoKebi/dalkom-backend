package com.dokebi.dalkom.domain.product.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

// 상품 상세 responseBody
public class ReadProductDetailResponse {

	private Long categorySeq;
	private String name;
	private Integer price;
	private String info;
	private String imageUrl;
	private String company;
	private List<OptionListDTO> optionList;
	private List<StockListDTO> stockList;
	private List<String> productImageUrlList;

	public ReadProductDetailResponse(ReadProductDetailDTO productDetailDTO, List<OptionListDTO> optionList,
		List<StockListDTO> stockList, List<String> productImageUrlList) {

		this.categorySeq = productDetailDTO.getCategorySeq();
		this.name = productDetailDTO.getName();
		this.price = productDetailDTO.getPrice();
		this.info = productDetailDTO.getInfo();
		this.imageUrl = productDetailDTO.getImageUrl();
		this.company = productDetailDTO.getCompany();
		this.optionList = optionList;
		this.stockList = stockList;
		this.productImageUrlList = productImageUrlList;
	}

	public ReadProductDetailResponse(String name, Integer price) {
		this.name = name;
		this.price = price;
	}


}
