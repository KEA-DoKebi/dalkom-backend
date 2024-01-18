package com.dokebi.dalkom.domain.order.service;

import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.order.repository.OrderDetailRepository;
import com.dokebi.dalkom.domain.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
	private final OrderDetailRepository orderDetailRepository;

	public OrderDetail findByOrdrDetailSeq(Long ordrDetailSeq){
		return orderDetailRepository.findByOrdrDetailSeq(ordrDetailSeq);
	}

	public void saveOrderDetail(OrderDetail orderDetail){
		orderDetailRepository.save(orderDetail);
	}

}
