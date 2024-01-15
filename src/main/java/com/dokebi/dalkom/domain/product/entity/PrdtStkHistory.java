package com.dokebi.dalkom.domain.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.dokebi.dalkom.common.EntityDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
public class PrdtStkHistory extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prdtStkHistorySeq;

	@ManyToOne
	@JoinColumn(name = "prdtStockSeq", nullable = false)
	private PrdtStock prdtStockSeq;

	@Column(nullable = false)
	private Integer amount;

	@Column(nullable = false)
	private Integer amountChanged;
}
