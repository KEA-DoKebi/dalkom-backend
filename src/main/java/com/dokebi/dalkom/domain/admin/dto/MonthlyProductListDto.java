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
<<<<<<< HEAD
	public String month;
=======
	public String Month;
>>>>>>> 4b8f81afc49e6512730462d214f424d2bb7b2043
	public Long productSeq;
	public String name;
	public String company;
	public Integer price;
	public Long cnt;
	public Long amount;
	public Long totalPrice;
}
