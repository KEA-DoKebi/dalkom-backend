package com.dokebi.dalkom.domain.category.factory;

import com.dokebi.dalkom.domain.category.dto.CategoryResponse;

public class CategoryResponseFactory {
	public static CategoryResponse createCategoryResponse(
		Long categorySeq, String name, String imageUrl
	) {
		return new CategoryResponse(
			categorySeq,
			name,
			imageUrl
		);
	}

}
