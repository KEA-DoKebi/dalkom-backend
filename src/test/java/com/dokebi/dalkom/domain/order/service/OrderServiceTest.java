package com.dokebi.dalkom.domain.order.service;

import static com.dokebi.dalkom.domain.order.factory.AuthorizeOrderRequestFactory.*;
import static com.dokebi.dalkom.domain.order.factory.OrderDetailSimpleResponseFactory.*;
import static com.dokebi.dalkom.domain.order.factory.OrderPageDtoFactory.*;
import static com.dokebi.dalkom.domain.order.factory.OrderReadResponseFactory.*;
import static com.dokebi.dalkom.domain.user.factory.UserFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
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
import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.mileage.entity.MileageHistory;
import com.dokebi.dalkom.domain.mileage.service.MileageService;
import com.dokebi.dalkom.domain.option.service.ProductOptionService;
import com.dokebi.dalkom.domain.order.dto.AuthorizeOrderRequest;
import com.dokebi.dalkom.domain.order.dto.OrderAdminReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderCreateRequest;
import com.dokebi.dalkom.domain.order.dto.OrderDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderDetailReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderDetailSimpleResponse;
import com.dokebi.dalkom.domain.order.dto.OrderPageDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderProductRequest;
import com.dokebi.dalkom.domain.order.dto.OrderStateUpdateRequest;
import com.dokebi.dalkom.domain.order.dto.OrderUserReadResponse;
import com.dokebi.dalkom.domain.order.dto.ReceiverDetailDto;
import com.dokebi.dalkom.domain.order.dto.ReceiverInfoRequest;
import com.dokebi.dalkom.domain.order.entity.Order;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.order.exception.PasswordNotValidException;
import com.dokebi.dalkom.domain.order.repository.OrderDetailRepository;
import com.dokebi.dalkom.domain.order.repository.OrderRepository;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.repository.ProductRepository;
import com.dokebi.dalkom.domain.product.service.ProductService;
import com.dokebi.dalkom.domain.stock.service.ProductStockService;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.repository.UserRepository;
import com.dokebi.dalkom.domain.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
	@InjectMocks private OrderService orderService;
	@Mock private OrderRepository orderRepository;
	@Mock private UserRepository userRepository;
	@Mock private OrderDetailRepository orderDetailRepository;
	@Mock private ProductRepository productRepository;
	@Mock private OrderDetailService orderDetailService;
	@Mock private ProductService productService;
	@Mock private ProductStockService productStockService;
	@Mock private ProductOptionService productOptionService;
	@Mock private MileageService mileageService;
	@Mock private UserService userService;
	@Mock private OrderCartService orderCartService;
	@Mock private Product mockProduct;
	@Mock private MileageHistory mileageHistory;
	@Mock private PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("ORDER-001 (사용자별 전체 주문 조회)")
	void readOrderByUserSeqTest() {
		// Given
		Long userSeq = 1L;
		Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 페이지 당 3개 항목

		List<OrderUserReadResponse> orderList = new ArrayList<>();
		orderList.add(createOrderReadResponse());
		orderList.add(createOrderReadResponse());

		Page<OrderUserReadResponse> responsePage = new PageImpl<>(orderList, pageable, orderList.size());
		when(orderRepository.findOrderListByUserSeq(userSeq, pageable)).thenReturn(responsePage);

		// When
		Page<OrderUserReadResponse> result = orderService.readOrderByUserSeq(userSeq, pageable);

		// Then
		assertNotNull(result);
		assertEquals(orderList.size(), result.toList().size());
	}

	@Test
	@DisplayName("ORDER-002 (주문 확인하기)")
	void readProductDetailTest() {
		// Given
		List<OrderPageDetailDto> orderList = List.of(createOrderPageDetailDto(3L, 3L, 100, "집업 자켓 아이보리", 97300));
		ReadProductDetailResponse productDetailResponse = new ReadProductDetailResponse("집업 자켓 아이보리", 97300);
		doNothing().when(productStockService).checkStock(anyLong(), anyLong(), anyInt());
		given(productService.readProduct(anyLong())).willReturn(productDetailResponse);

		// When
		List<OrderPageDetailDto> result = orderService.readProductDetail(orderList);

		// Then
		assertNotNull(result);
		assertEquals(orderList.size(), result.size());
	}

	@Test
	@DisplayName("ORDER-003 (특정 주문 조회)")
	void readOrderByOrderSeqTest() {
		// Given
		Long orderSeq = 1L;
		List<OrderDetailDto> orderDetailDtoList = List.of(
			new OrderDetailDto(1L, "Product1", "image1.jpg", 123L, "Detail1", null, 1L, 2, 25000, "CONFIRMED"),
			new OrderDetailDto(2L, "Product2", "image2.jpg", 456L, "Detail2", null, 1L, 3, 30000, "CONFIRMED")
		);
		ReceiverDetailDto receiverDetailDto = new ReceiverDetailDto("John Doe", "123-456-7890", "123 Main St",
			"Morning delivery");
		int totalPrice = 55000;

		when(orderDetailService.readOrderDetailDtoByOrderSeq(orderSeq)).thenReturn(orderDetailDtoList);
		when(orderRepository.findReceiverDetailDtoByOrdrSeq(orderSeq)).thenReturn(
			java.util.Optional.of(receiverDetailDto));

		// When
		OrderDetailReadResponse result = orderService.readOrderByOrderSeq(orderSeq);

		// Then
		assertNotNull(result);
		assertEquals(orderDetailDtoList, result.getOrderDetailList());
		assertEquals(receiverDetailDto, result.getReceiverDetail());
		assertEquals(totalPrice, result.getTotalPrice());

		verify(orderDetailService).readOrderDetailDtoByOrderSeq(orderSeq);
		verify(orderRepository).findReceiverDetailDtoByOrdrSeq(orderSeq);
	}

	@Test
	@DisplayName("ORDER-004 (관리자 전체 주문 조회)")
	void readOrderByAllTest() {
		// Given
		Pageable pageable = PageRequest.of(0, 3);

		Page<OrderAdminReadResponse> orderReadResponseList = createOrderList(); // OrderFactory를 사용
		given(orderRepository.findAllOrderList(pageable)).willReturn(orderReadResponseList);

		// When
		Page<OrderAdminReadResponse> result = orderService.readOrderByAll(pageable);

		// Then
		assertNotNull(result);
		assertEquals(orderReadResponseList.getSize(), result.getSize());
	}

	@Test
	@DisplayName("ORDER-005 (결제 하기)")
	void createOrderTest() {
		// Given
		Long userSeq = 1L;
		ReceiverInfoRequest receiverInfoRequest = new ReceiverInfoRequest("receiverName", "receiverAddress",
			"receiverMobileNum", "receiverMemo");
		OrderProductRequest orderProductRequest1 = new OrderProductRequest(1L, 1L, 1L, 1);
		OrderProductRequest orderProductRequest2 = new OrderProductRequest(2L, 2L, 2L, 1);
		List<OrderProductRequest> orderProductRequestList = Arrays.asList(orderProductRequest1, orderProductRequest2);
		OrderCreateRequest request = new OrderCreateRequest(receiverInfoRequest, orderProductRequestList);
		User user = new User("empId", "123456a!", "name",
			"email@email.com", "Address", LocalDate.now(),
			"nickname", 1000000000);
		user.setUserSeq(userSeq);

		Category category = new Category(
			"name", 1L, "imageUrl"
		);
		Product product = new Product(
			category, "name", 1000, "info", "imageUrl", "comapny", "state"
		);

		// productStockService의 checkStock이 void를 날리게 하기 위해 doNothing 선언했다.
		doNothing().when(productStockService).checkStock(anyLong(), anyLong(), anyInt());
		when(userRepository.findByUserSeq(userSeq)).thenReturn(Optional.of(user));
		when(productRepository.findProductByProductSeq(orderProductRequest1.getProductSeq()))
			.thenReturn(Optional.of(product));
		when(productRepository.findProductByProductSeq(orderProductRequest2.getProductSeq()))
			.thenReturn(Optional.of(product));

		// When
		orderService.createOrder(userSeq, request);

		// Then
		verify(userRepository).findByUserSeq(userSeq);
		verify(orderRepository).save(any(Order.class));
		verify(orderDetailRepository, times(2)).save(any(OrderDetail.class));
		verify(mileageService).createMileageHistory(any(), anyInt(), anyInt(), anyString());
		verify(orderCartService).deleteOrderCart(any());
	}

	@Test
	@DisplayName("ORDER-006 (특정 주문 상태 수정)")
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

	// ORDER-007 (사용자 주문 검색) 구현 안하기로 함

	@Test
	@DisplayName("ORDER-008 (주문 취소)")
	void deleteOrderByOrderSeqTest() {
		// Given
		Long orderSeq = 1L;
		Order order = createOrder();
		order.setOrderState(OrderState.CONFIRMED.getState());
		User user = createMockUser();
		order.setUser(user);

		when(orderRepository.findOrderByOrdrSeq(orderSeq)).thenReturn(Optional.of(order));

		// When
		orderService.deleteOrderByOrderSeq(orderSeq);

		verify(orderRepository).findOrderByOrdrSeq(orderSeq);
	}

	@Test
	@DisplayName("ORDER-009 (환불 확인)")
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
	@DisplayName("ORDER-010 (결제 비밀번호 인증)")
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

	// ORDER-011 (리뷰용 단일 주문상세 조회)는 OrderDetailService에 구현

	@Test
	@DisplayName("ORDER-012 (취소/환불 목록 조회)")
	void readOrderCancelListByUserSeqTest() {
		// Given
		Long userSeq = 1L;
		Pageable pageable = PageRequest.of(0, 10);
	}

	@Test
	@DisplayName("ORDER-013 (관리자 주문 목록 검색)")
	void readOrderListByAdminSearchTest() {
		Pageable pageable = Pageable.unpaged();

		when(orderRepository.findAllOrderList(pageable)).thenReturn(Page.empty());
		Page<OrderAdminReadResponse> result = orderService.readOrderListByAdminSearch(null, null, pageable);

		assertNotNull(result);
		verify(orderRepository).findAllOrderList(pageable);
	}
}
