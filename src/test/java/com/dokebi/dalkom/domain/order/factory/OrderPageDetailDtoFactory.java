package com.dokebi.dalkom.domain.order.factory;

import com.dokebi.dalkom.domain.order.dto.OrderPageDetailDto;

public class OrderPageDetailDtoFactory {

	public static OrderPageDetailDto createOrderPageDetailDto(Long productSeq, Long productOptionSeq,
		Integer productAmount, String productName, String productOptionDetail, Integer productPrice) {
		return new OrderPageDetailDto(productSeq, productOptionSeq, productAmount, productName, productPrice,
			productOptionDetail, productAmount * productPrice);
	}

	public static OrderPageDetailDto createOrderPageDetailDto() {
		OrderPageDetailDto orderDetailDto = new OrderPageDetailDto();
		orderDetailDto.setProductSeq(1L);
		orderDetailDto.setProductOptionSeq(2L);
		orderDetailDto.setProductAmount(3);
		orderDetailDto.setProductName("Sample Product");
		orderDetailDto.setProductPrice(1000);
		orderDetailDto.setProductOptionDetail("Sample Option");
		orderDetailDto.setTotalPrice(orderDetailDto.getProductPrice() * orderDetailDto.getProductAmount());
		return orderDetailDto;
	}

}
