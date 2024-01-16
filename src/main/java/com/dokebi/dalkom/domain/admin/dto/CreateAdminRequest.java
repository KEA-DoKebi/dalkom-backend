package com.dokebi.dalkom.domain.admin.dto;

import com.dokebi.dalkom.domain.admin.entity.Admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdminRequest {
	private String adminId;
	private String password;
	private String nickname;
	private String name;
	private String depart;

	public static Admin toEntity(CreateAdminRequest req) {
		return new Admin(req.getAdminId(), req.getPassword(), req.getNickname(), req.getName(), req.getDepart());
	}
}
