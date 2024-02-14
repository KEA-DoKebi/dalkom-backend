package com.dokebi.dalkom.domain.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.order.dto.OrderDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderDetailSimpleResponse;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.order.exception.OrderDetailNotFoundException;
import com.dokebi.dalkom.domain.order.repository.OrderDetailRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderDetailService {
	private final OrderDetailRepository orderDetailRepository;

	// ORDER-001 (사용자별 전체 주문 조회)는 OrderService에서 구현

	// ORDER-002 (주문 확인하기)는 OrderService에서 구현

	// ORDER-003 (특정 주문 조회)에서 사용되는 함수
	public List<OrderDetailDto> readOrderDetailDtoByOrderSeq(Long orderSeq) {
		List<OrderDetailDto> orderDetailDtoList = orderDetailRepository.findOrderDetailDtoListByOrderSeq(orderSeq);

		if (orderDetailDtoList.isEmpty()) {
			throw new OrderDetailNotFoundException();
		}

		return orderDetailDtoList;
	}

	// ORDER-005 (결제 하기)에서 사용되는 함수
	@Transactional
	public void saveOrderDetail(OrderDetail orderDetail) {
		orderDetailRepository.save(orderDetail);
	}

	// ORDER-006 (특정 주문 상태 수정) 에서는 orderRepository.findById()를 사용

	// ORDER-007 (사용자 주문 검색) 구현 안하기로 함

	// ORDER-008 (주문 취소), ORDER-009 (환불 확인)는 OrderService에서 구현

	// ORDER-010 (결제 비밀번호 인증)는 orderDetailRepository 사용 안함

	// ORDER-011 (리뷰용 단일 주문상세 조회)에서 사용되는 함수
	public OrderDetailSimpleResponse readOrderDetailSimpleResponseByOrderDetailSeq(Long orderDetailSeq) {
		return orderDetailRepository.findOrderDetailSimpleResponseByOrdrDetailSeq(orderDetailSeq)
			.orElseThrow(OrderDetailNotFoundException::new);
	}

	// ORDER-012 (취소/환불 목록 조회)에서 사용되는 함수
	public OrderDetail readFirstOrderDetailByOrderSeq(Long orderSeq) {
		return orderDetailRepository.findFirstByOrder_OrdrSeq(orderSeq)
			.orElseThrow(OrderDetailNotFoundException::new);
	}

	// ORDER-013 (관리자 주문 목록 검색)는 OrderService에서 구현

	// REVIEW-003 (주문 상품에 대한 리뷰 작성)에서 사용되는 함수
	public OrderDetail readOrderDetailByOrderDetailSeq(Long orderDetailSeq) {
		return orderDetailRepository.findOrderDetailByOrdrDetailSeq(orderDetailSeq)
			.orElseThrow(OrderDetailNotFoundException::new);
	}
}
