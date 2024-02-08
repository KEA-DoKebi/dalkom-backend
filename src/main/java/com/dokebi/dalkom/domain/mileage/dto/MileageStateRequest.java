package com.dokebi.dalkom.domain.mileage.dto;

import javax.validation.constraints.NotNull;

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
public class MileageStateRequest {
	@NotNull(message = "MileageStateRequest approvedState NotNull 에러")
	private String approvedState;
}
