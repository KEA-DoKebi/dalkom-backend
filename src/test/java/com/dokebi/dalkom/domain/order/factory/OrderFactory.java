package com.dokebi.dalkom.domain.order.factory;

import java.util.ArrayList;
import java.util.List;

import com.dokebi.dalkom.domain.order.dto.OrderDto;
import com.dokebi.dalkom.domain.order.dto.OrderPageDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderPageDto;

public class OrderFactory {

	public static OrderPageDto createOrderPageDto() {
		List<OrderPageDetailDto> orderList = new ArrayList<>();
		orderList.add(createOrderPageDetailDto(1L, 1L, 1, "안경",50000  ));
		orderList.add(createOrderPageDetailDto(2L, 2L, 2, "병원",30000  ));

		return new OrderPageDto(orderList);
	}

	public static OrderPageDetailDto createOrderPageDetailDto(
		Long productSeq, Long productOptionSeq, Integer productAmount,
		String productName, Integer productPrice) {
		OrderPageDetailDto orderPageDetailDto = new OrderPageDetailDto();
		orderPageDetailDto.setProductSeq(productSeq);
		orderPageDetailDto.setProductOptionSeq(productOptionSeq);
		orderPageDetailDto.setProductAmount(productAmount);
		orderPageDetailDto.setProductName(productName);
		orderPageDetailDto.setProductPrice(productPrice);
		orderPageDetailDto.setTotalPrice(productAmount * productPrice);

		return orderPageDetailDto;
	}

	public static OrderDto createOrderDtoOne() {
		return new OrderDto(
			1L,
			"John Doe",
			"123 Main St",
			"555-1234",
			"Some memo",
			100
		);
	}
	public static OrderDto createOrderDtoTwo() {
		return new OrderDto(
			2L,
			"suha Doe",
			"123 aaa St",
			"335-1234",
			"Some memo memo",
			100
		);
	}
}
