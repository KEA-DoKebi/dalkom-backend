package com.dokebi.dalkom.domain.category.entity;

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
import com.dokebi.dalkom.domain.inquiry.entity.Inquiry;
import com.dokebi.dalkom.domain.product.entity.Product;

import lombok.AccessLevel;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Category extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categorySeq;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "parentSeq")
	private Long parentSeq;

	@Column(name = "imageUrl", nullable = false)
	private String imageUrl;

	@OneToMany(mappedBy = "category")
	private List<Product> product = new ArrayList<>();

	@OneToMany(mappedBy = "category")
	private List<Inquiry> inquiry = new ArrayList<>();

	public Category(String name, Long parentSeq, String imageUrl) {
		this.name = name;
		this.parentSeq = parentSeq;
		this.imageUrl = imageUrl;
	}
}
