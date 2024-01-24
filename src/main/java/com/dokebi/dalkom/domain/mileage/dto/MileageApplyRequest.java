package com.dokebi.dalkom.domain.mileage.dto;
import javax.validation.constraints.Positive;
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
public class MileageApplyRequest {

	@Positive(message = "MileageAskRequest amount Positive 에러")
	private Integer amount;
}
