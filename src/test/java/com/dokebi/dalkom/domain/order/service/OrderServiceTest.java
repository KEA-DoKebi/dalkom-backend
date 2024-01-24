// TODO Pagenation 해결될때까지 동결
// package com.dokebi.dalkom.domain.order.service;
//
// import static com.dokebi.dalkom.domain.order.factory.OrderCreateRequestFactory.*;
// import static com.dokebi.dalkom.domain.order.factory.OrderFactory.*;
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.BDDMockito.*;
//
// import java.util.Arrays;
// import java.util.Collections;
// import java.util.List;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
//
// import com.dokebi.dalkom.domain.mileage.exception.MileageLackException;
// import com.dokebi.dalkom.domain.option.entity.ProductOption;
// import com.dokebi.dalkom.domain.option.service.ProductOptionService;
// import com.dokebi.dalkom.domain.order.dto.OrderCreateRequest;
// import com.dokebi.dalkom.domain.order.dto.OrderDto;
// import com.dokebi.dalkom.domain.order.dto.OrderPageDetailDto;
// import com.dokebi.dalkom.domain.order.entity.Order;
// import com.dokebi.dalkom.domain.order.entity.OrderDetail;
// import com.dokebi.dalkom.domain.order.repository.OrderRepository;
// import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
// import com.dokebi.dalkom.domain.product.entity.Product;
// import com.dokebi.dalkom.domain.product.service.ProductService;
// import com.dokebi.dalkom.domain.stock.service.ProductStockService;
// import com.dokebi.dalkom.domain.user.entity.User;
// import com.dokebi.dalkom.domain.user.service.UserService;
//
// @ExtendWith(MockitoExtension.class)
// public class OrderServiceTest {
// 	@InjectMocks
// 	private OrderService orderService;
//
// 	@Mock
// 	private OrderRepository orderRepository;
// 	@Mock
// 	private OrderDetailService orderDetailService;
// 	@Mock
// 	private ProductService productService;
// 	@Mock
// 	private ProductStockService productStockService;
// 	@Mock
// 	private ProductOptionService productOptionService;
// 	@Mock
// 	private UserService userService;
//
// 	@Mock
// 	private Product mockProduct;
//
// 	// Setup your mock data and mock behaviors here
// 	@BeforeEach
// 	void setUp() {
//
// 	}
//
// 	@Test
// 	void createOrderTest() {
// 		// given
// 		OrderCreateRequest request = createOrderCreateRequest();
// 		User mockUser = createMockUser();
//
// 		given(mockProduct.getPrice()).willReturn(10000);
// 		given(userService.readUserByUserSeq(anyLong())).willReturn(mockUser);
// 		given(productService.readByProductSeq(anyLong())).willReturn(mockProduct);
// 		given(productOptionService.readProductOptionByPrdtOptionSeq(anyLong())).willReturn(
// 			new ProductOption(2L, "OP1", "의류 사이즈", "M"));
// 		doNothing().when(productStockService).checkStock(anyLong(), anyLong(), anyInt());
//
// 		// when
// 		orderService.createOrder(request);
//
// 		// then
// 		verify(orderRepository, times(1)).save(any(Order.class));
// 		verify(orderDetailService, times(request.getProductSeqList().size())).saveOrderDetail(any(OrderDetail.class));
// 	}
//
// 	@Test
// 	void createOrderWithMileageLackExceptionTest() {
// 		// given
// 		OrderCreateRequest request = createOrderCreateRequest();
// 		User mockUser = createMockUserWithInsufficientMileage();
//
// 		given(mockProduct.getPrice()).willReturn(10000);
// 		given(userService.readUserByUserSeq(anyLong())).willReturn(mockUser);
// 		given(productService.readByProductSeq(anyLong())).willReturn(mockProduct);
//
// 		// when, then
// 		assertThrows(MileageLackException.class, () -> orderService.createOrder(request));
//
// 	}
//
// 	@Test
// 	void readProductDetailTest() {
// 		// given
// 		List<OrderPageDetailDto> orderList = List.of(
// 			createOrderPageDetailDto(3L, 3L, 100, "집업 자켓 아이보리", 97300));
// 		ReadProductDetailResponse productDetailResponse = new ReadProductDetailResponse("집업 자켓 아이보리",
// 			97300);
// 		doNothing().when(productStockService).checkStock(anyLong(), anyLong(), anyInt());
// 		given(productService.readProduct(anyLong())).willReturn(productDetailResponse);
//
// 		// when
// 		List<OrderPageDetailDto> result = orderService.readProductDetail(orderList);
//
// 		// then
// 		assertNotNull(result);
// 		assertEquals(orderList.size(), result.size());
// 	}
//
// 	@Test
// 	void readOrderByUserSeqTest() {
// 		// given
// 		Long userSeq = 1L;
// 		Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 페이지 당 3개 항목
//
// 		// List<Order> orders = createOrderList();
// 		// List<OrderDto> orders = new ArrayList<OrderDto>();
// 		// orders.add(createOrderDtoOne());
// 		// orders.add(createOrderDtoTwo());
//
// 		OrderDto fakeOrderDto1 = createOrderDtoOne();
// 		OrderDto fakeOrderDto2 = createOrderDtoTwo();
//
// 		List<OrderDto> orderDtoList = Arrays.asList(fakeOrderDto1, fakeOrderDto2);
//
// 		Page<OrderDto> responsePage = new PageImpl<>(orderDtoList, pageable, orderDtoList.size());
// 		// given(orderRepository.findOrderListByUserSeq(anyLong(), pageable)).willReturn(responsePage);
//
// 		when(orderService.readOrderByUserSeq(userSeq, pageable)).thenReturn(responsePage);
//
// 		// when
// 		Page<OrderDto> result = orderService.readOrderByUserSeq(userSeq, pageable);
//
// 		// then
// 		assertNotNull(result);
// 		assertEquals(orderDtoList.size(), result.toList().size());
//
// 		//
// 		// Page<Order> responsePage = new PageImpl<>(orders, pageable, orders.size());
// 		// given(orderRepository.findOrderListByUserSeq(anyLong(), pageable)).willReturn(responsePage);
// 		//
// 		// when(orderService.readOrderByUserSeq(userSeq, pageable)).thenReturn(responsePage);
// 		//
// 		// // when
// 		// Page<OrderDto> result = orderService.readOrderByUserSeq(userSeq, pageable);
// 		//
// 		// // then
// 		// assertNotNull(result);
// 		// assertEquals(orders.size(), result.toList().size());
// 	}
//
// 	@Test
// 	void readOrderByOrderSeqTest() {
// 		// given
// 		Long orderSeq = 1L;
// 		Order order = createOrder(); // OrderFactory를 사용
// 		given(orderRepository.findByOrdrSeq(anyLong())).willReturn(order);
//
// 		// when
// 		OrderDto result = orderService.readOrderByOrderSeq(orderSeq);
//
// 		// then
// 		assertNotNull(result);
// 		assertEquals(order.getOrdrSeq(), result.getOrdrSeq());
// 	}
//
// 	// @Test
// 	// void readOrderByAllTest() {
// 	// 	// given
// 	// 	List<Order> orders = createOrderList(); // OrderFactory를 사용
// 	// 	given(orderRepository.findAll()).willReturn(orders);
// 	//
// 	// 	// when
// 	// 	List<OrderDto> result = orderService.readOrderByAll();
// 	//
// 	// 	// then
// 	// 	assertNotNull(result);
// 	// 	assertEquals(orders.size(), result.size());
// 	// }
//
// 	/** factory **/
// 	private User createMockUser() {
// 		return new User("empId001", // empId
// 			"password", // password
// 			"김철수", // name
// 			"chulsu@example.com", // email
// 			"서울시 강남구", // address
// 			"2023-01-01", // joinedAt
// 			"chulsu", // nickname
// 			30000 // mileage
// 		);
// 	}
//
// 	private User createMockUserWithInsufficientMileage() {
// 		return new User("empId002", // empId
// 			"password", // password
// 			"이영희", // name
// 			"younghi@example.com", // email
// 			"서울시 마포구", // address
// 			"2023-02-01", // joinedAt
// 			"younghi", // nickname
// 			500 // mileage
// 		);
// 	}
// }
