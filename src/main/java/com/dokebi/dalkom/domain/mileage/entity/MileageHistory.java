package com.dokebi.dalkom.domain.mileage.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "milgHistory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MileageHistory extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long milgHistorySeq;

	@ManyToOne
	@JoinColumn(name = "userSeq")
	private User user;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Column(name = "balance", nullable = false)
	private Integer balance;

	@Column(name = "type", nullable = false)
	private String type;

	public MileageHistory(Integer amount, Integer balance,  String type,User user) {
		this.amount = amount;
		this.balance = balance;
		this.type = type;
		this.user = user;

	}
}
