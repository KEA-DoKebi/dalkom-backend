package com.dokebi.dalkom.domain.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.magicnumber.MileageHistoryState;
import com.dokebi.dalkom.common.magicnumber.OrderState;
import com.dokebi.dalkom.domain.cart.dto.OrderCartDeleteRequest;
import com.dokebi.dalkom.domain.cart.service.OrderCartService;
import com.dokebi.dalkom.domain.mileage.exception.MileageLackException;
import com.dokebi.dalkom.domain.mileage.service.MileageService;
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.option.service.ProductOptionService;
import com.dokebi.dalkom.domain.order.dto.AuthorizeOrderRequest;
import com.dokebi.dalkom.domain.order.dto.CancelRefundReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderAdminReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderCreateRequest;
import com.dokebi.dalkom.domain.order.dto.OrderDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderDetailReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderDirectCreateRequest;
import com.dokebi.dalkom.domain.order.dto.OrderDirectProductRequest;
import com.dokebi.dalkom.domain.order.dto.OrderPageDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderProductRequest;
import com.dokebi.dalkom.domain.order.dto.OrderStateUpdateRequest;
import com.dokebi.dalkom.domain.order.dto.OrderUserReadResponse;
import com.dokebi.dalkom.domain.order.dto.ReceiverDetailDto;
import com.dokebi.dalkom.domain.order.entity.Order;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.order.exception.InvalidOrderStateException;
import com.dokebi.dalkom.domain.order.exception.OrderNotFoundException;
import com.dokebi.dalkom.domain.order.exception.PasswordNotValidException;
import com.dokebi.dalkom.domain.order.repository.OrderDetailRepository;
import com.dokebi.dalkom.domain.order.repository.OrderRepository;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.exception.ProductNotFoundException;
import com.dokebi.dalkom.domain.product.repository.ProductRepository;
import com.dokebi.dalkom.domain.product.service.ProductService;
import com.dokebi.dalkom.domain.stock.service.ProductStockService;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.exception.UserNotFoundException;
import com.dokebi.dalkom.domain.user.repository.UserRepository;
import com.dokebi.dalkom.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class OrderService {
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final OrderDetailService orderDetailService;
	private final ProductOptionService productOptionService;
	private final ProductService productService;
	private final ProductStockService productStockService;
	private final MileageService mileageService;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final OrderCartService orderCartService;

	// ORDER-001 (사용자별 전체 주문 조회)
	public Page<OrderUserReadResponse> readOrderByUserSeq(Long userSeq, Pageable pageable) {
		return orderRepository.findOrderListByUserSeq(userSeq, pageable);
	}

	// ORDER-002 (주문 확인하기)
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

			//option detail 조회
			String optionDetail = productOptionService.readOptionDetailByPdtOptionSeq(optionSeq);

			// OrderPageDetailDto로 변환
			OrderPageDetailDto orderPageDetailDTO = new OrderPageDetailDto(productSeq, optionSeq, productAmount,
				productInfo.getName(), productInfo.getPrice(), optionDetail,
				productInfo.getPrice() * order.getProductAmount());

			result.add(orderPageDetailDTO);
		});

		return result;
	}

	// ORDER-003 (특정 주문 조회)
	public OrderDetailReadResponse readOrderByOrderSeq(Long orderSeq) {
		List<OrderDetailDto> orderDetailDtoList = orderDetailService.readOrderDetailDtoByOrderSeq(orderSeq);
		ReceiverDetailDto receiverDetailDto = orderRepository.findReceiverDetailDtoByOrdrSeq(orderSeq)
			.orElseThrow(OrderNotFoundException::new);
		int totalPrice = 0;

		for (OrderDetailDto orderDetail : orderDetailDtoList) {
			totalPrice += orderDetail.getTotalPrice();
		}

		return new OrderDetailReadResponse(orderDetailDtoList,
			receiverDetailDto, totalPrice);
	}

	// ORDER-004 (관리자 전체 주문 조회)
	public Page<OrderAdminReadResponse> readOrderByAll(Pageable pageable) {
		return orderRepository.findAllOrderList(pageable);
	}

	// ORDER-005 (결제 하기)
	@Transactional
	public Integer createOrder(Long userSeq, OrderCreateRequest request) {
		int orderTotalPrice = 0;
		OrderCartDeleteRequest orderCartDeleteRequest = new OrderCartDeleteRequest(new ArrayList<>());

		// orderTotalPrice를 먼저 계산해준다.
		for (OrderProductRequest orderProduct : request.getOrderProductRequestList()) {
			orderTotalPrice += calculateProductPrice(orderProduct);
		}

		for (OrderProductRequest orderProduct : request.getOrderProductRequestList()) {
			Long orderCartSeq = orderProduct.getOrderCartSeq();
			orderCartDeleteRequest.getOrderCartSeqList().add(orderCartSeq);
		}

		// 사용자 정보 조회
		User user = userRepository.findByUserSeq(userSeq).orElseThrow(UserNotFoundException::new);

		// 해당 사용자가 보유한 마일리지와 주문의 총 가격과 비교
		if (orderTotalPrice <= user.getMileage()) {

			// 주문을 위한 entity 생성 후 저장
			Order order = new Order(user, request.getReceiverInfoRequest().getReceiverName(),
				request.getReceiverInfoRequest().getReceiverAddress(),
				request.getReceiverInfoRequest().getReceiverMobileNum(),
				request.getReceiverInfoRequest().getReceiverMemo(),
				orderTotalPrice);
			order.setOrderState(OrderState.CONFIRMED.getState());
			orderRepository.save(order);

			// 주문에 속한 세부 주문( 주문에 속한 각 상품별 데이터 ) entity 생성 후 저장
			for (OrderProductRequest orderProduct : request.getOrderProductRequestList()) {

				// 상세 주문 내역 생성
				OrderDetail orderDetail = createOrderDetail(order, orderProduct);

				// 각 세부 주문 DB에 저장
				orderDetailRepository.save(orderDetail);
			}

			// 사용한 마일리지 감소 후 변경된 사용자 정보 업데이트
			Integer totalMileage = (user.getMileage() - orderTotalPrice);

			mileageService.createMileageHistory(user, orderTotalPrice, totalMileage,
				MileageHistoryState.USED.getState());

			//장바구니의 상품 삭제
			orderCartService.deleteOrderCart(orderCartDeleteRequest);

			// 사용자의 마일리지 업데이트
			user.setMileage(totalMileage);
			return user.getMileage();

		} else {
			throw new MileageLackException();
		}
	}

	// 직접 결제 하기
	@Transactional
	public Integer createDirectOrder(Long userSeq, OrderDirectCreateRequest request) {
		log.info("직접 결제하기 log");
		log.info(request);
		int orderTotalPrice = 0;
		// orderTotalPrice를 먼저 계산해준다.
		for (OrderDirectProductRequest orderDirectProduct : request.getOrderDirectProductRequestList()) {
			orderTotalPrice += calculateDirectProductPrice(orderDirectProduct);
		}

		// 사용자 정보 조회
		User user = userService.readUserByUserSeq(userSeq);

		// 해당 사용자가 보유한 마일리지와 주문의 총 가격과 비교
		if (orderTotalPrice <= user.getMileage()) {

			// 주문을 위한 entity 생성 후 저장
			Order order = new Order(user, request.getReceiverInfoRequest().getReceiverName(),
				request.getReceiverInfoRequest().getReceiverAddress(),
				request.getReceiverInfoRequest().getReceiverMobileNum(),
				request.getReceiverInfoRequest().getReceiverMemo(),
				orderTotalPrice);
			order.setOrderState(OrderState.CONFIRMED.getState());
			orderRepository.save(order);

			// 주문에 속한 세부 주문( 주문에 속한 각 상품별 데이터 ) entity 생성 후 저장
			for (OrderDirectProductRequest orderDirectProductRequest : request.getOrderDirectProductRequestList()) {

				// 상세 주문 내역 생성
				OrderDetail orderDetail = createOrderDirectDetail(order, orderDirectProductRequest);

				// 각 세부 주문 DB에 저장
				orderDetailService.saveOrderDetail(orderDetail);
			}

			// 사용한 마일리지 감소 후 변경된 사용자 정보 업데이트
			Integer totalMileage = (user.getMileage() - orderTotalPrice);

			mileageService.createMileageHistory(user, orderTotalPrice, totalMileage,
				MileageHistoryState.USED.getState());

			// 사용자의 마일리지 업데이트
			user.setMileage(totalMileage);
			return user.getMileage();

		} else {
			throw new MileageLackException();
		}
	}

	// 주문 상태 수정
	@Transactional
	public void updateOrderState(Long orderSeq, OrderStateUpdateRequest request) {
		Order order = orderRepository.findById(orderSeq).orElseThrow(OrderNotFoundException::new);

		// 상태 변경
		order.setOrderState(request.getOrderState());

		// db 저장
		orderRepository.save(order);
	}

	// ORDER-007 (사용자 주문 검색) 구현 안하기로 함

	// ORDER-008 (주문 취소)
	@Transactional
	public void deleteOrderByOrderSeq(Long orderSeq) {
		Order order = orderRepository.findOrderByOrdrSeq(orderSeq)
			.orElseThrow(OrderNotFoundException::new);
		User user = order.getUser();

		List<String> whenCancel = List.of(OrderState.CONFIRMED.getState(), OrderState.PREPARING.getState());
		List<String> whenRefund = List.of(OrderState.SHIPPED.getState(), OrderState.DELIVERED.getState(),
			OrderState.FINALIZED.getState());

		if (whenCancel.contains(order.getOrderState())) {
			cancelOrder(user, order);
		} else if (whenRefund.contains(order.getOrderState())) {
			order.setOrderState(OrderState.REFUND_CONFIRMED.getState());
		} else {
			throw new InvalidOrderStateException();
		}
	}

	// ORDER-009 (환불 확인)
	@Transactional
	public void confirmRefundByOrderSeq(Long orderSeq) {
		Order order = orderRepository.findOrderByOrdrSeq(orderSeq)
			.orElseThrow(OrderNotFoundException::new);
		User user = order.getUser();

		//반송이 완료되었다면
		if (order.getOrderState().equals(OrderState.RETURNED.getState())) {
			// 환불 후 금액
			Integer amountChanged = user.getMileage() + order.getTotalPrice();

			// 마일리지 복구
			mileageService.createMileageHistory(
				order.getUser(), order.getTotalPrice(), amountChanged, MileageHistoryState.REFUNDED.getState());

			user.setMileage(amountChanged);

			order.setOrderState(OrderState.REFUNDED.getState());
		} else {
			throw new InvalidOrderStateException();
		}
	}

	// ORDER-010 (결제 비밀번호 인증)
	public void authorizeOrderByPassword(Long userSeq, AuthorizeOrderRequest request) {
		User user = userService.readUserByUserSeq(userSeq);

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new PasswordNotValidException();
		}
	}

	// ORDER-011 (리뷰용 단일 주문상세 조회)는 OrderDetailService에 구현

	// ORDER-012 (취소/환불 목록 조회)
	public Page<CancelRefundReadResponse> readOrderCancelListByUserSeq(Long userSeq, Pageable pageable) {
		Page<Order> orderPage = orderRepository.findCancelRefundListByUserSeq(userSeq, pageable);

		List<CancelRefundReadResponse> responseList = orderPage.stream().map(order -> {
			OrderDetail orderDetail = orderDetailService.readFirstOrderDetailByOrderSeq(order.getOrdrSeq());
			Product product = orderDetail.getProduct();
			String requestState = OrderState.getNameByState(order.getOrderState());
			String requestType;

			if (Objects.equals(requestState, OrderState.CANCELED.getName())) {
				requestType = "취소";
			} else if (Objects.equals(requestState, OrderState.REFUND_CONFIRMED.getName())
				|| Objects.equals(requestState, OrderState.RETURNED.getName())
				|| Objects.equals(requestState, OrderState.REFUNDED.getName())) {
				requestType = "환불";
			} else {
				throw new InvalidOrderStateException();
			}

			return new CancelRefundReadResponse(product.getName(), product.getImageUrl(),
				orderDetail.getProductOption().getDetail(), order.getModifiedAt(), order.getOrderState(),
				requestType, requestState);
		}).collect(Collectors.toList());

		return new PageImpl<>(responseList, pageable, orderPage.getTotalElements());
	}

	// ORDER-013 (관리자 주문 목록 검색)
	public Page<OrderAdminReadResponse> readOrderListByAdminSearch(String receiverName, String name,
		Pageable pageable) {
		if (receiverName != null) {
			return orderRepository.findOrderListByAdminWithReceiverName(receiverName, pageable);
		} else if (name != null) {
			return orderRepository.findOrderListByAdminWithName(name, pageable);
		} else {
			return orderRepository.findAllOrderList(pageable);
		}
	}

	/** private **/

	public Integer calculateProductPrice(OrderProductRequest orderProduct) {
		Product product = productRepository.findProductByProductSeq(orderProduct.getProductSeq())
			.orElseThrow(ProductNotFoundException::new);
		int amount = orderProduct.getProductAmount();
		productStockService.checkStock(orderProduct.getProductSeq(), orderProduct.getProductOptionSeq(), amount);
		return amount * product.getPrice();
	}

	//직접 결제 price 계산
	private Integer calculateDirectProductPrice(OrderDirectProductRequest orderDirectProductRequest) {
		Product product = productService.readProductByProductSeq(orderDirectProductRequest.getProductSeq());
		int amount = orderDirectProductRequest.getProductAmount();
		productStockService.checkStock(orderDirectProductRequest.getProductSeq(),
			orderDirectProductRequest.getProductOptionSeq(), amount);
		return amount * product.getPrice();
	}

	// 장바구니 주문 상세 만들기
	private OrderDetail createOrderDetail(Order order, OrderProductRequest orderProduct) {
		Product product = productRepository.findProductByProductSeq(orderProduct.getProductSeq())
			.orElseThrow(ProductNotFoundException::new);
		ProductOption productOption = productOptionService.readProductOptionByPrdtOptionSeq(
			orderProduct.getProductOptionSeq());
		Integer amount = orderProduct.getProductAmount();

		OrderDetail orderDetail = new OrderDetail(order, product, productOption, amount,
			product.getPrice() * amount);
		// 상품 재고 변경
		productStockService.updateStockByProductSeqAndOptionSeq(orderProduct.getProductSeq(),
			orderProduct.getProductOptionSeq(), amount);

		return orderDetail;
	}

	// 직접 주문 상세 만들기
	private OrderDetail createOrderDirectDetail(Order order, OrderDirectProductRequest orderDirectProductRequest) {
		Product product = productService.readProductByProductSeq(orderDirectProductRequest.getProductSeq());
		ProductOption productOption = productOptionService.readProductOptionByPrdtOptionSeq(
			orderDirectProductRequest.getProductOptionSeq());
		Integer amount = orderDirectProductRequest.getProductAmount();

		OrderDetail orderDetail = new OrderDetail(order, product, productOption, amount,
			product.getPrice() * amount);
		// 상품 재고 변경
		productStockService.updateStockByProductSeqAndOptionSeq(orderDirectProductRequest.getProductSeq(),
			orderDirectProductRequest.getProductOptionSeq(), amount);

		return orderDetail;
	}

	// 주문 취소 - 주문 취소 처리
	private void cancelOrder(User user, Order order) {
		// 환불 후 금액
		Integer amountChanged = user.getMileage() + order.getTotalPrice();

		// 마일리지 복구
		mileageService.createMileageHistory(
			order.getUser(), order.getTotalPrice(), amountChanged, MileageHistoryState.CANCELLED.getState());

		user.setMileage(amountChanged);

		// 주문 상태 변경
		order.setOrderState(OrderState.CANCELED.getState());
	}
}


