package com.dokebi.dalkom.domain.cart.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
		Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 페이지 당 3개 항목

		OrderCartReadResponse fakeResponse1 = OrderCartReadResponseFactory.createOrderCartReadResponse();
		OrderCartReadResponse fakeResponse2 = OrderCartReadResponseFactory.createOrderCartReadResponse();
		OrderCartReadResponse fakeResponse3 = OrderCartReadResponseFactory.createOrderCartReadResponse();

		List<OrderCartReadResponse> responseList = Arrays.asList(fakeResponse1, fakeResponse2, fakeResponse3);
		Page<OrderCartReadResponse> responsePage = new PageImpl<>(responseList, pageable, responseList.size());

		when(orderCartRepository.findOrderCartList(userSeq, pageable)).thenReturn(responsePage);

		// When
		Page<OrderCartReadResponse> orderCartReadResponsePage = orderCartRepository.findOrderCartList(userSeq,
			pageable);

		// Then
		assertEquals(responsePage, orderCartReadResponsePage);
	}
}
