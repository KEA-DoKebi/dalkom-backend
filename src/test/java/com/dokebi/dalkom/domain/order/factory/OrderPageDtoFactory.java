package com.dokebi.dalkom.domain.order.factory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.dokebi.dalkom.domain.order.dto.OrderAdminReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderPageDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderPageDto;
import com.dokebi.dalkom.domain.order.dto.OrderUserReadResponse;
import com.dokebi.dalkom.domain.order.entity.Order;

public class OrderPageDtoFactory {

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

	public static OrderUserReadResponse createOrderDtoOne() {
		return new OrderUserReadResponse(1L, "상품명", 3L, 100, "1", LocalDateTime.parse("2024-01-21T00:00:00"));
	}

	public static OrderUserReadResponse createOrderDtoTwo() {
		return new OrderUserReadResponse(2L, "상품명", 3L, 100, "1", LocalDateTime.parse("2024-01-20T00:00:00"));
	}

	public static Page<OrderAdminReadResponse> createOrderList() {
		List<OrderAdminReadResponse> orderUserReadResponseList = new ArrayList<>();
		orderUserReadResponseList.add(
			new OrderAdminReadResponse(1L, LocalDateTime.parse("2024-01-21T00:00:00"), 2L, "suha", "suha", 1000, "1"));
		orderUserReadResponseList.add(
			new OrderAdminReadResponse(1L, LocalDateTime.parse("2024-01-21T00:00:00"), 2L, "hauha", "hahha", 3000,
				"1"));

		return new PageImpl<>(orderUserReadResponseList, PageRequest.of(0, 10), orderUserReadResponseList.size());
	}

	public static Order createOrder() {
		return new Order(1L, "John Doe", "123 Main St", "555-1234", "Some memo", 100);
	}

	// public static OrderDetailReadResponse createOrderDetailReadResponse() {
	// 	return new OrderDetailReadResponse(
	// 		"Product Name",
	// 		LocalDateTime.now(),
	// 		1L,
	// 		3,
	// 		150,
	// 		"Shipped",
	// 		"John Doe",
	// 		"555-1234",
	// 		"123 Main St",
	// 		"Special instructions for delivery"
	// 	);
	// }
}
