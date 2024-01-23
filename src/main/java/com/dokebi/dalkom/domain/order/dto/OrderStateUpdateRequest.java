package com.dokebi.dalkom.domain.order.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import lombok.Getter;

@Getter
public class OrderStateUpdateRequest {

	@Pattern(regexp = "^(1[0-9]|[2-9][0-9]|100)$",message = "OrderStateUpdateRequest orderState pattern 에러")
	private String orderState;

}
