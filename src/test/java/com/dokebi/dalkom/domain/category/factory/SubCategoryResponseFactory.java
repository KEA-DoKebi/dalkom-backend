package com.dokebi.dalkom.domain.category.factory;

import com.dokebi.dalkom.domain.category.dto.SubCategoryResponse;

public class SubCategoryResponseFactory {
	public static SubCategoryResponse createSubCategoryResponse(
		Long categorySeq, String name
	) {
		return new SubCategoryResponse(
			categorySeq,
			name
		);
	}

}
