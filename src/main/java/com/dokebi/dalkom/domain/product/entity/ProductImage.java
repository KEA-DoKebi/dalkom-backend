package com.dokebi.dalkom.domain.product.entity;

import com.dokebi.dalkom.common.EntityDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "prdtImage")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prdtImageSeq;

	@ManyToOne
	@JoinColumn(name = "productSeq")
	private Product product;

	@Column(name = "imageUrl", nullable = false)
	private String imageUrl;
}
