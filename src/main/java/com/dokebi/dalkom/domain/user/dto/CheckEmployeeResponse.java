package com.dokebi.dalkom.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class CheckEmployeeResponse {
	private String empId;
	private String name;
	private String email;
	private String joinedAt;
}
