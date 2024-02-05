package com.dokebi.dalkom.domain.order.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailReadResponse {

	private List<OrderDetailDto> orderDetailList;
	private ReceiverDetailDto receiverDetail;
	private Integer totalPrice;
}
