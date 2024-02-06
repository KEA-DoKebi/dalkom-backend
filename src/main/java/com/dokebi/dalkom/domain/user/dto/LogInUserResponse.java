package com.dokebi.dalkom.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class LogInUserResponse {
	private String accessToken;
	private Integer mileage;
}
