package com.dokebi.dalkom.domain.stock.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dokebi.dalkom.common.EntityDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "prdtStkHistory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductStockHistory extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prdtStkHistorySeq;

	@ManyToOne
	@JoinColumn(name = "prdtStockSeq")
	private ProductStock productStock;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Column(name = "amountChanged", nullable = false)
	private Integer amountChanged;

	public ProductStockHistory(ProductStock productStock, Integer amount, Integer amountChanged) {
		this.productStock = productStock;
		this.amount = amount;
		this.amountChanged = amountChanged;
	}
}
