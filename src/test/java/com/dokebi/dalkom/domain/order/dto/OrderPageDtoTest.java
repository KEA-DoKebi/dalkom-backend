package com.dokebi.dalkom.domain.order.dto;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class OrderPageDtoTest {

	@Test
	public void createOrderPageDto() {
		// Given
		OrderPageDetailDto detailDto1 = new OrderPageDetailDto(1L, 2L, 3, "상품1", 10000, 30000);
		OrderPageDetailDto detailDto2 = new OrderPageDetailDto(4L, 5L, 2, "상품2", 20000, 40000);
		List<OrderPageDetailDto> expectedOrderList = Arrays.asList(detailDto1, detailDto2);

		// When
		OrderPageDto orderPageDto = new OrderPageDto(expectedOrderList);

		// Then
		assertThat(orderPageDto.getOrderList()).hasSize(2);
		assertThat(orderPageDto.getOrderList()).containsExactlyInAnyOrder(detailDto1, detailDto2);
	}
}
