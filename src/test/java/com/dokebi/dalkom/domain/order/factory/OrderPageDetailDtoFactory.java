// package com.dokebi.dalkom.domain.order.factory;
//
// import com.dokebi.dalkom.domain.order.dto.OrderPageDetailDto;
//
// public class OrderPageDetailDtoFactory {
//
// 	public static OrderPageDetailDto createOrderPageDetailDto(Long productSeq, Long productOptionSeq,
// 		Integer productAmount, String productName,
// 		Integer productPrice) {
// 		return new OrderPageDetailDto(productSeq, productOptionSeq, productAmount, productName, productPrice,
// 			calculateTotalPrice(productAmount, productPrice));
// 	}
//
// 	private static Integer calculateTotalPrice(Integer productAmount, Integer productPrice) {
// 		// 여기에 총 가격을 계산하는 로직을 추가할 수 있습니다.
// 		// 예를 들어, productAmount와 productPrice를 곱하는 등의 계산을 수행합니다.
// 		return productAmount * productPrice;
// 	}
// }
