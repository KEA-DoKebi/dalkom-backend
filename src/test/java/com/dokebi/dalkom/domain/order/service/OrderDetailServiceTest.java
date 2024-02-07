package com.dokebi.dalkom.domain.order.service;

import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dokebi.dalkom.domain.option.entity.ProductOption;
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
}
