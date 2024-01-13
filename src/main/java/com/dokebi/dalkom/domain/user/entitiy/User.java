package com.dokebi.dalkom.domain.user.entitiy;


import javax.persistence.*;

import com.dokebi.dalkom.common.EntityDate;

import com.dokebi.dalkom.domain.cart.entity.OrderCart;
import com.dokebi.dalkom.domain.inqury.entity.Inquiry;
import com.dokebi.dalkom.domain.mileage.entity.MilgApply;
import com.dokebi.dalkom.domain.mileage.entity.MilgHistory;
import com.dokebi.dalkom.domain.order.entity.Ordr;
import com.dokebi.dalkom.domain.review.entity.Review;
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
public class User extends EntityDate {
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
	private List<MilgHistory> milgHistory = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<MilgApply> milgApply = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Ordr> ordr = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<OrderCart> OrderCart = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Review> review = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Inquiry> inquiry = new ArrayList<>();

}

