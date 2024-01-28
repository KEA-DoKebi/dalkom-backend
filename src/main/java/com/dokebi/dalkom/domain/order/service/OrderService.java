package com.dokebi.dalkom.domain.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.magicnumber.MileageHistoryState;
import com.dokebi.dalkom.common.magicnumber.OrderState;
import com.dokebi.dalkom.domain.mileage.exception.MileageLackException;
import com.dokebi.dalkom.domain.mileage.service.MileageService;
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.option.service.ProductOptionService;
import com.dokebi.dalkom.domain.order.dto.AuthorizeOrderRequest;
import com.dokebi.dalkom.domain.order.dto.OrderAdminReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderCreateRequest;
import com.dokebi.dalkom.domain.order.dto.OrderDetailReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderPageDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderStateUpdateRequest;
import com.dokebi.dalkom.domain.order.dto.OrderUserReadResponse;
import com.dokebi.dalkom.domain.order.entity.Order;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.order.exception.InvalidOrderStateException;
import com.dokebi.dalkom.domain.order.exception.OrderNotFoundException;
import com.dokebi.dalkom.domain.order.exception.PasswordNotValidException;
import com.dokebi.dalkom.domain.order.repository.OrderRepository;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailResponse;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.service.ProductService;
import com.dokebi.dalkom.domain.review.entity.Review;
import com.dokebi.dalkom.domain.review.service.ReviewService;
import com.dokebi.dalkom.domain.stock.service.ProductStockService;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderService {
	private final OrderRepository orderRepository;
	private final OrderDetailService orderDetailService;
	private final ProductOptionService productOptionService;
	private final ProductService productService;
	private final ProductStockService productStockService;
	private final MileageService mileageService;
	private final UserService userService;
	private final ReviewService reviewService;
	private final PasswordEncoder passwordEncoder;

	// 결제 하기
	@Transactional
	public void createOrder(OrderCreateRequest request) {

		Integer orderTotalPrice = 0;

		// orderTotalPrice를 먼저 계산해준다.
		for (int i = 0; i < request.getProductSeqList().size(); i++) {
			orderTotalPrice += calculateProductPrice(request, i);
		}

		// 어떤 사용자인지 조회
		User user = userService.readUserByUserSeq(request.getUserSeq());

		// 해당 사용자가 보유한 마일리지와 주문의 총 가격과 비교
		if (orderTotalPrice <= user.getMileage()) {

			// 주문을 위한 entity 생성 후 저장
			Order order = new Order(user, request.getReceiverName(), request.getReceiverAddress(),
				request.getReceiverMobileNum(), request.getReceiverMemo(), orderTotalPrice);
			order.setOrderState(OrderState.Before_Authorize);
			orderRepository.save(order);

			// 주문에 속한 세부 주문( 주문에 속한 각 상품별 데이터 ) entity 생성 후 저장
			for (int i = 0; i < request.getProductSeqList().size(); i++) {

				// 상세 주문 내역 생성
				OrderDetail orderDetail = createOrderDetail(order, request, i);

				// 각 세부 주문 DB에 저장
				orderDetailService.saveOrderDetail(orderDetail);
			}
			// 마일리지 감소 시점을 비밀번호 인증시로 이동
			// // 사용한 마일리지 감소 후 변경된 사용자 정보 업데이트
			// Integer totalMileage = (user.getMileage() - orderTotalPrice);
			//
			// mileageService.createMileageHistory(user, orderTotalPrice, totalMileage, MileageHistoryState.USED);
			//
			// // 사용자의 마일리지 업데이트
			// user.setMileage(totalMileage);

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
			OrderPageDetailDto orderPageDetailDTO = new OrderPageDetailDto(productSeq, optionSeq, productAmount,
				productInfo.getName(), productInfo.getPrice(), productInfo.getPrice() * order.getProductAmount());

			result.add(orderPageDetailDTO);
		});

		return result;
	}

	// 사용자별 주문 전체 조회
	public Page<OrderUserReadResponse> readOrderByUserSeq(Long userSeq, Pageable pageable) {

		Page<OrderUserReadResponse> orderPage = orderRepository.findOrderListByUserSeq(userSeq, pageable);

		List<OrderUserReadResponse> modifiedList = orderPage.getContent().stream()
			.peek(orderResponse -> orderResponse.makeOrderTitle(orderResponse.getOrderTitle(),
				orderResponse.getProductCnt()))
			.collect(Collectors.toList());

		return new PageImpl<>(modifiedList, pageable, orderPage.getTotalElements());

	}

	// 주문별 상세 조회
	public OrderDetailReadResponse readOrderByOrderSeq(Long orderSeq) {
		return orderRepository.findOrderDetailByOrdrSeq(orderSeq).orElseThrow(OrderNotFoundException::new);
	}

	// 주문 전체 조회
	public Page<OrderAdminReadResponse> readOrderByAll(Pageable pageable) {
		return orderRepository.findAllOrderList(pageable);
	}

	// 주문 상태 수정
	public void updateOrderState(Long orderSeq, OrderStateUpdateRequest request) {
		Order order = orderRepository.findById(orderSeq).orElseThrow(OrderNotFoundException::new);

		// 상태 변경
		order.setOrderState(request.getOrderState());

		// db 저장
		orderRepository.save(order);
	}

	private Integer calculateProductPrice(OrderCreateRequest request, int i) {
		Product product = productService.readProductByProductSeq(request.getProductSeqList().get(i));
		Long prdtOptionSeq = request.getPrdtOptionSeqList().get(i);
		Integer amount = request.getAmountList().get(i);
		Integer price = product.getPrice();

		productStockService.checkStock(product.getProductSeq(), prdtOptionSeq, amount);

		return amount * price;
	}

	// 주문 상세 만들기
	private OrderDetail createOrderDetail(Order order, OrderCreateRequest request, int i) {
		Long productSeq = request.getProductSeqList().get(i);
		Long prdtOptionSeq = request.getPrdtOptionSeqList().get(i);
		Integer amount = request.getAmountList().get(i);

		Product product = productService.readProductByProductSeq(productSeq);
		ProductOption productOption = productOptionService.readProductOptionByPrdtOptionSeq(prdtOptionSeq);
		Integer price = product.getPrice();

		OrderDetail orderDetail = new OrderDetail(order, product, productOption, amount, price);
		//상품 재고 변경
		productStockService.updateStockByProductSeqAndOptionSeq(productSeq, prdtOptionSeq, amount);

		return orderDetail;
	}

	// 주문 검색 조회 서비스
	public Page<OrderUserReadResponse> readOrderListBySearch(String receiverName, Pageable pageable) {
		return orderRepository.findAllOrderListByReceiverName(receiverName, pageable);
	}

	// 주문 취소
	@Transactional
	public void deleteOrderByOrderSeq(Long orderSeq) {
		Order order = orderRepository.findOrderByOrdrSeq(orderSeq)
			.orElseThrow(OrderNotFoundException::new);
		User user = order.getUser();
		List<OrderDetail> orderDetailList = orderDetailService.readOrderDetailByOrderSeq(orderSeq);
		List<Review> reviewList = reviewService.readReviewByOrderDetailList(orderDetailList);

		List<String> whenCancel = List.of(OrderState.CONFIRMED, OrderState.PREPARING);
		List<String> whenRefund = List.of(OrderState.SHIPPED, OrderState.DELIVERED, OrderState.FINALIZED);

		if (whenCancel.contains(order.getOrderState())) {
			cancelOrder(reviewList, user, order);
		} else if (whenRefund.contains(order.getOrderState())) {
			refundOrder(reviewList, order);
		} else {
			throw new InvalidOrderStateException();
		}

	}

	// 환불 확인 (상품 수령 후)
	@Transactional
	public void confirmRefundByOrderSeq(Long orderSeq) {
		Order order = orderRepository.findOrderByOrdrSeq(orderSeq)
			.orElseThrow(OrderNotFoundException::new);
		User user = order.getUser();

		//반송이 완료되었다면
		if (order.getOrderState().equals(OrderState.RETURNED)) {
			// 환불 후 금액
			Integer amountChanged = user.getMileage() + order.getTotalPrice();

			// 마일리지 복구
			mileageService.createMileageHistory(
				order.getUser(), order.getTotalPrice(), amountChanged, MileageHistoryState.REFUNDED);

			user.setMileage(amountChanged);

			order.setOrderState(OrderState.REFUNDED);
		} else {
			throw new InvalidOrderStateException();
		}
	}

	// 결제시 비밀번호 인증
	public void authorizeOrderByPassword(Long userSeq, AuthorizeOrderRequest request) {
		User user = userService.readUserByUserSeq(userSeq);
		Order order = orderRepository.findById(request.getOrderSeq()).orElseThrow(OrderNotFoundException::new);

		if (!order.getOrderState().equals(OrderState.Before_Authorize)) {
			throw new InvalidOrderStateException();
		}

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new PasswordNotValidException();
		}

		order.setOrderState(OrderState.CONFIRMED);

		// 사용한 마일리지 감소 후 변경된 사용자 정보 업데이트
		Integer totalMileage = (user.getMileage() - order.getTotalPrice());
		mileageService.createMileageHistory(user, order.getTotalPrice(), totalMileage, MileageHistoryState.USED);

		// 사용자의 마일리지 업데이트
		user.setMileage(totalMileage);
	}

	// 주문 취소 - 주문 취소 처리
	private void cancelOrder(List<Review> reviewList, User user, Order order) {

		// 만약, 리뷰가 존재한다면 리뷰를 전부 지운다. (조건문 불필요)
		for (Review review : reviewList) {
			reviewService.deleteReview(review.getReviewSeq());
		}

		// 환불 후 금액
		Integer amountChanged = user.getMileage() + order.getTotalPrice();

		// 마일리지 복구
		mileageService.createMileageHistory(
			order.getUser(), order.getTotalPrice(), amountChanged, MileageHistoryState.CANCELLED);

		user.setMileage(amountChanged);

		// 주문 상태 변경
		order.setOrderState(OrderState.CANCELED);
	}

	// 주문 취소 - 환불 처리
	private void refundOrder(List<Review> reviewList, Order order) {

		// 만약, 리뷰가 존재한다면 리뷰를 전부 지운다. (조건문 불필요)
		// TODO 환불의 경우 일단 상품을 받았으니 리뷰를 남겨둘지 고려
		for (Review review : reviewList) {
			reviewService.deleteReview(review.getReviewSeq());
		}

		/*
		// 환불 후 금액
		Integer amountChanged = user.getMileage() + order.getTotalPrice();

		// 마일리지 복구
		mileageService.createMileageHistory(
			order.getUser(), order.getTotalPrice(), amountChanged, MileageHistoryState.REFUNDED);

		user.setMileage(amountChanged);
		*/

		// 주문 상태 변경
		order.setOrderState(OrderState.REFUND_CONFIRMED);
	}
}


