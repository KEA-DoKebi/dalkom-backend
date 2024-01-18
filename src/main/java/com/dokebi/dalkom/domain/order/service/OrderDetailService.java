package com.dokebi.dalkom.domain.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.order.exception.OrderDetailNotFoundException;
import com.dokebi.dalkom.domain.order.repository.OrderDetailRepository;
import com.dokebi.dalkom.domain.order.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;

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

	public OrderDetail readOrderDetailByOrderDetailSeq(Long orderdetailSeq) {
		return orderDetailRepository.findByOrdrDetailSeq(orderdetailSeq).orElseThrow(OrderDetailNotFoundException::new);
	}
}
