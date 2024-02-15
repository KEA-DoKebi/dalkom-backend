package com.dokebi.dalkom.domain.order.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.dokebi.dalkom.domain.order.dto.OrderAdminReadResponse;
import com.dokebi.dalkom.domain.order.dto.OrderUserReadResponse;
import com.dokebi.dalkom.domain.order.dto.ReceiverDetailDto;
import com.dokebi.dalkom.domain.order.entity.Order;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {
	@Autowired
	private OrderRepository orderRepository;

	// ORDER-001 (사용자별 전체 주문 조회)에서 사용하는 SQL
	@Test
	@DisplayName("findOrderListByUserSeq 테스트")
	void findOrderListByUserSeqTest() {
		// Given
		Long userSeq = 1L;
		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<OrderUserReadResponse> orderUserReadResponsePage
			= orderRepository.findOrderListByUserSeq(userSeq, pageable);

		// Then
		assertNotNull(orderUserReadResponsePage);
		assertFalse(orderUserReadResponsePage.isEmpty());
		assertEquals(orderUserReadResponsePage.getSize(), pageable.getPageSize());
	}

	// ORDER-002 (주문 확인하기)는 orderRepository 사용 안함

	// ORDER-003 (특정 주문 조회)에서 사용하는 SQL
	@Test
	@DisplayName("findReceiverDetailDtoByOrdrSeq 테스트")
	void findReceiverDetailDtoByOrdrSeqTest() {
		// Given
		Long orderSeq = 1L;

		// When
		Optional<ReceiverDetailDto> receiverDetailDto
			= orderRepository.findReceiverDetailDtoByOrdrSeq(orderSeq);

		// Then
		assertTrue(receiverDetailDto.isPresent());
		assertNotNull(receiverDetailDto.get().getReceiverName());
		assertNotNull(receiverDetailDto.get().getReceiverMobileNum());
		assertNotNull(receiverDetailDto.get().getReceiverAddress());
		assertNotNull(receiverDetailDto.get().getReceiverMemo());
	}

	// ORDER-004 (관리자 전체 주문 조회)에서 사용하는 SQL
	@Test
	@DisplayName("findAllOrderList 테스트")
	void findAllOrderListTest() {
		// Given
		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<OrderAdminReadResponse> orderAdminReadResponsePage
			= orderRepository.findAllOrderList(pageable);

		// Then
		assertNotNull(orderAdminReadResponsePage);
		assertFalse(orderAdminReadResponsePage.isEmpty());
		assertEquals(orderAdminReadResponsePage.getSize(), pageable.getPageSize());
	}

	// ORDER-005 (결제 하기) 에서는 orderRepository.save()를 사용

	// ORDER-006 (특정 주문 상태 수정) 에서는 orderRepository.findById()를 사용

	// ORDER-007 (사용자 주문 검색) 구현 안하기로 함

	// ORDER-008 (주문 취소), ORDER-009 (환불 확인)에서 사용하는 SQL
	@Test
	@DisplayName("findOrderByOrdrSeq 테스트")
	void findOrderByOrdrSeqTest() {
		// Given
		Long orderSeq = 1L;

		// When
		Optional<Order> order = orderRepository.findOrderByOrdrSeq(orderSeq);

		// Then
		assertTrue(order.isPresent());
		assertNotNull(order.get().getUser());
		assertNotNull(order.get().getReceiverName());
		assertNotNull(order.get().getReceiverMobileNum());
		assertNotNull(order.get().getReceiverAddress());
		assertNotNull(order.get().getReceiverMemo());
		assertNotNull(order.get().getTotalPrice());
		assertNotNull(order.get().getOrderState());
		assertNotNull(order.get().getOrderDetailList());
	}

	// ORDER-010 (결제 비밀번호 인증)는 orderRepository 사용 안함

	// ORDER-011 (리뷰용 단일 주문상세 조회)는 OrderDetailRepository에 구현

	// ORDER-012 (취소/환불 목록 조회)에서 사용하는 SQL
	@Test
	@DisplayName("findCancelRefundListByUserSeq 테스트")
	void findCancelRefundListByUserSeqTest() {
		// Given
		Long userSeq = 1L;
		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<Order> orderPage = orderRepository.findCancelRefundListByUserSeq(userSeq, pageable);

		// Then
		assertNotNull(orderPage);
		assertFalse(orderPage.isEmpty());
		assertEquals(orderPage.getSize(), pageable.getPageSize());
	}

	// ORDER-013 (관리자 주문 목록 검색) - 수령자 이름 에서 사용하는 SQL
	@Test
	@DisplayName("findOrderListByAdminWithReceiverName 테스트")
	void findOrderListByAdminWithReceiverNameTest() {
		// Given - 실제 DB에 존재하는 이름 사용
		String receiverName = "name";
		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<OrderAdminReadResponse> orderAdminReadResponsePage
			= orderRepository.findOrderListByAdminWithReceiverName(receiverName, pageable);

		// Then
		assertNotNull(orderAdminReadResponsePage);
		assertFalse(orderAdminReadResponsePage.isEmpty());
		assertEquals(orderAdminReadResponsePage.getSize(), pageable.getPageSize());
	}

	// ORDER-013 (관리자 주문 목록 검색) - 주문자 이름 에서 사용하는 SQL
	@Test
	@DisplayName("findOrderListByAdminWithName 테스트")
	void findOrderListByAdminWithNameTest() {
		// Given
		String name = "tempUser";
		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<OrderAdminReadResponse> orderAdminReadResponsePage
			= orderRepository.findOrderListByAdminWithName(name, pageable);

		// Then
		assertNotNull(orderAdminReadResponsePage);
		assertFalse(orderAdminReadResponsePage.isEmpty());
		assertEquals(orderAdminReadResponsePage.getSize(), pageable.getPageSize());
	}
}
