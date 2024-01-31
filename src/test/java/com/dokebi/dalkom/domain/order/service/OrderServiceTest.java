// TODO Pagenation 해결될때까지 동결
package com.dokebi.dalkom.domain.order.service;

import static com.dokebi.dalkom.domain.order.factory.OrderFactory.*;
import static com.dokebi.dalkom.domain.order.factory.OrderReadResponseFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

import com.dokebi.dalkom.domain.mileage.entity.MileageHistory;
import com.dokebi.dalkom.domain.mileage.service.MileageService;
import com.dokebi.dalkom.domain.option.service.ProductOptionService;
import com.dokebi.dalkom.domain.order.dto.OrderAdminReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderPageDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderStateUpdateRequest;
import com.dokebi.dalkom.domain.order.dto.OrderUserReadResponse;
import com.dokebi.dalkom.domain.order.entity.Order;
import com.dokebi.dalkom.domain.order.repository.OrderRepository;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.service.ProductService;
import com.dokebi.dalkom.domain.stock.service.ProductStockService;
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
	private Product mockProduct;
	@Mock
	private MileageHistory mileageHistory;

	// Setup your mock data and mock behaviors here
	@BeforeEach
	void setUp() {

	}

	// @Test
	// @DisplayName("주문 생성 서비스 테스트 ")
	// void createOrderTest() {
	// 	// given
	// 	OrderCreateRequest request = createOrderCreateRequest();
	// 	User mockUser = createMockUser();
	//
	// 	given(mockProduct.getPrice()).willReturn(10000);
	// 	given(userService.readUserByUserSeq(anyLong())).willReturn(mockUser);
	// 	given(productService.readProductByProductSeq(anyLong())).willReturn(mockProduct);
	// 	given(mileageService.createMileageHistory(any(), any(), any(), any())).willReturn(mileageHistory);
	// 	given(productOptionService.readProductOptionByPrdtOptionSeq(anyLong())).willReturn(
	// 		new ProductOption(2L, "OP1", "의류 사이즈", "M"));
	// 	doNothing().when(productStockService).checkStock(anyLong(), anyLong(), anyInt());
	//
	// 	// when
	// 	orderService.createOrder(request);
	//
	// 	// then
	// 	verify(orderRepository, times(1)).save(any(Order.class));
	// 	verify(orderDetailService, times(request.getProductSeqList().size())).saveOrderDetail(any(OrderDetail.class));
	// }

	// @Test
	// @DisplayName("주문시 마일리지 부족 서비스 테스트")
	// void createOrderWithMileageLackExceptionTest() {
	// 	// given
	// 	OrderCreateRequest request = createOrderCreateRequest();
	// 	User mockUser = createMockUserWithInsufficientMileage();
	//
	// 	given(mockProduct.getPrice()).willReturn(10000);
	// 	given(userService.readUserByUserSeq(anyLong())).willReturn(mockUser);
	// 	given(productService.readProductByProductSeq(anyLong())).willReturn(mockProduct);
	//
	// 	// when, then
	// 	assertThrows(MileageLackException.class, () -> orderService.createOrder(request));
	//
	// }

	@Test
	@DisplayName("주문 상품 상세 서비스 테스트")
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
	@DisplayName("유저의 주문 목록 조회 서비스 테스트")
	void readOrderByUserSeqTest() {
		// given
		Long userSeq = 1L;
		Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 페이지 당 3개 항목

		List<OrderUserReadResponse> orderList = new ArrayList<>();
		orderList.add(createOrderReadResponse());
		orderList.add(createOrderReadResponse());

		Page<OrderUserReadResponse> responsePage = new PageImpl<>(orderList, pageable, orderList.size());

		when(orderService.readOrderByUserSeq(userSeq, pageable)).thenReturn(responsePage);

		// when
		Page<OrderUserReadResponse> result = orderService.readOrderByUserSeq(userSeq, pageable);

		// then
		assertNotNull(result);
		assertEquals(orderList.size(), result.toList().size());

	}

	// @Test
	// @DisplayName("주문코드로 주문 목록 조회 서비스 테스트")
	// void readOrderByOrderSeqTest() {
	// 	// given
	// 	Long orderSeq = 1L;
	// 	List<OrderDetailReadResponse> responseList = List.of(createOrderDetailReadResponse(),
	// 		createOrderDetailReadResponse());
	//
	// 	when(orderService.readOrderByOrderSeq(orderSeq)).thenReturn(responseList);
	//
	// 	// when
	// 	List<OrderDetailReadResponse> result = orderService.readOrderByOrderSeq(orderSeq);
	//
	// 	// then
	// 	assertNotNull(result);
	// }

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

}
