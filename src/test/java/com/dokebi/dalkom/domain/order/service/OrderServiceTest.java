package com.dokebi.dalkom.domain.order.service;

import static com.dokebi.dalkom.domain.order.factory.AuthorizeOrderRequestFactory.*;
import static com.dokebi.dalkom.domain.order.factory.OrderCreateRequestFactory.*;
import static com.dokebi.dalkom.domain.order.factory.OrderDetailSimpleResponseFactory.*;
import static com.dokebi.dalkom.domain.order.factory.OrderPageDtoFactory.*;
import static com.dokebi.dalkom.domain.order.factory.OrderReadResponseFactory.*;
import static com.dokebi.dalkom.domain.user.factory.UserFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dokebi.dalkom.common.magicnumber.MileageHistoryState;
import com.dokebi.dalkom.common.magicnumber.OrderState;
import com.dokebi.dalkom.domain.cart.service.OrderCartService;
import com.dokebi.dalkom.domain.mileage.entity.MileageHistory;
import com.dokebi.dalkom.domain.mileage.exception.MileageLackException;
import com.dokebi.dalkom.domain.mileage.service.MileageService;
import com.dokebi.dalkom.domain.option.service.ProductOptionService;
import com.dokebi.dalkom.domain.order.dto.AuthorizeOrderRequest;
import com.dokebi.dalkom.domain.order.dto.OrderAdminReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderCreateRequest;
import com.dokebi.dalkom.domain.order.dto.OrderDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderDetailReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderDetailSimpleResponse;
import com.dokebi.dalkom.domain.order.dto.OrderPageDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderStateUpdateRequest;
import com.dokebi.dalkom.domain.order.dto.OrderUserReadResponse;
import com.dokebi.dalkom.domain.order.dto.ReceiverDetailDto;
import com.dokebi.dalkom.domain.order.entity.Order;
import com.dokebi.dalkom.domain.order.exception.PasswordNotValidException;
import com.dokebi.dalkom.domain.order.repository.OrderRepository;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.service.ProductService;
import com.dokebi.dalkom.domain.stock.service.ProductStockService;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
	@InjectMocks
	private OrderService orderService;

	@Mock
	private OrderRepository orderRepository;
	@Mock
	private OrderDetailService orderDetailService;
	@Mock
	private ProductService productService;
	@Mock
	private ProductStockService productStockService;
	@Mock
	private ProductOptionService productOptionService;
	@Mock
	private MileageService mileageService;
	@Mock
	private UserService userService;

	@Mock
	private OrderCartService orderCartService;

	@Mock
	private Product mockProduct;
	@Mock
	private MileageHistory mileageHistory;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private Order mockOrder;

	// @Test
	// @DisplayName("주문 생성 서비스 테스트 ")
	// void createOrderTest() {
	// 	// given
	// 	OrderCreateRequest request = createOrderCreateRequest();
	// 	User mockUser = createMockUser();
	// 	Long userSeq = 1L;
	//
	// 	given(mockProduct.getPrice()).willReturn(10000);
	// 	given(userService.readUserByUserSeq(anyLong())).willReturn(mockUser);
	// 	given(productService.readProductByProductSeq(anyLong())).willReturn(mockProduct);
	// 	doNothing().when(mileageService).createMileageHistory(mockUser, any(), any(), any());
	// 	given(productOptionService.readProductOptionByPrdtOptionSeq(anyLong())).willReturn(
	// 		new ProductOption(2L, "OP1", "의류 사이즈", "M"));
	// 	doNothing().when(productStockService).checkStock(anyLong(), anyLong(), anyInt());
	//
	// 	// when
	// 	orderService.createOrder(userSeq, request);
	//
	// 	// then
	// 	verify(orderRepository).save(any(Order.class));
	// }

	// @Test
	// @DisplayName("결제하기- 마일리지 충분")
	// void createOrderWithSufficientMileage() {
	// 	// 가짜 데이터 설정
	// 	Long userSeq = 1L;
	// 	Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 페이지 당 3개 항목
	//
	// 	OrderProductRequest orderProductRequest = createOrderProductRequest();
	// 	OrderCreateRequest orderCreateRequest = createOrderCreateRequest();
	// 	User user = createMockUser();
	// 	Order order = createOrder();
	// 	int orderTotalPrice = calculateSampleOrderTotalPrice(orderProductRequest);
	// 	OrderCartDeleteRequest orderCartDeleteRequest = createOrderCartDeleteRequest();
	//
	// 	// 목 객체 설정
	// 	when(userService.readUserByUserSeq(userSeq)).thenReturn(user);
	// 	when(orderRepository.findCancelRefundListByUserSeq(userSeq, pageable))
	// 		.thenReturn(createMockPageOrder());
	// 	doNothing().when(mileageService).createMileageHistory(any(User.class), anyInt(), anyInt(), anyString());
	// 	doNothing().when(orderCartService).deleteOrderCart(orderCartDeleteRequest);
	//
	// 	// 테스트 메서드 실행
	// 	Integer updatedMileage = orderService.createOrder(userSeq, orderCreateRequest);
	// 	// 결과 검증
	// 	assertEquals(user.getMileage() - orderTotalPrice, updatedMileage);
	// 	verify(orderRepository.save(any(Order.class)));
	// 	verify(orderDetailService, times(orderCreateRequest.getOrderProductRequestList().size())).saveOrderDetail(
	// 		any(OrderDetail.class));
	// 	verify(mileageService).createMileageHistory(any(User.class), eq(orderTotalPrice), anyInt(),
	// 		eq(MileageHistoryState.USED.getState()));
	// 	verify(orderCartService).deleteOrderCart(orderCartDeleteRequest);
	// }

	@Test
	@DisplayName("주문시 마일리지 부족 서비스 테스트")
	void createOrderWithInsufficientMileage() {
		// given
		OrderCreateRequest request = createOrderCreateRequest();
		User mockUser = createMockUserWithInsufficientMileage();
		Long userSeq = 1L;

		given(mockProduct.getPrice()).willReturn(10000);
		given(userService.readUserByUserSeq(anyLong())).willReturn(mockUser);
		given(productService.readProductByProductSeq(anyLong())).willReturn(mockProduct);

		// when, then
		assertThrows(MileageLackException.class, () -> orderService.createOrder(userSeq, request));

	}

	@Test
	@DisplayName("주문하기 테스트")
	void readProductDetailTest() {
		// given
		List<OrderPageDetailDto> orderList = List.of(createOrderPageDetailDto(3L, 3L, 100, "집업 자켓 아이보리", 97300));
		ReadProductDetailResponse productDetailResponse = new ReadProductDetailResponse("집업 자켓 아이보리", 97300);
		doNothing().when(productStockService).checkStock(anyLong(), anyLong(), anyInt());
		given(productService.readProduct(anyLong())).willReturn(productDetailResponse);

		// when
		List<OrderPageDetailDto> result = orderService.readProductDetail(orderList);

		// then
		assertNotNull(result);
		assertEquals(orderList.size(), result.size());
	}

	@Test
	@DisplayName("사용자별 주문 전체 조회 테스트")
	void readOrderByUserSeqTest() {
		// given
		Long userSeq = 1L;
		Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 페이지 당 3개 항목

		List<OrderUserReadResponse> orderList = new ArrayList<>();
		orderList.add(createOrderReadResponse());
		orderList.add(createOrderReadResponse());

		Page<OrderUserReadResponse> responsePage = new PageImpl<>(orderList, pageable, orderList.size());
		when(orderRepository.findOrderListByUserSeq(userSeq, pageable)).thenReturn(responsePage);

		// when
		Page<OrderUserReadResponse> result = orderService.readOrderByUserSeq(userSeq, pageable);

		// then
		assertNotNull(result);
		assertEquals(orderList.size(), result.toList().size());

	}

	@Test
	@DisplayName("주문별 상세 조회 서비스 테스트")
	void readOrderByOrderSeqTest() {
		// given
		Long orderSeq = 1L;
		List<OrderDetailDto> orderDetailDtoList = List.of(
			new OrderDetailDto(1L, "Product1", "image1.jpg", 123L, "Detail1", null, 1L, 2, 25000, "CONFIRMED"),
			new OrderDetailDto(2L, "Product2", "image2.jpg", 456L, "Detail2", null, 1L, 3, 30000, "CONFIRMED")
		);
		ReceiverDetailDto receiverDetailDto = new ReceiverDetailDto("John Doe", "123-456-7890", "123 Main St",
			"Morning delivery");
		int totalPrice = 55000;

		// Mocking
		when(orderDetailService.readOrderDetailDtoByOrderSeq(orderSeq)).thenReturn(orderDetailDtoList);
		when(orderRepository.findReceiverDetailDtoByOrdrSeq(orderSeq)).thenReturn(
			java.util.Optional.of(receiverDetailDto));

		// when
		OrderDetailReadResponse result = orderService.readOrderByOrderSeq(orderSeq);

		// then
		assertNotNull(result);
		assertEquals(orderDetailDtoList, result.getOrderDetailList());
		assertEquals(receiverDetailDto, result.getReceiverDetail());
		assertEquals(totalPrice, result.getTotalPrice());

		// Verify that the methods were called with the expected arguments
		verify(orderDetailService).readOrderDetailDtoByOrderSeq(orderSeq);
		verify(orderRepository).findReceiverDetailDtoByOrdrSeq(orderSeq);
	}

	@Test
	@DisplayName("모든 주문 목록 조회 서비스 테스트")
	void readOrderByAllTest() {
		Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 페이지 당 3개 항목

		// given
		Page<OrderAdminReadResponse> orderReadResponseList = createOrderList(); // OrderFactory를 사용
		given(orderRepository.findAllOrderList(pageable)).willReturn(orderReadResponseList);

		// when
		Page<OrderAdminReadResponse> result = orderService.readOrderByAll(pageable);

		// then
		assertNotNull(result);
		assertEquals(orderReadResponseList.getSize(), result.getSize());
	}

	@Test
	@DisplayName("주문 상태 수정 서비스 테스트")
	void updateOrderStateTest() {
		Long orderSeq = 1L;
		OrderStateUpdateRequest orderStateUpdateRequest = new OrderStateUpdateRequest();
		orderStateUpdateRequest.setOrderState("11");

		Order order = createOrder();
		order.setOrderState("12");

		when(orderRepository.findById(orderSeq)).thenReturn(java.util.Optional.of(order));
		when(orderRepository.save(any(Order.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

		orderService.updateOrderState(orderSeq, orderStateUpdateRequest);
		verify(orderRepository, times(1)).findById(orderSeq);
		verify(orderRepository, times(1)).save(order);

	}

	@Test
	@DisplayName("주문 상세 seq 로 조회 ")
	void readOrderDetailByOrderDetailSeqTest() {
		Long orderDetailSeq = 1L;
		OrderDetailSimpleResponse expectedResponse = createOrderDetailSimpleResponse();

		// Mocking
		when(orderDetailService.readOrderDetailSimpleResponseByOrderDetailSeq(orderDetailSeq)).thenReturn(
			expectedResponse);

		// when
		OrderDetailSimpleResponse result = orderService.readOrderDetailByOrderDetailSeq(orderDetailSeq);

		// then
		assertNotNull(result);
		assertEquals(expectedResponse, result);

		// Verify that the method was called with the expected argument
		verify(orderDetailService).readOrderDetailSimpleResponseByOrderDetailSeq(orderDetailSeq);

	}

	// @Test
	// @DisplayName("취소 / 환불 리스트 조회")
	// void readOrderCancelListByUserSeqTest() {
	// 	// Given
	// 	Long userSeq = 1L;
	// 	Pageable pageable = PageRequest.of(0, 10);
	//
	// 	// Create a sample order and order detail for testing
	// 	Order order = createOrder();
	// 	OrderDetail orderDetail = createOrderDetailReadResponse();
	//
	// 	// Mocking repository response
	// 	Page<Order> orderPage = new PageImpl<>(Collections.singletonList(order), pageable, 1);
	// 	when(orderRepository.findCancelRefundListByUserSeq(userSeq, pageable)).thenReturn(orderPage);
	//
	// 	// Mocking order detail service response
	// 	when(orderDetailService.readFirstOrderDetailByOrderSeq(any())).thenReturn(orderDetail);
	//
	// 	// When
	// 	Page<CancelRefundReadResponse> resultPage = orderService.readOrderCancelListByUserSeq(userSeq, pageable);
	//
	// 	// Then
	// 	assertEquals(1, resultPage.getTotalElements());
	// 	assertEquals(1, resultPage.getTotalPages());
	//
	// 	CancelRefundReadResponse expectedResult = createExpectedResult(order, orderDetail);
	// 	assertEquals(Collections.singletonList(expectedResult), resultPage.getContent());
	//
	// 	// Verify that the repository method was called with the correct arguments
	// 	verify(orderRepository).findCancelRefundListByUserSeq(userSeq, pageable);
	//
	// 	// Verify that the order detail service method was called with the correct order sequence
	// 	verify(orderDetailService).readFirstOrderDetailByOrderSeq(order.getOrdrSeq());
	// }

	@Test
	@DisplayName("유저에서 주문 검색 조회 서비스")
	void readOrderListByUserSearchTest() {
		Pageable pageable = Pageable.unpaged();
		String receiverName = "receiverName";

		// Mocking
		when(orderRepository.findAllOrderListByReceiverName(receiverName, pageable)).thenReturn(Page.empty());

		// When
		Page<OrderUserReadResponse> result = orderService.readOrderListByUserSearch(receiverName, pageable);

		// Then
		assertNotNull(result);
		verify(orderRepository).findAllOrderListByReceiverName(receiverName, pageable);
	}

	@Test
	@DisplayName("관리자 주문 검색 조회 서비스 전체 ")
	void readOrderListByAdminSearchTest() {
		Pageable pageable = Pageable.unpaged();

		when(orderRepository.findAllOrderList(pageable)).thenReturn(Page.empty());
		Page<OrderAdminReadResponse> result = orderService.readOrderListByAdminSearch(null, null, pageable);

		assertNotNull(result);
		verify(orderRepository).findAllOrderList(pageable);
	}

	@Test
	@DisplayName("관리자 주문 검색 조회 서비스 수령인")
	void readOrderListByAdminSearchReceiverNameTest() {
		Pageable pageable = Pageable.unpaged();
		String receiverName = "receiverName";
		when(orderRepository.findOrderListByAdminWithReceiverName(receiverName, pageable)).thenReturn(Page.empty());
		Page<OrderAdminReadResponse> result = orderService.readOrderListByAdminSearch(receiverName, null, pageable);

		assertNotNull(result);
		verify(orderRepository).findOrderListByAdminWithReceiverName(receiverName, pageable);
	}

	@Test
	@DisplayName("관리자 주문 검색 조회 서비스 이름")
	void readOrderListByAdminSearchNameTest() {
		Pageable pageable = Pageable.unpaged();
		String name = "name";
		when(orderRepository.findOrderListByAdminWithName(name, pageable)).thenReturn(Page.empty());
		Page<OrderAdminReadResponse> result = orderService.readOrderListByAdminSearch(null, name, pageable);

		assertNotNull(result);
		verify(orderRepository).findOrderListByAdminWithName(name, pageable);
	}

	@Test
	@DisplayName("주문 삭제 - 주문 취소")
	void deleteOrderByOrderSeqTest() {
		// Given
		Long orderSeq = 1L;
		Order order = createOrder();
		order.setOrderState(OrderState.CONFIRMED.getState());
		User user = createMockUser();
		order.setUser(user);

		when(orderRepository.findOrderByOrdrSeq(orderSeq)).thenReturn(Optional.of(order));
		// Mocking cancelOrder method to do nothing

		// When
		orderService.deleteOrderByOrderSeq(orderSeq);

		verify(orderRepository).findOrderByOrdrSeq(orderSeq);
	}

	@Test
	@DisplayName("환불 확인 (상품 수령 후)")
	void confirmRefundByOrderSeqTest() {
		// Given
		Long orderSeq = 1L;
		Order mockOrder = mock(Order.class); // Order 클래스의 목을 생성
		when(mockOrder.getOrderState()).thenReturn(OrderState.RETURNED.getState()); // 목 오더의 상태를 반환하도록 설정
		when(orderRepository.findOrderByOrdrSeq(orderSeq)).thenReturn(Optional.of(mockOrder));
		when(mockOrder.getTotalPrice()).thenReturn(200); // 목 오더에서 when()을 사용하여 가격 반환 설정
		User mockUser = createMockUser();
		mockUser.setMileage(200);
		when(mockOrder.getUser()).thenReturn(mockUser);

		doNothing().when(mileageService).createMileageHistory(any(User.class), anyInt(), anyInt(), anyString());

		// When
		orderService.confirmRefundByOrderSeq(orderSeq);

		// Then
		verify(mileageService).createMileageHistory(
			eq(mockUser),
			eq(200),
			eq(400),
			eq(MileageHistoryState.REFUNDED.getState())
		);

	}

	@Test
	@DisplayName("결제시 비밀번호 인증")
	void authorizeOrderByPasswordTest() {
		Long userSeq = 1L;
		User mockUser = createMockUser();
		when(userService.readUserByUserSeq(userSeq)).thenReturn(mockUser);
		AuthorizeOrderRequest authorizeOrderRequest = createAuthorizeOrderRequest();

		// Mock the password not matching
		when(passwordEncoder.matches(eq(authorizeOrderRequest.getPassword()), anyString())).thenReturn(false);

		// When / Then
		assertThrows(PasswordNotValidException.class, () ->
			orderService.authorizeOrderByPassword(userSeq, authorizeOrderRequest)
		);

	}

	private static int getProductPriceByProductSeq(long productSeq) {
		// 가짜 데이터 생성 로직 추가
		// 상품 시퀀스에 해당하는 상품의 가격을 반환하도록 설정할 수 있습니다.
		// 예를 들어, 상품 시퀀스에 따라 미리 정의된 맵에서 가격을 가져오는 등의 방식으로 설정 가능합니다.
		return 10000; // 가짜 데이터: 상품 가격이 10,000원으로 가정
	}

	private Page<Order> createMockPageOrder() {
		// Create mock data for Page<Order>
		List<Order> orderList = Arrays.asList(
			createOrder(),
			createOrder()
		);

		return new PageImpl<>(orderList);
	}

}
