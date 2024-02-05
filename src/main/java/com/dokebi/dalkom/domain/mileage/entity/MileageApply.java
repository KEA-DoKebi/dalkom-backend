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
@Table(name = "milgApply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class MileageApply extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long milgApplySeq;

	@ManyToOne
	@JoinColumn(name = "userSeq")
	private User user;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Column(name = "approvedState", nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'N'")
	private String approvedState;

	@Column(name = "approvedAt")
	private LocalDateTime approvedAt;

	public MileageApply(User user, Integer amount, String approvedState) {
		this.user = user;
		this.amount = amount;
		this.approvedState = approvedState;
		this.approvedAt = LocalDateTime.now();
	}

	public void changeApprovedState(String state) {
		this.approvedState = state;
	}
}
