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
public class MileageApplyResponse {
	private Long userSeq;
	private Integer amount;
	private Integer balance;
	private String approvedState;
	private LocalDateTime createdAt;

}
