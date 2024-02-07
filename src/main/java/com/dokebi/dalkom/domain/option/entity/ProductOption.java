package com.dokebi.dalkom.domain.option.entity;

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
import com.dokebi.dalkom.domain.stock.entity.ProductStock;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "prdtOption")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class ProductOption extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prdtOptionSeq;

	@Column(name = "optionCode", nullable = false)
	private String optionCode;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "detail", nullable = false)
	private String detail;

	@OneToMany(mappedBy = "productOption")
	private List<ProductStock> productStockList = new ArrayList<>();

	public ProductOption(Long prdtOptionSeq, String optionCode, String name, String detail) {
		this.prdtOptionSeq = prdtOptionSeq;
		this.optionCode = optionCode;
		this.name = name;
		this.detail = detail;
	}

	public static ProductOption createProductOption() {
		return new ProductOption();
	}
}
