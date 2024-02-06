package com.dokebi.dalkom.domain.order.factory;

import java.time.LocalDateTime;
import java.util.List;

import com.dokebi.dalkom.domain.order.dto.OrderDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderDetailReadResponse;
import com.dokebi.dalkom.domain.order.dto.ReceiverDetailDto;

public class OrderDetailReadResponseFactory {
	public static OrderDetailReadResponse createOrderDetailReadResponse() {
		List<OrderDetailDto> orderDetailList = List.of(
			createOrderDetailDto(),
			createOrderDetailDto()
		);
		ReceiverDetailDto receiverDetail = createReceiverDetailDto();
		Integer totalPrice = 50000; // 예시로 임의의 값 설정

		return new OrderDetailReadResponse(orderDetailList, receiverDetail, totalPrice);
	}

	public static OrderDetailDto createOrderDetailDto() {
		return new OrderDetailDto(
			1L, // ordrDetailSeq
			"Product Name",
			"Image URL",
			123L, // optionSeq
			"Detail",
			LocalDateTime.now(), // orderDate
			1L, // ordrSeq
			2, // amount
			25000, // totalPrice
			"CONFIRMED" // ordrState
		);
	}

	public static ReceiverDetailDto createReceiverDetailDto() {
		return new ReceiverDetailDto(
			"John Doe", // receiverName
			"123-456-7890", // receiverMobileNum
			"123 Main St, City", // receiverAddress
			"Please deliver in the morning" // receiverMemo
		);
	}
}
