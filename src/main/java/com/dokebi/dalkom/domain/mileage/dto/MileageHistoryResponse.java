package com.dokebi.dalkom.domain.mileage.dto;

import java.time.LocalDateTime;

import com.dokebi.dalkom.common.magicnumber.MileageHistoryState;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class MileageHistoryResponse {

	private String type;
	private String typeName;
	private LocalDateTime createdAt;
	private Integer balance;
	private Integer amount;

	public MileageHistoryResponse(String type, LocalDateTime createdAt, Integer balance, Integer amount) {
		this.type = type;
		this.typeName = MileageHistoryState.getNameByState(type);
		this.createdAt = createdAt;
		this.balance = balance;
		this.amount = amount;
	}
}
