package com.dokebi.dalkom.domain.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

import com.dokebi.dalkom.common.EntityDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
public class Product extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productSeq;

	// @ManyToOne
	// @JoinColumn(name = "categorySeq")
	// private Category category;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private Integer price;

	@Column(nullable = false)
	private String info;

	@Column(nullable = false)
	@ColumnDefault("defaultImageUrl")
	private String imageUrl;

	@Column(nullable = false)
	private String company;

	@Column(nullable = false)
	@ColumnDefault("Y")
	private Character state;
}
