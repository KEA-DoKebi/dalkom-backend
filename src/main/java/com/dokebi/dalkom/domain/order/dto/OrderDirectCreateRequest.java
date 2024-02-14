package com.dokebi.dalkom.domain.order.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderDirectCreateRequest {
	private ReceiverInfoRequest receiverInfoRequest;
	private List<OrderDirectProductRequest> orderDirectProductRequestList;
}
