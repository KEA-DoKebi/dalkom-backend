package com.dokebi.dalkom.domain.product.factory;

import java.util.List;

import com.dokebi.dalkom.domain.option.dto.OptionAmountDto;
import com.dokebi.dalkom.domain.product.dto.ProductUpdateRequest;

public class ProductUpdateRequestFactory {
	public static ProductUpdateRequest createProductUpdateRequest() {
		List<OptionAmountDto> optionList =
			List.of(new OptionAmountDto(1L, 5000));

		return new ProductUpdateRequest(
			3L,
			"name",
			5000,
			"info",
			"imageUrl",
			"Limbus Company",
			"Y",
			optionList);
	}

	public static ProductUpdateRequest createFalseProductUpdateRequest() {
		return new ProductUpdateRequest(
			0L,
			"",
			-100,
			"",
			"",
			"",
			"A",
			List.of());
	}
}
