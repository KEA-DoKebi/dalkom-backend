package com.dokebi.dalkom.domain.admin.dto;

import com.dokebi.dalkom.common.magicnumber.AdminRole;
import com.dokebi.dalkom.domain.admin.entity.Admin;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ReadAdminResponse {
	public Long adminSeq;
	public String adminId;
	public String role;
	public String nickname;
	public String name;
	public String depart;
	public String roleName;

	public ReadAdminResponse(Long adminSeq, String adminId, String role, String nickname, String name, String depart) {
		this.adminSeq = adminSeq;
		this.adminId = adminId;
		this.role = role;
		this.nickname = nickname;
		this.name = name;
		this.depart = depart;
		this.roleName = AdminRole.getNameByState(role);
	}

	public static ReadAdminResponse toDto(Admin admin) {
		return new ReadAdminResponse(
			admin.getAdminSeq(),
			admin.getAdminId(),
			admin.getRole(),
			admin.getNickname(),
			admin.getName(),
			admin.getDepart()
		);
	}
}
