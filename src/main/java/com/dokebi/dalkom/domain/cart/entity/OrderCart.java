package com.dokebi.dalkom.domain.cart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ordrCart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCart extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ordrCartSeq;

	@ManyToOne
	@JoinColumn(name = "productSeq")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "userSeq")
	private User user;

	@Column(name = "prdtOptionSeq")
	private Long prdtOptionSeq;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	public OrderCart(User user, Product product, Long prdtOptionSeq, Integer amount) {
		this.user = user;
		this.product = product;
		this.prdtOptionSeq = prdtOptionSeq;
		this.amount = amount;
	}
}
