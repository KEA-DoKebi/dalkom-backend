package com.dokebi.dalkom.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
	private Long categorySeq;
	private String name;
	private String imageUrl;

}
