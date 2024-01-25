package com.dokebi.dalkom.domain.product.factory;

import java.util.List;

import com.dokebi.dalkom.domain.product.dto.OptionAmountDTO;
import com.dokebi.dalkom.domain.product.dto.ProductCreateRequest;

public class ProductCreateRequestFactory {
	public static ProductCreateRequest createProductCreateRequest() {
		List<OptionAmountDTO> optionList = List.of(new OptionAmountDTO(1L, 5000));

		return new ProductCreateRequest(3L, "name", 5000, "info", "Y", "imageUrl",
			"Limbus Company", optionList);
	}
}
