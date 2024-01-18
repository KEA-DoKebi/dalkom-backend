package com.dokebi.dalkom.domain.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.review.entity.Review;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ordrDetail")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderDetail extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ordrDetailSeq;

	@ManyToOne
	@JoinColumn(name = "ordrSeq")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "productSeq")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "prdtOptionSeq")
	private ProductOption productOption;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Column(name = "price", nullable = false)
	private Integer price;

	@OneToOne(mappedBy = "orderDetail")
	private Review review;

	public OrderDetail(Order order, Product product, ProductOption productOption,
		Integer amount, Integer price) {
		this.order = order;
		this.product = product;
		this.productOption = productOption;
		this.amount = amount;
		this.price = price;
	}
}
