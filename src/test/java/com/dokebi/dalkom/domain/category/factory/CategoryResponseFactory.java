package com.dokebi.dalkom.domain.category.factory;

import java.util.List;

import com.dokebi.dalkom.domain.category.dto.CategoryResponse;
import com.dokebi.dalkom.domain.order.dto.OrderCreateRequest;

public class CategoryResponseFactory {
	public static CategoryResponse createCategoryResponse(
		Long categorySeq,String name, String imageUrl
	){
		return new CategoryResponse(
			categorySeq,
			name,
			imageUrl
		);
	}

}
