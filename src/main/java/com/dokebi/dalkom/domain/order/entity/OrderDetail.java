package com.dokebi.dalkom.domain.order.entity;

import com.dokebi.dalkom.common.EntityDate;
import com.dokebi.dalkom.domain.option.entity.PrdtOption;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.review.entity.Review;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "ordrDetail")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OrderDetail extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderDetailSeq;

	@ManyToOne
	@JoinColumn(name = "ordrSeq")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "productSeq")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "prdtOptionSeq")
	private PrdtOption prdtOption;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Column(name = "price", nullable = false)
	private Integer price;

	@OneToOne(mappedBy = "ordrDetail")
	private Review review;
}
