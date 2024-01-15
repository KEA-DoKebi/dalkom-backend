package com.dokebi.dalkom.domain.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.dokebi.dalkom.common.EntityDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Setter
public class PrdtOption extends EntityDate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prdtOptionSeq;

	@Column(nullable = false)
	private Integer optionCode;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String detail;
}
