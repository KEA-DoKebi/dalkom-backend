package com.dokebi.dalkom.domain.cart.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
		LocalDate joinedAt = LocalDate.now();

		User user = new User("empId", "password", "name", "email@email.com",
			"address", joinedAt, "nickname", 1200000);
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
		Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 페이지 당 3개 항목

		OrderCartReadResponse fakeResponse1 = OrderCartReadResponseFactory.createOrderCartReadResponse();
		OrderCartReadResponse fakeResponse2 = OrderCartReadResponseFactory.createOrderCartReadResponse();
		OrderCartReadResponse fakeResponse3 = OrderCartReadResponseFactory.createOrderCartReadResponse();
		List<OrderCartReadResponse> responseList = Arrays.asList(fakeResponse1, fakeResponse2, fakeResponse3);
		Page<OrderCartReadResponse> responsePage = new PageImpl<>(responseList, pageable, responseList.size());

		when(orderCartService.readOrderCartList(userSeq, pageable)).thenReturn(responsePage);

		// When
		Page<OrderCartReadResponse> orderCartReadResponsePage = orderCartService.readOrderCartList(userSeq, pageable);

		// Then
		assertEquals(responsePage.getTotalElements(), orderCartReadResponsePage.getTotalElements());
		assertEquals(responseList, orderCartReadResponsePage.getContent());
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
