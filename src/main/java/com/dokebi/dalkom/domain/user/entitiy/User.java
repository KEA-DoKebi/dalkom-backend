package com.dokebi.dalkom.domain.user.entitiy;


import javax.persistence.*;

import com.dokebi.dalkom.common.EntityDate;

import com.dokebi.dalkom.domain.cart.entity.ordrCart;
import com.dokebi.dalkom.domain.inqury.entity.inquiry;
import com.dokebi.dalkom.domain.mileage.entity.milgApply;
import com.dokebi.dalkom.domain.mileage.entity.milgHistory;
import com.dokebi.dalkom.domain.order.entity.ordr;
import com.dokebi.dalkom.domain.review.entity.review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class user extends EntityDate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userSeq;

	@Column(name = "empId", nullable = false, unique = true)
	private String empId;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "joinedAt", nullable = false)
	private LocalDateTime joinedAt;

	@Column(name = "nickname", nullable = false, unique = true)
	private String nickname;

	@Column(name = "mileage", nullable = false)
	private String mileage;

	@Column(name = "state", nullable = false)
	private String state;

	@Column(name = "deletedAt")
	private LocalDateTime deleteDate;

	@OneToMany(mappedBy = "user")
	private List<milgHistory> milgHistory = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<milgApply> milgApply = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<ordr> ordr = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<ordrCart> ordrCart = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<review> review = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<inquiry> inquiry = new ArrayList<>();

}

