package com.dokebi.dalkom.domain.user.dto;

import com.dokebi.dalkom.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserListResponse {
	public Long UserSeq;
	public String email;
	public String nickname;
	public Integer mileage;
	public String address;

	public static UserListResponse toDto(User user) {
		return new UserListResponse(
			user.getUserSeq(),
			user.getEmail(),
			user.getNickname(),
			user.getMileage(),
			user.getAddress()
		);
	}

}
