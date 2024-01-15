package com.dokebi.dalkom.domain.product.entity;

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
import com.dokebi.dalkom.domain.cart.entity.OrderCart;
import com.dokebi.dalkom.domain.category.entity.Category;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.stock.entity.ProductStock;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productSeq;

	@ManyToOne
	@JoinColumn(name = "categorySeq")
	private Category category;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "price", nullable = false)
	private Integer price;

	@Column(name = "info", nullable = false)
	private String info;

	@Column(name = "imageUrl", nullable = false)
	private String imageUrl;

	@Column(name = "company", nullable = false)
	private String company;

	@Column(name = "state", nullable = false, columnDefinition = "VARCHAR(1) DEFAULT 'Y'")
	private String state;

	@OneToMany(mappedBy = "product")
	private List<ProductStock> productStockList = new ArrayList<>();

	@OneToMany(mappedBy = "product")
	private List<ProductImage> productImageList = new ArrayList<>();

	@OneToMany(mappedBy = "product")
	private List<OrderDetail> orderDetailList = new ArrayList<>();

	@OneToMany(mappedBy = "product")
	private List<OrderCart> OrderCartList = new ArrayList<>();

}