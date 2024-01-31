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
	private Long milgApplySeq;
	private Long userSeq;
	private String name;
	private String email;
	private String nickname;
	private Integer amount;
	private Integer balance;
	private String approvedState;
	private LocalDateTime approvedAt;
	private LocalDateTime createdAt;

}
