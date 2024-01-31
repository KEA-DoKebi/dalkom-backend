package com.dokebi.dalkom.domain.mileage.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MileageStateRequest {
	@NotNull(message = "MileageStateRequest approvedState NotNull 에러")
	private String approvedState;
}
