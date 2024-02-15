package com.dokebi.dalkom.domain.order.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.dokebi.dalkom.domain.order.dto.OrderDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderDetailSimpleResponse;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderDetailRepositoryTest {
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	// ORDER-001 (사용자별 전체 주문 조회)는 OrderService에서 구현

	// ORDER-002 (주문 확인하기)는 OrderService에서 구현

	// ORDER-003 (특정 주문 조회)에서 사용되는 SQL
	@Test
	@DisplayName("findOrderDetailDtoListByOrderSeq 테스트")
	void findOrderDetailDtoListByOrderSeqTest() {
		// Given
		Long orderSeq = 1L;

		// When
		List<OrderDetailDto> orderDetailDtoList
			= orderDetailRepository.findOrderDetailDtoListByOrderSeq(orderSeq);

		// Then
		assertNotNull(orderDetailDtoList);
		assertFalse(orderDetailDtoList.isEmpty());
	}

	// ORDER-004 (관리자 전체 주문 조회)는 OrderService에서 구현

	// ORDER-005 (결제 하기)에서 사용되는 함수는 orderDetailService.save()로 사용

	// ORDER-006 (특정 주문 상태 수정) 에서는 orderRepository.findById()를 사용

	// ORDER-007 (사용자 주문 검색) 구현 안하기로 함

	// ORDER-008 (주문 취소), ORDER-009 (환불 확인)는 OrderService에서 구현

	// ORDER-010 (결제 비밀번호 인증)는 orderDetailRepository 사용 안함

	// ORDER-011 (리뷰용 단일 주문상세 조회)에서 사용되는 SQL
	@Test
	@DisplayName("findOrderDetailSimpleResponseByOrdrDetailSeq 테스트")
	void findOrderDetailSimpleResponseByOrdrDetailSeqTest() {
		// Given
		Long orderDetailSeq = 1L;

		// When
		Optional<OrderDetailSimpleResponse> orderDetailSimpleResponse
			= orderDetailRepository.findOrderDetailSimpleResponseByOrdrDetailSeq(orderDetailSeq);

		// Then
		assertTrue(orderDetailSimpleResponse.isPresent());
		assertNotNull(orderDetailSimpleResponse.get().getProductName());
		assertNotNull(orderDetailSimpleResponse.get().getImageUrl());
		assertNotNull(orderDetailSimpleResponse.get().getOptionSeq());
		assertNotNull(orderDetailSimpleResponse.get().getDetail());
	}

	// ORDER-012 (취소/환불 목록 조회)에서 사용되는 SQL
	@Test
	@DisplayName("findFirstByOrder_OrdrSeq 테스트")
	void findFirstByOrder_OrdrSeqTest() {
		// Given
		Long orderDetailSeq = 1L;

		// When
		Optional<OrderDetail> orderDetail
			= orderDetailRepository.findFirstByOrder_OrdrSeq(orderDetailSeq);

		// Then
		assertTrue(orderDetail.isPresent());
	}

	// ORDER-013 (관리자 주문 목록 검색)는 OrderService에서 구현

	// REVIEW-003 (주문 상품에 대한 리뷰 작성)에서 사용되는 SQL
	@Test
	@DisplayName("findOrderDetailByOrdrDetailSeq 테스트")
	void findOrderDetailByOrdrDetailSeqTest() {
		// Given
		Long orderDetailSeq = 1L;

		// When
		Optional<OrderDetail> orderDetail
			= orderDetailRepository.findFirstByOrder_OrdrSeq(orderDetailSeq);

		// Then
		assertTrue(orderDetail.isPresent());
	}
}
