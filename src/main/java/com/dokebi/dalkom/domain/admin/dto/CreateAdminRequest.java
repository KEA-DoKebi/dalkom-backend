package com.dokebi.dalkom.domain.admin.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.dokebi.dalkom.domain.admin.entity.Admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdminRequest {

	@NotNull(message = "CreateAdminRequest adminId notnull 에러")
	@NotBlank(message = "CreateAdminRequest adminId notblank 에러")
	private String adminId;

	@NotNull(message = "CreateAdminRequest password notnull 에러")
	@NotBlank(message = "CreateAdminRequest password notblank 에러")
	private String password;

	@NotNull(message = "CreateAdminRequest nickname notnull 에러")
	@NotBlank(message = "CreateAdminRequest nickname notblank 에러")
	private String nickname;

	@NotNull(message = "CreateAdminRequest name notnull 에러")
	@NotBlank(message = "CreateAdminRequest name notblank 에러")
	private String name;

	@NotNull(message = "CreateAdminRequest depart notnull 에러")
	@NotBlank(message = "CreateAdminRequest depart notblank 에러")
	private String depart;

	public static Admin toEntity(CreateAdminRequest req) {
		return new Admin(req.getAdminId(), req.getPassword(), req.getNickname(), req.getName(), req.getDepart());
	}
}
