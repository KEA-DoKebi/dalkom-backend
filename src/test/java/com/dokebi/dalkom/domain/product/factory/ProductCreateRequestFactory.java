package com.dokebi.dalkom.domain.product.factory;

import java.util.List;

import com.dokebi.dalkom.domain.option.dto.OptionAmountDto;
import com.dokebi.dalkom.domain.product.dto.ProductCreateRequest;

public class ProductCreateRequestFactory {
	public static ProductCreateRequest createProductCreateRequest() {
		List<OptionAmountDto> optionList =
			List.of(new OptionAmountDto(1L, 5000));

		return new ProductCreateRequest(
			3L,
			"name",
			5000,
			"info",
			"Y",
			"imageUrl",
			"Limbus Company",
			optionList);
	}

	public static ProductCreateRequest createFalseProductCreateRequest() {
		return new ProductCreateRequest(
			0L,
			"",
			-100,
			"",
			"A",
			"",
			"",
			List.of());
	}
}
