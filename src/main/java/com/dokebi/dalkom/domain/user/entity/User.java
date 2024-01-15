package com.dokebi.dalkom.domain.user.entity;

import java.time.LocalDateTime;
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
import com.dokebi.dalkom.domain.cart.entity.OrderCart;
import com.dokebi.dalkom.domain.inqury.entity.Inquiry;
import com.dokebi.dalkom.domain.mileage.entity.MileageApply;
import com.dokebi.dalkom.domain.mileage.entity.MileageHistory;
import com.dokebi.dalkom.domain.order.entity.Order;
import com.dokebi.dalkom.domain.review.entity.Review;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private String joinedAt;

	@Column(name = "nickname", nullable = false, unique = true)
	private String nickname;

	@Column(name = "mileage", nullable = false)
	private String mileage;

	@Column(name = "state", nullable = false)
	private String deleteState;

	@Column(name = "deletedAt")
	private LocalDateTime deletedAt;

	@OneToMany(mappedBy = "user")
	private List<MileageHistory> mileageHistoryList = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<MileageApply> mileageApplyList = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Order> orderList = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<OrderCart> OrderCart = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Review> review = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Inquiry> inquiry = new ArrayList<>();


	public User (String empId, String password, String name, String email, String address, String joinedAt, String nickname, String mileage){
		this.empId = empId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.address = address;
		this.joinedAt = joinedAt;
		this.nickname = nickname;
		this.mileage = mileage;
		this.deleteState = "N";

	}
}

