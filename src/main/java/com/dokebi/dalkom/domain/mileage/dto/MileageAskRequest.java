package com.dokebi.dalkom.domain.mileage.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MileageAskRequest {

	@NotNull(message = "MileageAskRequest amount NotBlank 에러")
	@Positive(message = "MileageAskRequest amount Positive 에러")
	private Integer amount;
}
