package com.dokebi.dalkom.domain.product.factory;

import java.util.ArrayList;
import java.util.List;

import com.dokebi.dalkom.domain.product.dto.ReadProductResponse;

public class ReadProductResponseFactory {

	public static List<ReadProductResponse> createReadProductResponseList() {
		List<ReadProductResponse> readProductResponseList = new ArrayList<>();
		readProductResponseList.add(new ReadProductResponse("name", 5000));

		readProductResponseList.add(new ReadProductResponse("anotherName", 5500));

		return readProductResponseList;
	}
}
