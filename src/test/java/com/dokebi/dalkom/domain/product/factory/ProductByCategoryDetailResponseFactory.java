package com.dokebi.dalkom.domain.product.factory;

import java.util.ArrayList;
import java.util.List;

import com.dokebi.dalkom.domain.product.dto.ProductByCategoryDetailPage;

public class ProductByCategoryDetailResponseFactory {

	public static List<ProductByCategoryDetailPage> createProductByCategoryDetailResponseList() {
		List<ProductByCategoryDetailPage> productByCategoryDetailPageList = new ArrayList<>();
		productByCategoryDetailPageList.add(new ProductByCategoryDetailPage(
			1L, // productSeq
			1L,
			"TestName", // name
			10000, // price
			"Y", // state
			"RandomImageUrl", // imageUrl
			"Limbus Company", // company
			4.5, // rating
			5L // review amount
		));

		productByCategoryDetailPageList.add(new ProductByCategoryDetailPage(
			3L, // productSeq
			3L,
			"Boorung-Boorung", // name
			100000, // price
			"Y", // state
			"AnotherRandomImageUrl", // imageUrl
			"K Corp.", // company
			4.5, // rating
			10L // review amount
		));

		return productByCategoryDetailPageList;
	}
}
