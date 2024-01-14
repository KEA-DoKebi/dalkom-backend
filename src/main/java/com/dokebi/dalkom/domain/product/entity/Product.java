package com.dokebi.dalkom.domain.product.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.dokebi.dalkom.common.EntityDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productSeq;

	@Column(nullable = false)
	private Long categorySeq;

	@Column(nullable = false)
	private String company;

	@Column(nullable = false)
	private String imageUrl;

	@Column(nullable = false)
	private String info;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Integer price;

	@Column(nullable = false)
	private Character state;
}
