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
public class MonthlyCategoryListDto {
	public Long categorySeq;
	public String name;
	public Long cnt;

}
