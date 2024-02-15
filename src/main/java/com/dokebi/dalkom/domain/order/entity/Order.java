package com.dokebi.dalkom.domain.order.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ordr")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends EntityDate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ordrSeq;

	@ManyToOne
	@JoinColumn(name = "userSeq")
	private User user;

	@Column(name = "rcvrName", nullable = false)
	private String receiverName;

	@Column(name = "rcvrAddress", nullable = false)
	private String receiverAddress;

	@Column(name = "rcvrMobileNum", nullable = false)
	private String receiverMobileNum;

	@Column(name = "rcvrMemo", nullable = false)
	private String receiverMemo;

	@Column(name = "totalPrice", nullable = false)
	private Integer totalPrice;

	@Column(name = "ordrState", nullable = false)
	private String orderState;

	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetailList = new ArrayList<>();

	public Order(User user, String receiverName, String receiverAddress,
		String receiverMobileNum, String receiverMemo, Integer totalPrice) {
		this.user = user;
		this.receiverName = receiverName;
		this.receiverAddress = receiverAddress;
		this.receiverMobileNum = receiverMobileNum;
		this.receiverMemo = receiverMemo;
		this.totalPrice = totalPrice;
		this.orderState = "배송준비중";
	}

	public Order(Long ordrSeq, String receiverName, String receiverAddress, String receiverMobileNum,
		String receiverMemo,
		Integer totalPrice) {
		this.ordrSeq = ordrSeq;
		this.receiverName = receiverName;
		this.receiverAddress = receiverAddress;
		this.receiverMobileNum = receiverMobileNum;
		this.receiverMemo = receiverMemo;
		this.totalPrice = totalPrice;
	}
}
