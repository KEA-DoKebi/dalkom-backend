package com.dokebi.dalkom.domain.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.order.dto.OrderDetailDto;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.order.exception.OrderDetailNotFoundException;
import com.dokebi.dalkom.domain.order.repository.OrderDetailRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailService {

	private final OrderDetailRepository orderDetailRepository;

	@Transactional
	public void saveOrderDetail(OrderDetail orderDetail) {
		orderDetailRepository.save(orderDetail);
	}

	public OrderDetail readOrderDetailByOrderDetailSeq(Long orderDetailSeq) {
		return orderDetailRepository.findOrderDetailByOrdrDetailSeq(orderDetailSeq)
			.orElseThrow(OrderDetailNotFoundException::new);
	}

	public List<OrderDetail> readOrderDetailByOrderSeq(Long orderSeq) {
		List<OrderDetail> orderDetailList = orderDetailRepository.findOrderDetailListByOrderSeq(orderSeq);

		if (orderDetailList.isEmpty()) {
			throw new OrderDetailNotFoundException();
		}

		return orderDetailList;
	}

	public List<OrderDetailDto> readOrderDetailDtoByOrderSeq(Long orderSeq) {
		List<OrderDetailDto> orderDetailDtoList = orderDetailRepository.findOrderDetailDtoListByOrderSeq(orderSeq);

		if (orderDetailDtoList.isEmpty()) {
			throw new OrderDetailNotFoundException();
		}

		return orderDetailDtoList;
	}
}
