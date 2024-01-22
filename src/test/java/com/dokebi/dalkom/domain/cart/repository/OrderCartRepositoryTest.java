package com.dokebi.dalkom.domain.cart.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dokebi.dalkom.domain.cart.dto.OrderCartReadResponse;
import com.dokebi.dalkom.domain.cart.factory.OrderCartReadResponseFactory;

@ExtendWith(MockitoExtension.class)
public class OrderCartRepositoryTest {
	@Mock
	private OrderCartRepository orderCartRepository;

	@Test
	void findOrderCartListTest() {
		// Given
		Long userSeq = 1L;

		OrderCartReadResponse fakeResponse1 = OrderCartReadResponseFactory.createOrderCartReadResponse();
		OrderCartReadResponse fakeResponse2 = OrderCartReadResponseFactory.createOrderCartReadResponse();
		OrderCartReadResponse fakeResponse3 = OrderCartReadResponseFactory.createOrderCartReadResponse();

		List<OrderCartReadResponse> responseList = Arrays.asList(fakeResponse1, fakeResponse2, fakeResponse3);

		when(orderCartRepository.findOrderCartList(userSeq)).thenReturn(responseList);

		// When
		List<OrderCartReadResponse> orderCartReadResponseList = orderCartRepository.findOrderCartList(userSeq);

		// Then
		assertEquals(responseList, orderCartReadResponseList);
	}
}
