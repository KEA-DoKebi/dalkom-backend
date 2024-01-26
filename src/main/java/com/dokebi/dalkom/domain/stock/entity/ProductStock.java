package com.dokebi.dalkom.domain.stock.entity;

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
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.product.entity.Product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "prdtStock")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductStock extends EntityDate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prdtStockSeq;

	@ManyToOne
	@JoinColumn(name = "productSeq")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "prdtOptionSeq")
	private ProductOption productOption;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@OneToMany(mappedBy = "productStock")
	private List<ProductStockHistory> productStockHistoryList = new ArrayList<>();

	public ProductStock(Product product, ProductOption productOption, Integer amount) {
		this.product = product;
		this.productOption = productOption;
		this.amount = amount;
	}

	public static ProductStock createProductStock() {
		return new ProductStock();
	}
}
