package com.dokebi.dalkom.domain.mileage.dto;

import java.time.LocalDateTime;

import com.dokebi.dalkom.common.magicnumber.MileageApplyState;

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
@Generated
public class MileageApplyResponse {
	private Long milgApplySeq;
	private Long userSeq;
	private String name;
	private String email;
	private String nickname;
	private Integer amount;
	private Integer balance;
	private String approvedState;
	private String approvedStateName;
	private LocalDateTime approvedAt;
	private LocalDateTime createdAt;

	public MileageApplyResponse(Long milgApplySeq, Long userSeq, String name, String email, String nickname,
		Integer amount, Integer balance, String approvedState, LocalDateTime approvedAt, LocalDateTime createdAt) {
		this.milgApplySeq = milgApplySeq;
		this.userSeq = userSeq;
		this.name = name;
		this.email = email;
		this.nickname = nickname;
		this.amount = amount;
		this.balance = balance;
		this.approvedState = approvedState;
		this.approvedAt = approvedAt;
		this.createdAt = createdAt;
		this.approvedStateName = MileageApplyState.getNameByState(approvedState);
	}

}
