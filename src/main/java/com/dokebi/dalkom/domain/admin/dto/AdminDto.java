package com.dokebi.dalkom.domain.admin.dto;

import com.dokebi.dalkom.domain.admin.entity.Admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {
	public Long adminSeq;
	public String adminId;
	public String role;
	public String nickname;
	public String name;
	public String depart;

	public static AdminDto toDto(Admin admin) {
		return new AdminDto(
			admin.getAdminSeq(),
			admin.getAdminId(),
			admin.getRole(),
			admin.getNickname(),
			admin.getName(),
			admin.getDepart()
		);
	}
}
