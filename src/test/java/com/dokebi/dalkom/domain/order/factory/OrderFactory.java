package com.dokebi.dalkom.domain.order.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.dokebi.dalkom.domain.order.dto.OrderReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderPageDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderPageDto;
import com.dokebi.dalkom.domain.order.entity.Order;

public class OrderFactory {

	public static OrderPageDto createOrderPageDto() {
		List<OrderPageDetailDto> orderList = new ArrayList<>();
		orderList.add(createOrderPageDetailDto(1L, 1L, 1, "안경", 50000));
		orderList.add(createOrderPageDetailDto(2L, 2L, 2, "병원", 30000));

		return new OrderPageDto(orderList);
	}

	public static OrderPageDetailDto createOrderPageDetailDto(Long productSeq, Long productOptionSeq,
		Integer productAmount, String productName, Integer productPrice) {
		OrderPageDetailDto orderPageDetailDto = new OrderPageDetailDto();
		orderPageDetailDto.setProductSeq(productSeq);
		orderPageDetailDto.setProductOptionSeq(productOptionSeq);
		orderPageDetailDto.setProductAmount(productAmount);
		orderPageDetailDto.setProductName(productName);
		orderPageDetailDto.setProductPrice(productPrice);
		orderPageDetailDto.setTotalPrice(productAmount * productPrice);

		return orderPageDetailDto;
	}

	public static OrderReadResponse createOrderDtoOne() {
		return new OrderReadResponse(1L, "John Doe", "123 Main St", "555-1234", "Some memo", 100);
	}

	public static OrderReadResponse createOrderDtoTwo() {
		return new OrderReadResponse(2L, "suha Doe", "123 aaa St", "335-1234", "Some memo memo", 100);
	}

	public static Page<OrderReadResponse> createOrderList() {
		List<OrderReadResponse> orderReadResponseList = new ArrayList<>();
		orderReadResponseList.add(new OrderReadResponse(1L, "John Doe", "123 Main St", "555-1234", "Some memo", 100));
		orderReadResponseList.add(
			new OrderReadResponse(2L, "Suha Doe", "123 AAA St", "335-1234", "Some memo memo", 100));

		return new PageImpl<>(orderReadResponseList, PageRequest.of(0, 10), orderReadResponseList.size());
	}

	public static Order createOrder() {
		return new Order(1L, "John Doe", "123 Main St", "555-1234", "Some memo", 100);
	}
}
