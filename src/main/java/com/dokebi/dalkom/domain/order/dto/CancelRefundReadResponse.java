package com.dokebi.dalkom.domain.order.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CancelRefundReadResponse {
	private String productName;
	private String imageUrl;
	private String optionDetail;
	private LocalDateTime modifiedAt;
	private String stateCode;
	private String requestType;
	private String requestState;
}
