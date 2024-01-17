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
public class MileageAskResponse {
	private Long userSeq;
	private LocalDateTime createdAt;
	private Integer balance;
	private Integer amount;
	private String approvedState;
}
