package com.dokebi.dalkom.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyProductListDto {
	public String Month;
	public Long productSeq;
	public String name;
	public String company;
	public Integer price;
	public Long cnt;
	public Long amount;
	public Long totalPrice;
}
