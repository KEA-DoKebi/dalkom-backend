package com.dokebi.dalkom.domain.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.mileage.exception.MileageLackException;
import com.dokebi.dalkom.domain.mileage.service.MileageService;
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.option.service.ProductOptionService;
import com.dokebi.dalkom.domain.order.dto.OrderCreateRequest;
import com.dokebi.dalkom.domain.order.dto.OrderDto;
import com.dokebi.dalkom.domain.order.dto.OrderPageDetailDto;
import com.dokebi.dalkom.domain.order.entity.Order;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.order.repository.OrderRepository;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.service.ProductService;
import com.dokebi.dalkom.domain.stock.entity.ProductStock;
import com.dokebi.dalkom.domain.stock.service.ProductStockService;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {
	private final OrderRepository orderRepository;
	private final OrderDetailService orderDetailService;
	private final ProductOptionService productOptionService;
	private final ProductService productService;
	private final ProductStockService productStockService;
	private final MileageService mileageService;
	private final UserService userService;

	// 결제 하기
	@Transactional
	public void createOrder(OrderCreateRequest request) {

		int totalPrice = 0;

		// totalPrice를 먼저 계산해준다.
		for (int i = 0; i < request.getProductSeqList().size(); i++) {
			totalPrice += calculateProductPrice(request, i);
		}

		// 어떤 사용자인지 조회
		User user = userService.readUserByUserSeq(request.getUserSeq());

		// 해당 사용자가 보유한 마일리지와 주문의 총 가격과 비교
		if (totalPrice <= user.getMileage()) {

			// 주문을 위한 entity 생성 후 저장
			Order order = new Order(
				user,
				request.getReceiverName(),
				request.getReceiverAddress(),
				request.getReceiverMobileNum(),
				request.getReceiverMemo(),
				totalPrice
			);
			orderRepository.save(order);

			// 주문에 속한 세부 주문( 주문에 속한 각 상품별 데이터 ) entity 생성 후 저장
			for (int i = 0; i < request.getProductSeqList().size(); i++) {

				// 상세 주문 내역 생성
				OrderDetail orderDetail = createOrderDetail(order, request, i);

				// 각 세부 주문 DB에 저장
				orderDetailService.saveOrderDetail(orderDetail);
			}

			// 사용한 마일리지 감소 후 변경된 사용자 정보 업데이트
			Integer amount = (user.getMileage() - totalPrice);

			mileageService.createMileageHistoryAndUpdateUser(user.getUserSeq(), amount, "2");
		} else {
			throw new MileageLackException();
		}
	}

	// 주문하기
	public List<OrderPageDetailDto> readProductDetail(List<OrderPageDetailDto> orderList) {
		List<OrderPageDetailDto> result = new ArrayList<>();
		orderList.forEach(order -> {
			// 선택한 상품
			Long productSeq = order.getProductSeq();
			// 선택한 옵션
			Long optionSeq = order.getProductOptionSeq();
			// 선택한 개수
			Integer productAmount = order.getProductAmount();
			// 재고 확인
			productStockService.checkStock(productSeq, optionSeq, productAmount);
			
			// 사용자가 주문한 상품에 대한 정보 조회
			ReadProductDetailResponse productInfo = productService.readProduct(order.getProductSeq());

			// OrderPageDetailDto로 변환
			OrderPageDetailDto orderPageDetailDTO =new OrderPageDetailDto(productSeq,optionSeq,productAmount,
				productInfo.getName(),productInfo.getPrice(),productInfo.getPrice() * order.getProductAmount());
			
			result.add(orderPageDetailDTO);
		});

		return result;
	}

	// 사용자별 상품 조회
	public List<OrderDto> readOrderByUserSeq(Long userSeq) {
		List<Order> orders = orderRepository.findOrderListByUserSeq(userSeq);

		return orders.stream()
			.map(order ->
				new OrderDto(order.getOrdrSeq(),
					order.getReceiverName(),
					order.getReceiverAddress(),
					order.getReceiverMobileNum(),
					order.getReceiverMemo(),
					order.getTotalPrice()))
			.collect(Collectors.toList());
	}

	// 주문 별 주문 조회
	public OrderDto readOrderByOrderSeq(Long orderSeq) {
		Order order = orderRepository.findByOrdrSeq(orderSeq);

		return new OrderDto(order.getOrdrSeq(),
			order.getReceiverName(),
			order.getReceiverAddress(),
			order.getReceiverMobileNum(),
			order.getReceiverMemo(),
			order.getTotalPrice());
	}
	// 주문 전체 조회

	public List<OrderDto> readOrderByAll() {
		List<Order> orders = orderRepository.findAll();

		return orders.stream()
			.map(order ->
				new OrderDto(order.getOrdrSeq(),
					order.getReceiverName(),
					order.getReceiverAddress(),
					order.getReceiverMobileNum(),
					order.getReceiverMemo(),
					order.getTotalPrice()))
			.collect(Collectors.toList());
	}



	private Integer calculateProductPrice(OrderCreateRequest request,int i) {
		Product product = productService.readByProductSeq(request.getProductSeqList().get(i));

		Long prdtOptionSeq = request.getPrdtOptionSeqList().get(i);
		Integer amount = request.getAmountList().get(i);
		Integer price = product.getPrice();

		productStockService.checkStock(product.getProductSeq(), prdtOptionSeq, amount);

		return amount * price;
	}

	private OrderDetail createOrderDetail(Order order,OrderCreateRequest request,int i) {
		Long productSeq = request.getProductSeqList().get(i);
		Long prdtOptionSeq = request.getPrdtOptionSeqList().get(i);
		Integer amount = request.getAmountList().get(i);

		Product product = productService.readByProductSeq(productSeq);
		ProductOption productOption = productOptionService.readProductOptionByPrdtOptionSeq(prdtOptionSeq);
		Integer price = product.getPrice();

		OrderDetail orderDetail = new OrderDetail(
			order,
			product,
			productOption,
			amount,
			price
		);
		ProductStock productStock = new ProductStock(product, productOption, amount);
		productStockService.createStock(productStock);
		
		return orderDetail;
	}
}


