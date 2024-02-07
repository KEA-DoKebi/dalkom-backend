package com.dokebi.dalkom.domain.order.service;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.order.dto.OrderDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderDetailSimpleResponse;
import com.dokebi.dalkom.domain.order.entity.Order;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.order.repository.OrderDetailRepository;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
public class OrderDetailServiceTest {
	@InjectMocks
	private OrderDetailService orderDetailService;
	@Mock
	private OrderDetailRepository orderDetailRepository;

	// ORDER-003 (특정 주문 조회)에서 사용되는 함수
	@Test
	@DisplayName("readOrderDetailDtoByOrderSeq 테스트")
	void readOrderDetailDtoByOrderSeqTest() {
		// Given
		Long orderSeq = 1L;
		OrderDetailDto orderDetailDto1 = new OrderDetailDto(1L, "productName",
			"imageUrl", 1L, "detail", LocalDateTime.now(), 1L,
			5, 10000, "ordrState");
		OrderDetailDto orderDetailDto2 = new OrderDetailDto(2L, "productName",
			"imageUrl", 2L, "detail", LocalDateTime.now(), 2L,
			10, 20000, "ordrState");
		List<OrderDetailDto> orderDetailDtoList = Arrays.asList(orderDetailDto1, orderDetailDto2);

		when(orderDetailRepository.findOrderDetailDtoListByOrderSeq(orderSeq)).thenReturn(orderDetailDtoList);

		// When
		orderDetailService.readOrderDetailDtoByOrderSeq(orderSeq);

		// Then
		verify(orderDetailRepository).findOrderDetailDtoListByOrderSeq(orderSeq);
	}

	// ORDER-005 (결제 하기)에서 사용되는 함수
	@Test
	@DisplayName("saveOrderDetail 테스트")
	void saveOrderDetailTest() {
		// Given
		User user = new User("DKT201735826", "123456a!", "name",
			"email@email.com", "address", LocalDate.now(), "nickname", 120000);
		Order order = new Order(user, "receiverName", "receiverAddress",
			"receiverMobileNum", "receiverMemo", 10000);
		Product product = new Product(1L);
		ProductOption productOption = ProductOption.createProductOption();
		OrderDetail orderDetail = new OrderDetail(order, product, productOption, 5, 10000);

		// When
		orderDetailService.saveOrderDetail(orderDetail);

		// Then
		verify(orderDetailRepository).save(orderDetail);
	}

	// ORDER-011 (리뷰용 단일 주문상세 조회)에서 사용되는 함수
	@Test
	@DisplayName("readOrderDetailSimpleResponseByOrderDetailSeq 테스트")
	void readOrderDetailSimpleResponseByOrderDetailSeqTest() {
		// Given
		Long orderDetailSeq = 1L;
		OrderDetailSimpleResponse response = new OrderDetailSimpleResponse();

		when(orderDetailRepository.findOrderDetailSimpleResponseByOrdrDetailSeq(orderDetailSeq))
			.thenReturn(Optional.of(response));

		// When
		orderDetailService.readOrderDetailSimpleResponseByOrderDetailSeq(orderDetailSeq);

		// Then
		verify(orderDetailRepository).findOrderDetailSimpleResponseByOrdrDetailSeq(orderDetailSeq);
	}

	// ORDER-012 (취소/환불 목록 조회)에서 사용되는 함수
	@Test
	@DisplayName("readFirstOrderDetailByOrderSeq 테스트")
	void readFirstOrderDetailByOrderSeqTest() {
		// Given
		Long orderSeq = 1L;
		OrderDetail orderDetail = new OrderDetail();

		when(orderDetailRepository.findFirstByOrder_OrdrSeq(orderSeq)).thenReturn(Optional.of(orderDetail));

		// When
		orderDetailService.readFirstOrderDetailByOrderSeq(orderSeq);

		// Then
		verify(orderDetailRepository).findFirstByOrder_OrdrSeq(orderSeq);
	}
}