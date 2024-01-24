package com.dokebi.dalkom.domain.product.factory;

import java.util.ArrayList;
import java.util.List;

import com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse;

public class ProductByCategoryResponseFactory {

	public static List<ProductByCategoryResponse> createProductByCategoryResponseList() {
		List<ProductByCategoryResponse> productByCategoryResponseList = new ArrayList<>();
		productByCategoryResponseList.add(new ProductByCategoryResponse(
			1L, // productSeq;
			"TestName", // name;
			10000, // price;
			"Y", // state;
			"RandomImageUrl", // imageUrl;
			"Limbus Company", // company;
			500 // stock;
		));

		productByCategoryResponseList.add(new ProductByCategoryResponse(
			3L, // productSeq;
			"Boorung-Boorung", // name;
			100000, // price;
			"Y", // state;
			"AnotherRandomImageUrl", // imageUrl;
			"K Corp.", // company;
			50 // stock;
		));

		return productByCategoryResponseList;
	}
}
