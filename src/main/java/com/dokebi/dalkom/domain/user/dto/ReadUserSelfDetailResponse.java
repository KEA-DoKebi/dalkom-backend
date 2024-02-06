package com.dokebi.dalkom.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Generated
public class ReadUserSelfDetailResponse {
	private String email;
	private String name;
	private String nickname;
	private String address;
}
