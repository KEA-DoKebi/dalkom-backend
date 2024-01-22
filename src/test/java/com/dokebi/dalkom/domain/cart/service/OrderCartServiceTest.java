package com.dokebi.dalkom.domain.cart.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dokebi.dalkom.domain.cart.dto.OrderCartCreateRequest;
import com.dokebi.dalkom.domain.cart.dto.OrderCartDeleteRequest;
import com.dokebi.dalkom.domain.cart.dto.OrderCartReadResponse;
import com.dokebi.dalkom.domain.cart.entity.OrderCart;
import com.dokebi.dalkom.domain.cart.factory.OrderCartCreateRequestFactory;
import com.dokebi.dalkom.domain.cart.factory.OrderCartDeleteRequestFactory;
import com.dokebi.dalkom.domain.cart.factory.OrderCartReadResponseFactory;
import com.dokebi.dalkom.domain.cart.repository.OrderCartRepository;
import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.service.ProductService;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class OrderCartServiceTest {
	@InjectMocks
	private OrderCartService orderCartService;
	@Mock
	private OrderCartRepository orderCartRepository;
	@Mock
	private ProductService productService;
	@Mock
	private UserService userService;
	@Captor
	private ArgumentCaptor<OrderCart> orderCartArgumentCaptor;
	@Captor
	private ArgumentCaptor<Long> orderCartSeqCaptor;

	@Test
	void createOrderCartTest() {
		// Given
		Long userSeq = 1L;
		OrderCartCreateRequest request = OrderCartCreateRequestFactory
			.createOrderCartCreateRequest();

		User user = new User("empId", "password", "name", "email@email.com",
			"address", "joinedAt", "nickname", 1200000);
		when(userService.readUserByUserSeq(userSeq)).thenReturn(user);

		Category category = new Category("name", 1L, "imageUrl");

		Product product = new Product(category, "name", 1000,
			"info", "imageUrl", "company", "Y");
		when(productService.readProductByProductSeq(request.getProductSeq())).thenReturn(product);

		// When
		orderCartService.createOrderCart(userSeq, request);

		// Then
		verify(orderCartRepository).save(orderCartArgumentCaptor.capture());

		OrderCart capturedOrderCart = orderCartArgumentCaptor.getValue();

		assertEquals(user, capturedOrderCart.getUser());
		assertEquals(product, capturedOrderCart.getProduct());
		assertEquals(request.getPrdtOptionSeq(), capturedOrderCart.getPrdtOptionSeq());
		assertEquals(request.getAmount(), capturedOrderCart.getAmount());
	}

	@Test
	void readOrderCartListTest() {
		// Given
		Long userSeq = 1L;
		OrderCartReadResponse fakeResponse1 = OrderCartReadResponseFactory.createOrderCartReadResponse();
		OrderCartReadResponse fakeResponse2 = OrderCartReadResponseFactory.createOrderCartReadResponse();
		OrderCartReadResponse fakeResponse3 = OrderCartReadResponseFactory.createOrderCartReadResponse();

		List<OrderCartReadResponse> responseList = Arrays.asList(fakeResponse1, fakeResponse2, fakeResponse3);

		when(orderCartService.readOrderCartList(userSeq)).thenReturn(responseList);

		// When
		List<OrderCartReadResponse> orderCartReadResponseList = orderCartService.readOrderCartList(userSeq);

		// Then
		for (int i = 0; i < responseList.size(); i++) {
			OrderCartReadResponse fakeResponse = responseList.get(i);
			OrderCartReadResponse response = orderCartReadResponseList.get(i);

			assertEquals(fakeResponse, response);
		}
	}

	@Test
	void deleteOrderCartTest() {
		// Given
		OrderCartDeleteRequest request = OrderCartDeleteRequestFactory.createOrderCartDeleteRequest();

		List<Long> orderCartSeqList = request.getOrderCartSeqList();
		for (Long orderCartSeq : orderCartSeqList) {
			when(orderCartRepository.existsById(orderCartSeq)).thenReturn(true);
		}

		// When
		assertDoesNotThrow(() -> orderCartService.deleteOrderCart(request));

		// Then
		verify(orderCartRepository, times(orderCartSeqList.size())).deleteById(orderCartSeqCaptor.capture());

		List<Long> capturedOrderCartSeqList = orderCartSeqCaptor.getAllValues();
		assertEquals(orderCartSeqList, capturedOrderCartSeqList);
	}
}
