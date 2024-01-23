package com.dokebi.dalkom.domain.order.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dokebi.dalkom.domain.mileage.exception.MileageLackException;
import com.dokebi.dalkom.domain.mileage.service.MileageService;
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.option.service.ProductOptionService;
import com.dokebi.dalkom.domain.order.dto.OrderCreateRequest;
import com.dokebi.dalkom.domain.order.dto.OrderPageDetailDto;
import com.dokebi.dalkom.domain.order.entity.Order;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.order.repository.OrderRepository;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.service.ProductService;
import com.dokebi.dalkom.domain.stock.service.ProductStockService;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

import static com.dokebi.dalkom.domain.order.factory.OrderCreateRequestFactory.*;
import static com.dokebi.dalkom.domain.order.factory.OrderFactory.*;
import static com.dokebi.dalkom.domain.user.factory.UserFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
	@InjectMocks
	private OrderService orderService;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private OrderDetailService orderDetailService;
	@Mock
	private ProductOptionService productOptionService;
	@Mock
	private ProductService productService;
	@Mock
	private ProductStockService productStockService;
	@Mock
	private MileageService mileageService;
	@Mock
	private UserService userService;

	@Mock
	private Product mockProduct;
	@Test
	void createOrderTest() {
		// given
		OrderCreateRequest request = createOrderCreateRequest();
		User mockUser = createUser();

		given(mockProduct.getPrice()).willReturn(10000);
		given(userService.readUserByUserSeq(anyLong())).willReturn(mockUser);
		given(productService.readByProductSeq(anyLong())).willReturn(mockProduct);
		given(productOptionService.readProductOptionByPrdtOptionSeq(anyLong())).willReturn(
			new ProductOption(2L, "OP1", "의류 사이즈", "M"));
		doNothing().when(productStockService).checkStock(anyLong(), anyLong(), anyInt());

		// when
		orderService.createOrder(request);

		// then
		verify(orderRepository, times(1)).save(any(Order.class));
		verify(orderDetailService, times(request.getProductSeqList().size())).saveOrderDetail(any(OrderDetail.class));
	}

	@Test
	void createOrderWithMileageLackExceptionTest() {
		// given
		OrderCreateRequest request = createOrderCreateRequest();
		User mockUser = createMockUserWithInsufficientMileage();

		given(mockProduct.getPrice()).willReturn(10000);
		given(userService.readUserByUserSeq(anyLong())).willReturn(mockUser);
		given(productService.readByProductSeq(anyLong())).willReturn(mockProduct);

		// when, then
		assertThrows(MileageLackException.class, () -> orderService.createOrder(request));

	}

	@Test
	void readProductDetailTest() {
		// given
		List<OrderPageDetailDto> orderList = Collections.singletonList(
			createOrderPageDetailDto(3L, 3L, 100, "집업 자켓 아이보리", 97300));
		ReadProductDetailResponse productDetailResponse = new ReadProductDetailResponse("집업 자켓 아이보리",
			97300);

		doNothing().when(productStockService).checkStock(anyLong(), anyLong(), anyInt());
		given(productService.readProduct(anyLong())).willReturn(productDetailResponse);

		// when
		List<OrderPageDetailDto> result = orderService.readProductDetail(orderList);

		// then
		assertNotNull(result);
		assertEquals(orderList.size(), result.size());
	}


	// @Test
	// void readProductDetailTest() {
	// 	// Mock data
	// 	List<OrderPageDetailDto> orderList = createMockOrderList(); // Implement this method based on your needs
	//
	// 	// Mocking productStockService.checkStock()
	// 	doNothing().when(productStockService).checkStock(anyLong(), anyLong(), anyInt());
	//
	// 	// Mocking productService.readProduct()
	// 	// Adjust the method call and return value based on your actual implementation
	// 	given(productService.readProduct(anyLong())).willReturn(createMockProductInfo()); // Implement this method based on your needs
	//
	// 	// Perform the test
	// 	List<OrderPageDetailDto> result = orderService.readProductDetail(orderList);
	//
	// 	// Verify that the required methods were called
	// 	verify(productStockService, times(orderList.size())).checkStock(anyLong(), anyLong(), anyInt());
	// 	verify(productService, times(orderList.size())).readProduct(anyLong());
	//
	// 	// Verify the result
	// 	assertEquals(orderList.size(), result.size()); // or other assertions based on your expected result
	// }

	// Helper methods for creating mock data
	// private List<OrderPageDetailDto> createMockOrderList() {
	// 	List<OrderPageDetailDto> mockOrderList = new ArrayList<>();
	//
	// 	// Create a few mock OrderPageDetailDto instances for testing
	// 	mockOrderList.add(new OrderPageDetailDto(1L, 2L, 3, "Product1", 10.0, 30.0));
	// 	mockOrderList.add(new OrderPageDetailDto(4L, 5L, 6, "Product2", 15.0, 90.0));
	// 	// Add more entries as needed
	//
	// 	return mockOrderList;
	// }

	// private ReadProductDetailResponse createMockProductInfo() {
	// 	return new ReadProductDetailResponse("ProductName", "ProductDescription", 20.0);
	//
	// }

	@Test
	void readOrderByUserSeq() {

	}
}
