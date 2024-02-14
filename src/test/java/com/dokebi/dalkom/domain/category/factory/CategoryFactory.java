package com.dokebi.dalkom.domain.category.factory;

import com.dokebi.dalkom.domain.category.entity.Category;

public class CategoryFactory {
	public static Category createMockCategory() {
		return new Category("테스트", 1L, "이미지");
	}
}
