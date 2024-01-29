package com.dokebi.dalkom.domain.order.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
	private ReceiverInfoRequest receiverInfoRequest;
	private List<OrderProductRequest> orderProductRequestList;
}

