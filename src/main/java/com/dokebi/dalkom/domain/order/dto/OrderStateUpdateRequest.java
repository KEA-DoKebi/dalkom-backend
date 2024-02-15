package com.dokebi.dalkom.domain.order.dto;

import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderStateUpdateRequest {

	@Pattern(regexp = "^(1[0-9]|[2-9][0-9]|100)$", message = "OrderStateUpdateRequest orderState pattern 에러")
	private String orderState;

}
