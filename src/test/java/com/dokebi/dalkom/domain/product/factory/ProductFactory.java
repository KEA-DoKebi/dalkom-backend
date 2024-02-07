package com.dokebi.dalkom.domain.product.factory;

import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.product.entity.Product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ProductFactory {
	public static Product createProductFactory(Category category, String name, Integer price,
		String info, String imageUrl, String company, String state) {
		return new Product(category, name, price, info, imageUrl, company, state);
	}

	public static Product createMockProduct() {
		// 여기에서 적절한 값으로 Product 객체를 생성하고 초기화
		Category category = new Category("name", 1L, "image");
		String name = "Example Product";
		Integer price = 10000;
		String info = "This is an example product.";
		String imageUrl = "example_image.jpg";
		String company = "Example Company";
		String state = "Y";

		return createProductFactory(category, name, price, info, imageUrl, company, state);
	}
}
