package com.dokebi.dalkom.domain.stock.factory;

import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.stock.entity.ProductStock;

public class StockFactory {
	public static ProductStock createMockStock(Product product) {
		ProductOption productOption = ProductOption.createProductOption();
		return new ProductStock(product, productOption, 300);
	}
}
