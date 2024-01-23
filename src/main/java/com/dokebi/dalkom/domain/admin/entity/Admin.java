package com.dokebi.dalkom.domain.admin.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.inquiry.entity.Inquiry;
import com.dokebi.dalkom.domain.notice.entity.Notice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adminSeq;

	@Column(name = "adminId", nullable = false, unique = true)
	private String adminId;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "role", nullable = false, columnDefinition = "VARCHAR(1) DEFAULT '2'")
	private String role;

	@Column(name = "nickname", nullable = false, unique = true)
	private String nickname;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "depart", nullable = false)
	private String depart;

	@OneToMany(mappedBy = "admin")
	private List<Inquiry> inquiry = new ArrayList<>();

	@OneToMany(mappedBy = "admin")
	private List<Notice> notice = new ArrayList<>();

	public Admin(String adminId, String password, String nickname, String name, String depart) {
		this.adminId = adminId;
		this.password = password;
		this.nickname = nickname;
		this.name = name;
		this.depart = depart;
		this.role = "2";    //시스템에서 등록되는 관리자는 모두 일반관리자(2)이다.
	}
}
