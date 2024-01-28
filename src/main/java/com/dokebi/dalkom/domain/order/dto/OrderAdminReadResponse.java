package com.dokebi.dalkom.domain.order.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class OrderAdminReadResponse {
	private Long ordrSeq;
	private LocalDateTime ordrDate;
	private Long ordrCnt;
	private String name;
	private String receiveName;
	private Integer totalPrice;
	private String ordrState;
}
