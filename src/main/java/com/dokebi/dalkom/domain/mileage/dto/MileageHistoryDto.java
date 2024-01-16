package com.dokebi.dalkom.domain.mileage.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MileageHistoryDto {

	private String type;
	private LocalDateTime createdAt;

	private Integer balance;
	private Integer amount;

}
