package com.dokebi.dalkom.domain.user.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "employee")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {

	@Id
	private String empId;

	private String email;

	private String name;

	private LocalDate joinedAt;

}
