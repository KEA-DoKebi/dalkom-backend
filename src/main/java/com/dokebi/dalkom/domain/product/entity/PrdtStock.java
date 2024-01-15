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
public class PrdtStock extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prdtStockSeq;

	@ManyToOne
	@JoinColumn(name = "productSeq", nullable = false)
	private Product productSeq;

	@ManyToOne
	@JoinColumn(name = "prdtOptionSeq")
	private PrdtOption prdtOptionSeq;

	@Column(nullable = false)
	private Integer amount;
}
