package com.dokebi.dalkom.domain.product.factory;

import java.util.ArrayList;
import java.util.List;

import com.dokebi.dalkom.domain.product.dto.ProductByCategoryDetailResponse;

public class ProductByCategoryDetailResponseFactory {

	public static List<ProductByCategoryDetailResponse> createProductByCategoryDetailResponseList() {
		List<ProductByCategoryDetailResponse> productByCategoryDetailResponseList = new ArrayList<>();
		productByCategoryDetailResponseList.add(new ProductByCategoryDetailResponse(
			1L, // productSeq
			"TestName", // name
			10000, // price
			"Y", // state
			"RandomImageUrl", // imageUrl
			"Limbus Company", // company
			// 4.5, // rating
			// 5L // review amount
			5
		));

		productByCategoryDetailResponseList.add(new ProductByCategoryDetailResponse(
			3L, // productSeq
			"Boorung-Boorung", // name
			100000, // price
			"Y", // state
			"AnotherRandomImageUrl", // imageUrl
			"K Corp.", // company
			// 4.5, // rating
			// 10L // review amount
			5
		));

		return productByCategoryDetailResponseList;
	}
}
